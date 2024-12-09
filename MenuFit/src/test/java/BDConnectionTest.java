
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.persistence.BDManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BDConnectionTest {
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
    void testConnection() {
        assertNotNull(bdManager, "BDManager não foi inicializado corretamente.");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Base_de_Dados_MenuFit.db")) {
            assertNotNull(conn, "A conexão com o banco de dados falhou.");
            System.out.println("Teste de conexão com o banco de dados: SUCESSO");
        } catch (SQLException e) {
            fail("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
