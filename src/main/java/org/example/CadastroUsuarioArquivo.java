import java.io.*;
import java.util.Scanner;

public class CadastroUsuarioArquivo {
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("=== Sistema de Cadastro de Usuários ===");

        while (continuar) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Criar Conta");
            System.out.println("2 - Mostrar Usuários");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> criarConta(scanner);
                case 2 -> mostrarUsuarios();
                case 0 -> {
                    System.out.println("Saindo do sistema...");
                    continuar = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    // Método para criar uma conta de usuário
    private static void criarConta(Scanner scanner) {
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();

        // Verificar se o nome de usuário já existe no arquivo
        if (usuarioJaExiste(nomeUsuario)) {
            System.out.println("Erro: Nome de usuário já está em uso. Escolha outro.");
            return;
        }

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        // Salvar o novo usuário no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS, true))) {
            writer.write(nomeUsuario + "," + senha);
            writer.newLine();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

    // Método para verificar se um nome de usuário já está cadastrado
    private static boolean usuarioJaExiste(String nomeUsuario) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equals(nomeUsuario)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe, então o usuário não está cadastrado
            return false;
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return false;
    }

    // Método para mostrar todos os usuários cadastrados
    private static void mostrarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            System.out.println("Usuários cadastrados:");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                System.out.println("- " + dados[0]); // Exibir apenas o nome do usuário
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum usuário cadastrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
