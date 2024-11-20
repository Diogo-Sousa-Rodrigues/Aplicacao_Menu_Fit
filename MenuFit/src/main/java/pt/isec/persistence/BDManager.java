package pt.isec.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDManager implements Serializable {
    private String dbURL;
    private String dbName;

    private Connection dbConn;


    private static final Long version = 01L;


    public BDManager() {
        // Create a File object for the directory
        this.dbURL = "jdbc:sqlite:src/Base_de_Dados_MenuFit.db";
        this.connect();
    }

    private void connect() {
        try {
            this.dbConn = DriverManager.getConnection(this.dbURL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.closeConnection();
        }
    }

    public void closeConnection() {
        try {
            if (this.dbConn != null) {
                this.dbConn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void registerUser(String fullName, String email, String password, String gender, String birthDateString) {
        String sql = "INSERT INTO USER (Email, Password, Date_of_Birth, Gender, Name) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            // Configurar os parâmetros do PreparedStatement
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, birthDateString);
            pstmt.setString(4, gender);
            pstmt.setString(5, fullName);

            // Executar a inserção
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering user: " + e.getMessage());
        }
    }
}