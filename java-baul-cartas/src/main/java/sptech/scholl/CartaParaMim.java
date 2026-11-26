package sptech.scholl;

public class CartaParaMim extends Carta {
    private String momentoVida;
    private String lerNovamenteEm;

    public CartaParaMim(String titulo, String destinatario, String mensagem, String dataRegistro,
                        Sentimento sentimento, String momentoVida, String lerNovamenteEm)
            throws CartaInvalidaException {
        super(titulo, destinatario, mensagem, dataRegistro, sentimento);
        this.momentoVida = (momentoVida == null || momentoVida.isBlank()) ? "Momento atual" : momentoVida.trim();
        this.lerNovamenteEm = (lerNovamenteEm == null || lerNovamenteEm.isBlank()) ? "Quando eu precisar" : lerNovamenteEm.trim();
    }

    @Override
    public String formatarDetalhes() {
        return "Tipo: Carta para Mim | Fase: " + momentoVida + " | Reler: " + lerNovamenteEm;
    }

    public String getMomentoVida() {
        return momentoVida;
    }

    public void setMomentoVida(String momentoVida) {
        this.momentoVida = momentoVida;
    }

    public String getLerNovamenteEm() {
        return lerNovamenteEm;
    }

    public void setLerNovamenteEm(String lerNovamenteEm) {
        this.lerNovamenteEm = lerNovamenteEm;
    }
}