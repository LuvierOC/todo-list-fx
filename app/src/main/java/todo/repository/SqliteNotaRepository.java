package todo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import todo.db.SqliteConector;
import todo.model.NotaItem;


public class SqliteNotaRepository implements INotaItemRepository {

    public SqliteNotaRepository() {
        SqliteConector.crearTablaSiNoExiste();
    }

    @Override
    public NotaItem guardar(String nuevaNota) {
        if (nuevaNota == null || nuevaNota.isBlank()) {
            throw new IllegalArgumentException(
                    "No se puede guardar una nota sin nombre");
        }

        var nota = new NotaItem(nuevaNota);
        String sql = """
                INSERT INTO notas (nombre_nota, fecha_hora)
                VALUES (?, datetime('now', 'localtime'))
                RETURNING id, fecha_hora
                """;
        try (Connection conn = SqliteConector.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, nuevaNota);

                try (ResultSet rs = stmt.executeQuery()) {
                    nota.setId(rs.getLong("id"));
                    nota.setFechaHora(rs.getString("fecha_hora"));
                }
            }
            guardarTags(conn, nota);
            conn.commit();
            return nota;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar la nota", e);
        }
    }

    @Override
    public void actualizar(NotaItem nota) {
        if (nota.getId() == null) {
            throw new IllegalArgumentException(
                    "No se puede actualizar una nota sin id. Usa guardar() para insertarla.");
        }
        String sql = "UPDATE notas SET nombre_nota = ?, descripcion = ?, fecha_hora = datetime('now', 'localtime') WHERE id = ?";
        try (Connection conn = SqliteConector.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nota.getNombreNota());
                stmt.setString(2, nota.getDescripcion());
                stmt.setLong(3, nota.getId());
                stmt.executeUpdate();
            }
            // Los tags pueden haber cambiado: se desvinculan todos y se vuelven a vincular
            reemplazarTags(conn, nota);
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la nota", e);
        }
    }

    private void guardarTags(Connection conn, NotaItem nota) throws SQLException {
        if (nota.getTags() == null || nota.getTags().isEmpty()) {
            return;
        }
        String insertarTag = "INSERT OR IGNORE INTO tags (nombre) VALUES (?)";
        String buscarTag = "SELECT id FROM tags WHERE nombre = ?";
        String vincular = "INSERT OR IGNORE INTO nota_tags (nota_id, tag_id) VALUES (?, ?)";

        try (PreparedStatement stmtInsertar = conn.prepareStatement(insertarTag);
             PreparedStatement stmtBuscar = conn.prepareStatement(buscarTag);
             PreparedStatement stmtVincular = conn.prepareStatement(vincular)) {

            for (String tag : nota.getTags()) {
                stmtInsertar.setString(1, tag);
                stmtInsertar.executeUpdate();

                stmtBuscar.setString(1, tag);
                long tagId;
                try (ResultSet rs = stmtBuscar.executeQuery()) {
                    rs.next();
                    tagId = rs.getLong(1);
                }

                stmtVincular.setLong(1, nota.getId());
                stmtVincular.setLong(2, tagId);
                stmtVincular.executeUpdate();
            }
        }
    }

    private void reemplazarTags(Connection conn, NotaItem nota) throws SQLException {
        String sql = "DELETE FROM nota_tags WHERE nota_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, nota.getId());
            stmt.executeUpdate();
        }
        guardarTags(conn, nota);
    }

    @Override
    public boolean eliminar(NotaItem nota) {
        if (nota.getId() == null) {
            return false;
        }
        try (Connection conn = SqliteConector.conectar()) {
            conn.setAutoCommit(false);

            String borrarVinculos = "DELETE FROM nota_tags WHERE nota_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(borrarVinculos)) {
                stmt.setLong(1, nota.getId());
                stmt.executeUpdate();
            }
            int filasBorradas;
            String borrarNota = "DELETE FROM notas WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(borrarNota)) {
                stmt.setLong(1, nota.getId());
                filasBorradas = stmt.executeUpdate();

            }

            // Limpieza: eliminar tags que ya no quedan vinculados a ninguna nota
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                        "DELETE FROM tags WHERE id NOT IN (SELECT DISTINCT tag_id FROM nota_tags)");
            }

            conn.commit();
            return filasBorradas > 0 ;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la nota", e);
        }
    }

    @Override
    public List<NotaItem> obtenertodos() {
        // Una sola consulta trae las notas y sus tags agregados (GROUP_CONCAT)
        String sql = """
                SELECT n.id, n.nombre_nota, n.descripcion, n.fecha_hora,
                       GROUP_CONCAT(t.nombre, ',') AS tags
                FROM notas n
                LEFT JOIN nota_tags nt ON nt.nota_id = n.id
                LEFT JOIN tags t ON t.id = nt.tag_id
                GROUP BY n.id
                ORDER BY n.id DESC
                """;
        List<NotaItem> notas = new ArrayList<>();
        try (Connection conn = SqliteConector.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NotaItem nota = new NotaItem(
                        rs.getLong("id"),
                        rs.getString("nombre_nota"),
                        rs.getString("descripcion"),
                        rs.getString("fecha_hora"));
                nota.setTags(parsearTags(rs.getString("tags")));
                notas.add(nota);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer las notas", e);
        }
        return notas;
    }

    private List<String> parsearTags(String tags) {
        if (tags == null || tags.isBlank()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(tags.split(",")));
    }
}