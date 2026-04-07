import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String ARQUIVO_DADOS = "eventos.dados";
    private static List<Evento> eventos = new ArrayList<>();
    private static Usuario usuarioLogado = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarEventos();
        realizarLogin();
        menuPrincipal();
        scanner.close();
    }

    // ------------------------------------------------------------------ Login

    private static void realizarLogin() {
        System.out.println("===========================================");
        System.out.println("  Sistema de Cadastro de Eventos");
        System.out.println("===========================================");
        System.out.println("Bem-vindo! Informe seus dados para continuar.\n");

        String nome = "";
        while (nome.isEmpty()) {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) System.out.println("Nome não pode ser vazio.");
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.print("E-mail: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) System.out.println("E-mail não pode ser vazio.");
        }

        String telefone = "";
        while (telefone.isEmpty()) {
            System.out.print("Telefone: ");
            telefone = scanner.nextLine().trim();
            if (telefone.isEmpty()) System.out.println("Telefone não pode ser vazio.");
        }

        usuarioLogado = new Usuario(nome, email, telefone);
        System.out.println("\nOlá, " + usuarioLogado.getNome() + "! Login realizado com sucesso.\n");
    }

    // --------------------------------------------------------- Menu principal

    private static void menuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("===========================================");
            System.out.println("  MENU PRINCIPAL");
            System.out.println("===========================================");
            System.out.println("1. Cadastrar novo evento");
            System.out.println("2. Listar eventos");
            System.out.println("3. Entrar na lista VIP de um evento");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();
            switch (opcao) {
                case 1:
                    cadastrarEvento();
                    break;
                case 2:
                    listarEventos();
                    break;
                case 3:
                    entrarNaListaVip();
                    break;
                case 0:
                    System.out.println("Até logo, " + usuarioLogado.getNome() + "!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
    }

    // ------------------------------------------------------- Cadastrar evento

    private static void cadastrarEvento() {
        System.out.println("--- Cadastrar Novo Evento ---");

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Categoria (ex: Show, Esporte, Teatro): ");
        String categoria = scanner.nextLine().trim();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine().trim();

        System.out.print("Horário (ex: 10/06/2025 às 19h): ");
        String horario = scanner.nextLine().trim();

        Evento evento = new Evento(nome, categoria, endereco, horario);
        eventos.add(evento);
        salvarEventos();

        System.out.println("\nEvento \"" + nome + "\" cadastrado com sucesso!\n");
    }

    // --------------------------------------------------------- Listar eventos

    private static void listarEventos() {
        System.out.println("--- Eventos Cadastrados ---");

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado ainda.\n");
            return;
        }

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.println((i + 1) + ". " + e);
            List<String> vips = e.getListaVip();
            if (!vips.isEmpty()) {
                System.out.println("   Lista VIP: " + String.join(", ", vips));
            }
        }
        System.out.println();
    }

    // ---------------------------------------------------- Entrar na lista VIP

    private static void entrarNaListaVip() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento disponível para entrar na lista VIP.\n");
            return;
        }

        System.out.println("--- Entrar na Lista VIP ---");
        listarEventos();

        System.out.print("Digite o número do evento: ");
        int numero;
        try {
            numero = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.\n");
            return;
        }

        if (numero < 1 || numero > eventos.size()) {
            System.out.println("Evento não encontrado.\n");
            return;
        }

        Evento evento = eventos.get(numero - 1);

        if (evento.getListaVip().contains(usuarioLogado.getNome())) {
            System.out.println("Você já está na lista VIP deste evento.\n");
            return;
        }

        evento.adicionarVip(usuarioLogado.getNome());
        salvarEventos();
        System.out.println("Você foi adicionado à lista VIP de \"" + evento.getNome() + "\"!\n");
    }

    // ------------------------------------------------ Persistência em arquivo

    private static void salvarEventos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (Evento evento : eventos) {
                writer.write(evento.serializar());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void carregarEventos() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    Evento evento = Evento.desserializar(linha);
                    if (evento != null) {
                        eventos.add(evento);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }
}
