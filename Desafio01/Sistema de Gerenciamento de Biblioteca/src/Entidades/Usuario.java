package Entidades;

import Conexao.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    private String usuarioID,  endereco, email, telefone, emprestimo;

    @Column(name"nome")
    private String nome;
    private Date dataAssociacao;
    private List<Livro> livrosEmprestados;

    public Usuario(String usuarioID, String nome, String endereco, String email, String telefone, String emprestimo, Date dataAssociacao, List<Livro> livrosEmprestados) {
        this.usuarioID = usuarioID;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.emprestimo = emprestimo;
        this.dataAssociacao = dataAssociacao;
        this.livrosEmprestados = new ArrayList<>();
    }

    public String getUsuarioID() {
        return usuarioID;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(String emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Date getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(Date dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    public List<Livro> getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(List<Livro> livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    // Método para inserir o usuário no banco de dados
    public void inserirUsuario() {
        String sql = "INSERT INTO usuario (usuarioID, nome, endereco, email, telefone, emprestimo, dataAssociacao, livroEmprestados) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.usuarioID);
            statement.setString(2, this.nome);
            statement.setString(3, this.endereco);

            // Verifica se dataPublicacao não é nula
            if (this.dataAssociacao == null) {
                System.err.println("Data de publicação é nula.");
                return;
            }

            // Converte java.util.Date para java.sql.Date
            statement.setString(4, this.email);
            statement.setString(5, this.telefone);
            statement.setString(6, this.emprestimo);
            statement.setDate(7, new java.sql.Date(this.dataAssociacao.getTime()));
            statement.setString(8, this.livrosEmprestados.toString());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Um Usuário foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }
}

