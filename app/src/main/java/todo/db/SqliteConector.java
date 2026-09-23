package todo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SqliteConector {

    private static final String URL =
            System.getProperty("todo.db.url", "jdbc:sqlite:todo.db");

    private SqliteConector() {
    }

    public static Connection conectar() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void crearTablaSiNoExiste() {
        String[] crearTablas = {
                """
                CREATE TABLE IF NOT EXISTS notas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre_nota TEXT NOT NULL,
                    descripcion TEXT,
                    fecha_hora TEXT NOT NULL
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS tags (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL UNIQUE
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS nota_tags (
                    nota_id INTEGER NOT NULL,
                    tag_id INTEGER NOT NULL,
                    PRIMARY KEY (nota_id, tag_id),
                    FOREIGN KEY (nota_id) REFERENCES notas(id) ON DELETE CASCADE,
                    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
                )
                """
        };
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            for (String sql : crearTablas) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo inicializar la base de datos SQLite", e);
        }
    }
}