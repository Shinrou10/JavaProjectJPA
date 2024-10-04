package Entidades;

import Conexao.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {

    private situacaoEmprestimo id;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo, dataDevolucao;
    private String estado;
    private BigDecimal multa;

    public Emprestimo(situacaoEmprestimo id, Livro livro, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao, String estado, BigDecimal multa) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.estado = estado;
        this.multa = multa;
    }


    // Método para verificar a disponibilidade do livro no banco de dados
    public boolean verificarDisponibilidade(String isbn) {
        String sql = "SELECT quantidade FROM livros WHERE isbn = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int quantidade = resultSet.getInt("quantidade");
                return quantidade > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar disponibilidade: " + e.getMessage());
        }

        return false;
    }

    // Método para subtrair a quantidade de livros disponíveis após um empréstimo
    public void subtrairQuantidade(String isbn) {
        String sql = "UPDATE livros SET quantidade = quantidade - 1 WHERE isbn = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, isbn);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Quantidade de livros atualizada com sucesso.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar quantidade: " + e.getMessage());
        }
    }

    // Método para calcular multa se o livro for devolvido após 5 dias
    public BigDecimal calcularMulta(LocalDate dataDevolucaoPrevista) {
        long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, LocalDate.now());

        if (diasAtraso > 5) {
            BigDecimal multa = new BigDecimal(diasAtraso - 5).multiply(new BigDecimal("5"));
            return multa;
        }

        return BigDecimal.ZERO;
    }

    // Método para registrar o empréstimo
    public void emprestimoLivro() {
        if (!verificarDisponibilidade(livro.getIsbn())) {
            System.out.println("Livro não disponível para empréstimo.");
            return;
        }

        String sql = "INSERT INTO emprestimos (id, livro, usuario, dataEmprestimo, dataDevolucao, estado, multa) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, String.valueOf(this.id));
            statement.setString(2, this.livro.getIsbn());
            statement.setString(3, this.usuario.getUsuarioID());
            statement.setDate(4, Date.valueOf(this.dataEmprestimo));
            statement.setDate(5, Date.valueOf(this.dataDevolucao));
            statement.setString(6, this.estado);

            // Calcula multa se houver atraso
            BigDecimal multaCalculada = calcularMulta(this.dataDevolucao);
            statement.setString(7, multaCalculada.toString());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Empréstimo registrado com sucesso!");
                subtrairQuantidade(this.livro.getIsbn()); // Atualiza a quantidade de livros
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar empréstimo: " + e.getMessage());
        }
    }
}
