package sptech.scholl;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

public class TelaCartas extends JFrame {
    private static final String TIPO_PARA_MIM = "Carta para Mim";
    private static final String TIPO_REFLEXAO = "Carta de Reflexão";

    private final CartaDAO repository;

    // Campos da aba Escrever Carta
    private JComboBox<String> cmbTipoCarta;
    private JComboBox<Sentimento> cmbSentimento;
    private JTextField txtTitulo;
    private JTextField txtDestinatario;
    private JTextField txtData;
    private JTextArea txtMensagem;

    // Campos específicos de CartaParaMim
    private JTextField txtMomentoVida;
    private JTextField txtLerNovamenteEm;

    // Campos específicos de CartaReflexao
    private JTextField txtGatilhoCena;
    private JTextField txtAprendizado;

    private JPanel painelCamposEspecificos;
    private CardLayout cardLayoutEspecifico;

    // Campos da aba Cartas Guardadas
    private JTextArea areaListagem;

    // Campos da aba Buscar
    private JTextField txtTermoBusca;
    private JTextArea areaResultadosBusca;

    public TelaCartas() {
        this.repository = new CartaDAO();
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Baú de Cartas");
        setSize(720, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Escrever Carta", criarAbaEscrever());
        abas.addTab("Cartas Guardadas", criarAbaListar());
        abas.addTab("Buscar", criarAbaBuscar());

        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarAbaEscrever() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbTipoCarta = new JComboBox<>(new String[]{TIPO_PARA_MIM, TIPO_REFLEXAO});
        cmbSentimento = new JComboBox<>(Sentimento.values());
        txtTitulo = new JTextField();
        txtDestinatario = new JTextField();
        txtData = new JTextField();
        txtMensagem = new JTextArea(4, 20);
        txtMensagem.setLineWrap(true);
        txtMensagem.setWrapStyleWord(true);

        int linha = 0;
        adicionarCampo(formulario, gbc, linha++, "Tipo de carta:", cmbTipoCarta);
        adicionarCampo(formulario, gbc, linha++, "Sentimento:", cmbSentimento);
        adicionarCampo(formulario, gbc, linha++, "Título:", txtTitulo);
        adicionarCampo(formulario, gbc, linha++, "Destinatário:", txtDestinatario);
        adicionarCampo(formulario, gbc, linha++, "Data:", txtData);

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formulario.add(new JLabel("Mensagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = linha++;
        gbc.weightx = 1.0;
        formulario.add(new JScrollPane(txtMensagem), gbc);

        cardLayoutEspecifico = new CardLayout();
        painelCamposEspecificos = new JPanel(cardLayoutEspecifico);
        painelCamposEspecificos.add(criarPainelParaMim(), TIPO_PARA_MIM);
        painelCamposEspecificos.add(criarPainelReflexao(), TIPO_REFLEXAO);

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 2;
        formulario.add(painelCamposEspecificos, gbc);

        cmbTipoCarta.addActionListener(e -> {
            String selecionado = (String) cmbTipoCarta.getSelectedItem();
            cardLayoutEspecifico.show(painelCamposEspecificos, selecionado);
        });

        JButton btnSalvar = new JButton("Salvar Carta");
        btnSalvar.addActionListener(e -> salvarCarta());

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.add(btnSalvar);

        painelPrincipal.add(formulario, BorderLayout.CENTER);
        painelPrincipal.add(rodape, BorderLayout.SOUTH);
        return painelPrincipal;
    }

    private JPanel criarPainelParaMim() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Detalhes - Carta para Mim"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMomentoVida = new JTextField();
        txtLerNovamenteEm = new JTextField();

        adicionarCampo(painel, gbc, 0, "Momento de vida:", txtMomentoVida);
        adicionarCampo(painel, gbc, 1, "Ler novamente em:", txtLerNovamenteEm);
        return painel;
    }

    private JPanel criarPainelReflexao() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Detalhes - Carta de Reflexão"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtGatilhoCena = new JTextField();
        txtAprendizado = new JTextField();

        adicionarCampo(painel, gbc, 0, "Gatilho ou cena:", txtGatilhoCena);
        adicionarCampo(painel, gbc, 1, "Aprendizado:", txtAprendizado);
        return painel;
    }

    private JPanel criarAbaListar() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarCartas());

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(btnAtualizar);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarAbaBuscar() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topo = new JPanel(new BorderLayout(8, 0));
        txtTermoBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCartas());
        txtTermoBusca.addActionListener(e -> buscarCartas());

        topo.add(new JLabel("Palavra-chave ou destinatário: "), BorderLayout.WEST);
        topo.add(txtTermoBusca, BorderLayout.CENTER);
        topo.add(btnBuscar, BorderLayout.EAST);

        areaResultadosBusca = new JTextArea();
        areaResultadosBusca.setEditable(false);
        areaResultadosBusca.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaResultadosBusca), BorderLayout.CENTER);
        return painel;
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String rotulo, java.awt.Component campo) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(new JLabel(rotulo), gbc);

        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.weightx = 1.0;
        painel.add(campo, gbc);
    }

    private void salvarCarta() {
        try {
            String tipo = (String) cmbTipoCarta.getSelectedItem();
            Sentimento sentimento = (Sentimento) cmbSentimento.getSelectedItem();
            String titulo = txtTitulo.getText();
            String destinatario = txtDestinatario.getText();
            String data = txtData.getText();
            String mensagem = txtMensagem.getText();

            Carta carta;
            if (TIPO_PARA_MIM.equals(tipo)) {
                carta = new CartaParaMim(
                        titulo,
                        destinatario,
                        mensagem,
                        data,
                        sentimento,
                        txtMomentoVida.getText(),
                        txtLerNovamenteEm.getText()
                );
            } else {
                carta = new CartaReflexao(
                        titulo,
                        destinatario,
                        mensagem,
                        data,
                        sentimento,
                        txtGatilhoCena.getText(),
                        txtAprendizado.getText()
                );
            }

            repository.salvar(carta);
            JOptionPane.showMessageDialog(this, "Carta salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarCartas();
        } catch (CartaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco: " + ex.getMessage(), "Erro de Banco", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCartas() {
        try {
            List<Carta> cartas = repository.listarTodas();
            areaListagem.setText(formatarListaCartas(cartas, "Nenhuma carta cadastrada ainda."));
        } catch (SQLException | CartaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar cartas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCartas() {
        String termo = txtTermoBusca.getText().trim();
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite algo para buscar.", "Busca", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Carta> resultados = repository.buscarPorTermo(termo);
            areaResultadosBusca.setText(formatarListaCartas(resultados, "Nenhuma carta encontrada para: \"" + termo + "\""));
        } catch (SQLException | CartaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarListaCartas(List<Carta> cartas, String mensagemVazia) {
        if (cartas == null || cartas.isEmpty()) {
            return mensagemVazia;
        }

        StringBuilder sb = new StringBuilder();
        for (Carta carta : cartas) {
            sb.append("--------------------------------------------------\n");
            sb.append(carta.formatarCabecalho()).append("\n");
            sb.append(carta.formatarDetalhes()).append("\n\n");
            sb.append(carta.getMensagem()).append("\n");
        }
        sb.append("--------------------------------------------------\n");
        return sb.toString();
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtDestinatario.setText("");
        txtData.setText("");
        txtMensagem.setText("");
        txtMomentoVida.setText("");
        txtLerNovamenteEm.setText("");
        txtGatilhoCena.setText("");
        txtAprendizado.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCartas().setVisible(true));
    }
}