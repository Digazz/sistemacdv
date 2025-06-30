import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static final String URL = "jdbc:sqlite:veiculos.db";

    public BancoDeDados() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "placa TEXT PRIMARY KEY," +
                "modelo TEXT NOT NULL," +
                "ano INTEGER NOT NULL," +
                "tipo TEXT NOT NULL," +
                "portas INTEGER," +
                "cilindradas INTEGER" +
                ");";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void salvarVeiculos(List<Veiculo> veiculos) {
        String sql = "INSERT OR REPLACE INTO veiculos(placa, modelo, ano, tipo, portas, cilindradas) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Veiculo v : veiculos) {
                pstmt.setString(1, v.getPlaca());
                pstmt.setString(2, v.getModelo());
                pstmt.setInt(3, v.getAno());
                pstmt.setString(4, v.getTipo());
                if (v instanceof Carro) {
                    pstmt.setInt(5, ((Carro) v).getPortas());
                    pstmt.setNull(6, Types.INTEGER);
                } else if (v instanceof Moto) {
                    pstmt.setNull(5, Types.INTEGER);
                    pstmt.setInt(6, ((Moto) v).getCilindradas());
                } else {
                    pstmt.setNull(5, Types.INTEGER);
                    pstmt.setNull(6, Types.INTEGER);
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public List<Veiculo> carregarVeiculos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                String tipo = rs.getString("tipo");

                if ("Carro".equals(tipo)) {
                    int portas = rs.getInt("portas");
                    lista.add(new Carro(placa, modelo, ano, portas));
                } else if ("Moto".equals(tipo)) {
                    int cilindradas = rs.getInt("cilindradas");
                    lista.add(new Moto(placa, modelo, ano, cilindradas));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return lista;
    }
}