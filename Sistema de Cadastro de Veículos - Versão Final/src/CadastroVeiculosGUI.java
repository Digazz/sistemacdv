import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroVeiculosGUI {
    private JFrame frame;
    private JTextArea area;
    private SistemaCadastro sistema;

    public CadastroVeiculosGUI() {
        sistema = new SistemaCadastro();

        frame = new JFrame("Sistema de Cadastro de Veículos");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton adicionarBtn = new JButton("Adicionar Veículo");
        JButton consultarBtn = new JButton("Consultar Veículo");
        JButton alterarBtn = new JButton("Alterar Veículo");
        JButton excluirBtn = new JButton("Excluir Veículo");
        JButton listarBtn = new JButton("Listar Veículos");

        panel.add(adicionarBtn);
        panel.add(consultarBtn);
        panel.add(alterarBtn);
        panel.add(excluirBtn);
        panel.add(listarBtn);

        area = new JTextArea();
        area.setEditable(false);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(area), BorderLayout.CENTER);

        adicionarBtn.addActionListener(e -> abrirFormularioAdicao(null));
        consultarBtn.addActionListener(e -> consultarVeiculo());
        alterarBtn.addActionListener(e -> alterarVeiculo());
        excluirBtn.addActionListener(e -> excluirVeiculo());
        listarBtn.addActionListener(e -> listarVeiculos());

        frame.setVisible(true);
    }

    private void abrirFormularioAdicao(Veiculo existente) {
        JTextField placaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField anoField = new JTextField();
        JTextField extraField = new JTextField();
        JComboBox<String> tipoBox = new JComboBox<>(new String[]{"Carro", "Moto"});
        JLabel extraLabel = new JLabel("Portas:");

        tipoBox.addActionListener(e -> {
            String selected = (String) tipoBox.getSelectedItem();
            extraLabel.setText(selected.equals("Carro") ? "Portas:" : "Cilindradas:");
        });

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Tipo:")); panel.add(tipoBox);
        panel.add(new JLabel("Placa:")); panel.add(placaField);
        panel.add(new JLabel("Modelo:")); panel.add(modeloField);
        panel.add(new JLabel("Ano:")); panel.add(anoField);
        panel.add(extraLabel); panel.add(extraField);

        if (existente != null) {
            placaField.setText(existente.getPlaca());
            modeloField.setText(existente.getModelo());
            anoField.setText(String.valueOf(existente.getAno()));
            if (existente instanceof Carro) {
                tipoBox.setSelectedItem("Carro");
                extraField.setText(String.valueOf(((Carro) existente).getPortas()));
            } else {
                tipoBox.setSelectedItem("Moto");
                extraField.setText(String.valueOf(((Moto) existente).getCilindradas()));
            }
            placaField.setEnabled(false);
        }

        int result = JOptionPane.showConfirmDialog(frame, panel,
                existente == null ? "Adicionar Veículo" : "Alterar Veículo", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String tipo = (String) tipoBox.getSelectedItem();
                String placa = placaField.getText();
                String modelo = modeloField.getText();
                int ano = Integer.parseInt(anoField.getText());
                int extra = Integer.parseInt(extraField.getText());

                Veiculo novo = tipo.equals("Carro") ?
                        new Carro(placa, modelo, ano, extra) :
                        new Moto(placa, modelo, ano, extra);

                if (existente == null) {
                    sistema.adicionarVeiculo(novo);
                    JOptionPane.showMessageDialog(frame, "Veículo cadastrado com sucesso!");
                } else {
                    sistema.atualizarVeiculo(placa, novo);
                    JOptionPane.showMessageDialog(frame, "Veículo alterado com sucesso!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro nos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void consultarVeiculo() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa para consultar:");
        if (placa != null) {
            Veiculo v = sistema.buscarPorPlaca(placa);
            if (v != null) {
                JOptionPane.showMessageDialog(frame, v.exibirDados(), "Dados do Veículo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
            }
        }
    }

    private void alterarVeiculo() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa para alterar:");
        if (placa != null) {
            Veiculo v = sistema.buscarPorPlaca(placa);
            if (v != null) {
                abrirFormularioAdicao(v);
            } else {
                JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
            }
        }
    }

    private void excluirVeiculo() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa para excluir:");
        if (placa != null) {
            if (sistema.excluirVeiculo(placa)) {
                JOptionPane.showMessageDialog(frame, "Veículo excluído com sucesso.");
            } else {
                JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
            }
        }
    }

    private void listarVeiculos() {
        List<Veiculo> lista = sistema.listarTodos();
        if (lista.isEmpty()) {
            area.setText("Nenhum veículo cadastrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Veiculo v : lista) {
            sb.append(v.exibirDados()).append("\n-------------------\n");
        }
        area.setText(sb.toString());
    }

    public static void main(String[] args) {
        new CadastroVeiculosGUI();
    }
}