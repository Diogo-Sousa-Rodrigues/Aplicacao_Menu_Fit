import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.persistence.BDManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class BDCreateTableTest {
    private BDManager bdManager;

    @BeforeEach
    void setUp() {
        bdManager = new BDManager();
    }

    @AfterEach
    void tearDown() {
        bdManager.closeConnection();
    }

    @Test
    void testCreateTables() {
        assertNotNull(bdManager, "BDManager não foi inicializado corretamente.");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Base_de_Dados_MenuFit.db")) {
            assertNotNull(conn, "A conexão com o banco de dados falhou.");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS USER (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "preferences TEXT," +
                    "calorieLimit INTEGER" +
                    ");";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println("Tabela 'USER' criada ou já existente");
            } catch (SQLException e) {
                fail("Erro ao criar a tabela Users: " + e.getMessage());
            }
        } catch (SQLException e) {
            fail("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

}
