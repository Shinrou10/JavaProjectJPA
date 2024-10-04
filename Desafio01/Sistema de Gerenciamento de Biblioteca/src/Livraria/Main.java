
package Livraria;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Entidades.Livro;
import Entidades.Usuario;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o ID do usuário: ");
        String usuarioId = sc.nextLine();

        System.out.println("Digite o nome do usuário: ");
        String nome = sc.nextLine();

        System.out.println("Digite endereço do usuário: ");
        String endereco = sc.nextLine();

        System.out.println("Digite o email do usuário: ");
        String email = sc.nextLine();

        System.out.println("Digite o telefone do usuário: ");
        String telefone = sc.nextLine();

        System.out.println("Digite o nome do livro emprestado: ");
        String emprestimo = sc.nextLine();

        System.out.println("Insira a data de associação (formato dd/MM/yyyy):");
        String dataAssociacaoStr = sc.nextLine();
        Date dataAssociacao = Livro.converterStringParaData(dataAssociacaoStr);

        List<Livro> livrosEmprestados = new ArrayList<>();
        Usuario usuario = new Usuario(usuarioId, nome, endereco, email, telefone, emprestimo, dataAssociacao, livrosEmprestados);
        usuario.inserirUsuario();

        System.out.println("Insira o ISBN do livro: ");
        String isbn = sc.nextLine();

        System.out.println("Insira o título do livro: ");
        String titulo = sc.nextLine();

        System.out.println("Insira o nome do autor: ");
        String autor = sc.nextLine();

        System.out.println("Insira a data de publicação (formato dd/MM/yyyy): ");
        String dataPublicacaoStr = sc.nextLine();
        Date dataPublicacao = Livro.converterStringParaData(dataPublicacaoStr);  // Método que converte String para Date

        System.out.println("Insira o gênero do livro: ");
        String genero = sc.nextLine();

        System.out.println("Insira a quantidade de exemplares: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        Livro livro = new Livro(isbn,titulo, autor, dataPublicacao, genero, quantidade);
        livro.inserirLivro(); // Chama o método para inserir o banco de dados


        sc.close();
    }
}
