package sptech.scholl;

public enum Sentimento {
    GRATIDAO("Gratidão"),
    SAUDADE("Saudade"),
    ESPERANCA("Esperança"),
    ADMIRACAO("Admiração");

    private final String descricao;

    Sentimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}