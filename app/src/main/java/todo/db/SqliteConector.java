package todo.db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConector {
    private static  final String URL =
     System.getProperty("todo.db.url", "jdbc:sqlite:todo:db");

    private SqliteConector () {
        
    }

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL);
    }

    public static void crearTablaSiNoExiste(){
        String sql = """
                CREATE TABLE IF NOT EXISTS notas(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nota VARCHAR(25) CHECK (LENGTH(codigo) <= 25 NOT NULL,
                    fecha_hora DATATIME NOT NULL
                )

                CREATE TABLE IF NOT EXISTS etiquetas(
                id INTEGER PRIMARY KEY AUTOINCREMENT
                NOMBRE TEXT NOT NULL UNIQUE
                )

                CREATE TABLE IF NOT EXISTS nota_etiquetas(
                nota_id INTEGER
                etiqueta_id INTEGER
                PRIMARY KEY (nota_id, etiquetas_id)
                FOREING KEY (nota_id) REFERENCES notas(id) ON DELETE CASCADE,
                FOREING KEY (etiqueta_id) REFERENCES etiquetas(id) ON DELETE CASCADE
                )
                """;
        
        try (Connection conn = conectar();
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo inicializar la base de datos", e);
        }   
    }

}
