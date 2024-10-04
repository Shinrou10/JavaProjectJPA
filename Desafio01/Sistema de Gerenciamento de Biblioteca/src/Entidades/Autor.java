package Entidades;

import Conexao.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Autor {

    private String autorID, nome, biografia, nacionalidade;
    private LocalDate dataNascimento;


    public Autor(String autorID, String nome, String biografia, String nacionalidade, LocalDate dataNascimento) {
        this.autorID = autorID;
        this.nome = nome;
        this.biografia = biografia;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
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
    public void inserirAutor() {
        String sql = "INSERT INTO autor (autorID, nome, , dataPublicacao, genero, quantidade) VALUES (?, ?, ?, ?, ?, ?)";

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

    public String getAutorID() {
        return autorID;
    }

    public void setAutorID(String autorID) {
        this.autorID = autorID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
