package Leiloes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;

public class conectaDAO {

    private final String url = "jdbc:mysql://localhost/uc11";
    private final String user = "root";
    private final String password = "Junho2004@";

    public Connection connectDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
            System.err.println("Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }
    
    public void executeSQLScript(String filePath) {
        Connection conn = connectDB();
        if (conn != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                StringBuilder sqlScript = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sqlScript.append(line).append("\n");
                }
                String[] sqlCommands = sqlScript.toString().split(";");
                
                try (Statement stmt = conn.createStatement()) {
                    for (String command : sqlCommands) {
                        if (!command.trim().isEmpty()) {
                            stmt.execute(command);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Script executado com sucesso.");
                }
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + e.getMessage());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao executar o script SQL: " + e.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        conectaDAO conectaDAO = new conectaDAO();
        conectaDAO.executeSQLScript("C:\\Users\\dimgo\\OneDrive\\Área de Trabalho\\pietro\\SENAC\\3 Sem\\Mod 1\\ATV2\\uc11.sql");
    }
}
