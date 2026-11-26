package sptech.scholl;


public abstract class Carta {
    private int id;
    private String titulo;
    private String destinatario;
    private String mensagem;
    private String dataRegistro;
    private Sentimento sentimento;

    public Carta(String titulo, String destinatario, String mensagem, String dataRegistro, Sentimento sentimento)
            throws CartaInvalidaException {
        validarCampos(titulo, destinatario, mensagem, sentimento);
        this.titulo = titulo.trim();
        this.destinatario = destinatario.trim();
        this.mensagem = mensagem.trim();
        this.dataRegistro = (dataRegistro == null || dataRegistro.isBlank()) ? "Sem data" : dataRegistro.trim();
        this.sentimento = sentimento;
    }

    private void validarCampos(String titulo, String destinatario, String mensagem, Sentimento sentimento)
            throws CartaInvalidaException {
        if (titulo == null || titulo.isBlank()) {
            throw new CartaInvalidaException("O título da carta é obrigatório.");
        }
        if (destinatario == null || destinatario.isBlank()) {
            throw new CartaInvalidaException("O destinatário da carta é obrigatório.");
        }
        if (mensagem == null || mensagem.isBlank()) {
            throw new CartaInvalidaException("A mensagem da carta não pode ficar vazia.");
        }
        if (sentimento == null) {
            throw new CartaInvalidaException("Selecione um sentimento para a carta.");
        }
    }

    public String formatarCabecalho() {
        return "[" + sentimento.getDescricao() + "] " + titulo + " -> Para: " + destinatario + " (" + dataRegistro + ")";
    }

    public abstract String formatarDetalhes();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) throws CartaInvalidaException {
        if (titulo == null || titulo.isBlank()) {
            throw new CartaInvalidaException("O título da carta é obrigatório.");
        }
        this.titulo = titulo.trim();
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) throws CartaInvalidaException {
        if (destinatario == null || destinatario.isBlank()) {
            throw new CartaInvalidaException("O destinatário da carta é obrigatório.");
        }
        this.destinatario = destinatario.trim();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) throws CartaInvalidaException {
        if (mensagem == null || mensagem.isBlank()) {
            throw new CartaInvalidaException("A mensagem da carta não pode ficar vazia.");
        }
        this.mensagem = mensagem.trim();
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Sentimento getSentimento() {
        return sentimento;
    }

    public void setSentimento(Sentimento sentimento) throws CartaInvalidaException {
        if (sentimento == null) {
            throw new CartaInvalidaException("Selecione um sentimento para a carta.");
        }
        this.sentimento = sentimento;
    }
}