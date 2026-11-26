package sptech.scholl;

public class CartaReflexao extends Carta {
    private String gatilhoOuCena;
    private String aprendizado;

    public CartaReflexao(String titulo, String destinatario, String mensagem, String dataRegistro,
                         Sentimento sentimento, String gatilhoOuCena, String aprendizado)
            throws CartaInvalidaException {
        super(titulo, destinatario, mensagem, dataRegistro, sentimento);
        if (aprendizado == null || aprendizado.isBlank()) {
            throw new CartaInvalidaException("Registre ao menos um aprendizado ou reflexão.");
        }
        this.gatilhoOuCena = (gatilhoOuCena == null || gatilhoOuCena.isBlank()) ? "Cotidiano" : gatilhoOuCena.trim();
        this.aprendizado = aprendizado.trim();
    }

    @Override
    public String formatarDetalhes() {
        return "Tipo: Carta de Reflexão | Cena: " + gatilhoOuCena + " | Aprendizado: " + aprendizado;
    }

    public String getGatilhoOuCena() {
        return gatilhoOuCena;
    }

    public void setGatilhoOuCena(String gatilhoOuCena) {
        this.gatilhoOuCena = gatilhoOuCena;
    }

    public String getAprendizado() {
        return aprendizado;
    }

    public void setAprendizado(String aprendizado) throws CartaInvalidaException {
        if (aprendizado == null || aprendizado.isBlank()) {
            throw new CartaInvalidaException("Registre ao menos um aprendizado ou reflexão.");
        }
        this.aprendizado = aprendizado.trim();
    }
}
