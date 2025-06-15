package utils;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Interface extends JFrame {

    private GrafoCapitais grafo;
    private JComboBox<String> origemComboBox;
    private JComboBox<String> destinoComboBox;
    private JTextArea resultadoArea;

    public Interface() {
        setTitle("Calculadora de Rotas entre Capitais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); 

        grafo = inicializarGrafo();

        String[] siglas = grafo.capitais.keySet().toArray(new String[0]);
        Arrays.sort(siglas);

        String[] opcoes = new String[siglas.length];
        for (int i = 0; i < siglas.length; i++) {
            opcoes[i] = siglas[i] + " - " + NomesCapitais.NOMES.get(siglas[i]);
        }

        origemComboBox = new JComboBox<>(opcoes);
        destinoComboBox = new JComboBox<>(opcoes);
        JButton calcularBotao = new JButton("Calcular Rota");
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(new GridLayout(2, 2, 10, 10));
        painelEntrada.add(new JLabel("Origem:"));
        painelEntrada.add(origemComboBox);
        painelEntrada.add(new JLabel("Destino:"));
        painelEntrada.add(destinoComboBox);

        painelEntrada.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcularBotao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelCentral.add(painelEntrada);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(calcularBotao);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        JScrollPane scroll = new JScrollPane(resultadoArea);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(scroll);

        add(painelCentral, BorderLayout.CENTER);

        ImageIcon mapaIcon = new ImageIcon(getClass().getResource("/resources/mapa.png"));
        Image imagem = mapaIcon.getImage().getScaledInstance(800, 820, Image.SCALE_SMOOTH);
        JLabel mapaLabel = new JLabel(new ImageIcon(imagem));
        add(mapaLabel, BorderLayout.SOUTH);

        calcularBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularRota();
            }
        });
    }

    private void calcularRota() {
        String origemOpcao = (String) origemComboBox.getSelectedItem();
        String destinoOpcao = (String) destinoComboBox.getSelectedItem();

        if (origemOpcao == null || destinoOpcao == null) {
            resultadoArea.setText("Por favor, selecione as capitais.");
            return;
        }

        String origem = origemOpcao.split(" - ")[0];
        String destino = destinoOpcao.split(" - ")[0];

        ResultadoRota rota = grafo.calcularMenorRotaTerrestre(origem, destino);
        if (rota == null) {
            resultadoArea.setText("Não há rota terrestre entre " +
                    NomesCapitais.NOMES.get(origem) + " e " +
                    NomesCapitais.NOMES.get(destino));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Distância total: ").append(rota.distanciaTotal).append(" km\n");
            sb.append("Caminho:");
            for (int i = 0; i < rota.caminho.size(); i++) {
                String sigla = rota.caminho.get(i);
                sb.append(NomesCapitais.NOMES.get(sigla)).append(" (").append(sigla).append(")");
                if (i < rota.caminho.size() - 1) {
                    sb.append(" → ");
                }
            }
            resultadoArea.setText(sb.toString());
        }
    }

    public GrafoCapitais inicializarGrafo() {
        return Main.inicializarGrafo();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }
}
