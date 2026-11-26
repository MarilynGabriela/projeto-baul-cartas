package sptech.scholl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CartaDAO {

    public void salvar(Carta carta) throws SQLException {
        String sql = """
                INSERT INTO carta (
                    tipo_carta, titulo, destinatario, mensagem, data_registro, sentimento,
                    momento_vida, ler_novamente_em, gatilho_ou_cena, aprendizado
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(2, carta.getTitulo());
            stmt.setString(3, carta.getDestinatario());
            stmt.setString(4, carta.getMensagem());
            stmt.setString(5, carta.getDataRegistro());
            stmt.setString(6, carta.getSentimento().name());

            if (carta instanceof CartaParaMim paraMim) {
                stmt.setString(1, "PARA_MIM");
                stmt.setString(7, paraMim.getMomentoVida());
                stmt.setString(8, paraMim.getLerNovamenteEm());
                stmt.setNull(9, Types.VARCHAR);
                stmt.setNull(10, Types.VARCHAR);
            } else if (carta instanceof CartaReflexao reflexao) {
                stmt.setString(1, "REFLEXAO");
                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.VARCHAR);
                stmt.setString(9, reflexao.getGatilhoOuCena());
                stmt.setString(10, reflexao.getAprendizado());
            } else {
                throw new SQLException("Tipo de carta não suportado: " + carta.getClass().getSimpleName());
            }

            stmt.executeUpdate();
        }
    }

    public List<Carta> listarTodas() throws SQLException, CartaInvalidaException {
        String sql = "SELECT * FROM carta ORDER BY id DESC";
        List<Carta> cartas = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cartas.add(mapearCarta(rs));
            }
        }
        return cartas;
    }

    public List<Carta> buscarPorTermo(String termo) throws SQLException, CartaInvalidaException {
        String sql = """
                SELECT * FROM carta
                WHERE titulo LIKE ?
                   OR destinatario LIKE ?
                   OR mensagem LIKE ?
                   OR aprendizado LIKE ?
                ORDER BY id DESC
                """;
        List<Carta> cartas = new ArrayList<>();
        String padrao = "%" + termo.trim() + "%";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, padrao);
            stmt.setString(2, padrao);
            stmt.setString(3, padrao);
            stmt.setString(4, padrao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cartas.add(mapearCarta(rs));
                }
            }
        }
        return cartas;
    }

    private Carta mapearCarta(ResultSet rs) throws SQLException, CartaInvalidaException {
        int id = rs.getInt("id");
        String tipo = rs.getString("tipo_carta");
        String titulo = rs.getString("titulo");
        String destinatario = rs.getString("destinatario");
        String mensagem = rs.getString("mensagem");
        String dataRegistro = rs.getString("data_registro");
        Sentimento sentimento = Sentimento.valueOf(rs.getString("sentimento"));

        Carta carta;
        if ("PARA_MIM".equalsIgnoreCase(tipo)) {
            carta = new CartaParaMim(
                    titulo,
                    destinatario,
                    mensagem,
                    dataRegistro,
                    sentimento,
                    rs.getString("momento_vida"),
                    rs.getString("ler_novamente_em")
            );
        } else if ("REFLEXAO".equalsIgnoreCase(tipo)) {
            carta = new CartaReflexao(
                    titulo,
                    destinatario,
                    mensagem,
                    dataRegistro,
                    sentimento,
                    rs.getString("gatilho_ou_cena"),
                    rs.getString("aprendizado")
            );
        } else {
            throw new SQLException("Tipo de carta desconhecido no banco: " + tipo);
        }

        carta.setId(id);
        return carta;
    }
}