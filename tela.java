import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class tela {
    public static void main(String[] args) {
        // Criar janela
        JFrame tela = new JFrame("Cadastro de Contato");
        tela.setSize(350, 200);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new GridLayout(3, 2)); // 3 linhas, 2 colunas

        // Componentes
        JLabel nomeLabel = new JLabel("Nome completo:");
        JTextField nomeCampo = new JTextField();

        JLabel telLabel = new JLabel("Telefone:");
        JTextField telCampo = new JTextField();

        JButton botaoSalvar = new JButton("Salvar");

        // Ação do botão
        botaoSalvar.addActionListener(e -> {
            String nome = nomeCampo.getText();
            String telefone = telCampo.getText();

            if (nome.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                return;
            }

            // Salvar no banco
            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://nozomi.proxy.rlwy.net:18915/railway", // troque se seu banco tiver outro nome
                        "postgres", // usuário
                        "qsyWaZUNUAVlmkfMFeEkjaTndNvUnNro" // sua senha
                );

                String sql = "INSERT INTO contatos (nome, telefone) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, telefone);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Contato salvo com sucesso!");
                nomeCampo.setText("");
                telCampo.setText("");

                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        // Adicionar na tela
        tela.add(nomeLabel);
        tela.add(nomeCampo);
        tela.add(telLabel);
        tela.add(telCampo);
        tela.add(new JLabel()); // espaço vazio
        tela.add(botaoSalvar);

        // Mostrar janela
        tela.setVisible(true);
    }
}
