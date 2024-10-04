package Entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conexao.DbConnection;

public class Livro {
    private String isbn, titulo, autor, genero;
    private Date dataPublicacao;
    private int quantidade;

    // Construtor principal
    public Livro(String isbn, String titulo, String autor, Date dataPublicacao, String genero, int quantidade) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.genero = genero;
        this.quantidade = quantidade;
    }

    // Novo método para converter string em Date
    public static Date converterStringParaData(String dataString) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = formato.parse(dataString);
        } catch (ParseException e) {
            System.err.println("Erro ao converter a string para data: " + e.getMessage());
        }
        return data;
    }

    // Método para inserir o livro no banco de dados
    public void inserirLivro() {
        String sql = "INSERT INTO livro (isbn, titulo, autor, dataPublicacao, genero, quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.isbn);
            statement.setString(2, this.titulo);
            statement.setString(3, this.autor);


            // Verifica se dataPublicacao não é nula
            if (this.dataPublicacao == null) {
                System.err.println("Data de publicação é nula.");
                return;
            }

            // Converte java.util.Date para java.sql.Date
            statement.setDate(4, new java.sql.Date(this.dataPublicacao.getTime()));
            statement.setString(5, this.genero);
            statement.setInt(6, this.quantidade);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Um livro foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}



