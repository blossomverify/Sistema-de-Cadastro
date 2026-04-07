import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Evento> eventos = new ArrayList<>();
        ArrayList<Evento> eventosConfirmados = new ArrayList<>();
        try {
            FileReader reader = new FileReader("eventos.dados");
            Scanner leitor = new Scanner(reader);
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                if (linha.length() < 3) continue;
                String[] partes = linha.split(";");
                if (partes.length < 5) continue;
                String nomeEvento = partes[0];
                String endereco = partes[1];
                String categoria = partes[2];
                LocalDateTime horario = LocalDateTime.parse(partes[3]);
                String descricao = partes[4];
                Evento evento = new Evento(nomeEvento, endereco, categoria, horario, descricao);
                eventos.add(evento);
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Arquivo de dados nao encontrado, comecando do zero!");
        }
        System.out.println("Bem vindo ao app de eventos de Osasco!");
        System.out.println("Qual o seu nome?");
        String nome = scanner.nextLine();
        System.out.println("Digite o seu email:");
        String email = scanner.nextLine();
        System.out.println("Qual o seu telefone?");
        String telefone = scanner.nextLine();
        String cidade = "Osasco";
        Usuario usuario = new Usuario(nome, email, telefone);
        System.out.println("\nMeu perfil atual:");
        System.out.println("Nome: " + usuario.nome);
        System.out.println("Email: " + usuario.email);
        System.out.println("Telefone: " + usuario.telefone);
        System.out.println("Cidade que eu moro: " + cidade);
        int opcao = 0;
        do {
            System.out.println("\n--------------------------------");
            System.out.println("1 - Mostrar todos os eventos");
            System.out.println("2 - Quero participar de um");
            System.out.println("3 - Adicionar um evento novo");
            System.out.println("4 - Quero sair de um evento");
            System.out.println("5 - Os eventos que vou");
            System.out.println("0 - Sair do app");
            System.out.print("Sua escolha: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) { 
                System.out.println("Poxa vida vc digitou letra onde so podia numero!");
                opcao = -1;
                continue;
            }
            if (opcao == 3) {
                System.out.println("Qual vai ser o nome do evento?");
                String nomeDoEvento = scanner.nextLine();
                System.out.println("Qual destas listonas o cara pode tentar?:");
                System.out.println("1 - Festa");
                System.out.println("2 - Show");
                System.out.println("3 - Esportivo");
                System.out.println("4 - Teatro");
                System.out.println("5 - Conferência");
                System.out.println("6 - Exposição");
                System.out.println("7 - Comunitário");
                int escolhaCat = Integer.parseInt(scanner.nextLine());
                String categoria;
                switch (escolhaCat) {
                    case 1:
                        categoria = "Festa";
                        break;
                    case 2:
                        categoria = "Show";
                        break;
                    case 3:
                        categoria = "Esportivo";
                        break;
                    case 4:
                        categoria = "Teatro";
                        break;
                    case 5:
                        categoria = "Conferência";
                        break;
                    case 6:
                        categoria = "Exposição";
                        break;
                    case 7:
                        categoria = "Comunitario";
                        break;
                    default:
                        categoria = "Festa";
                        System.out.println("Não entendi a opção de lugar, entao preenchi Festa!");
                }
                System.out.println("Legal, e onde deve ocorer?");
                String endereco = scanner.nextLine();
                System.out.println("Dia que ocorrerá em ano-mes-dia e Hora Ex: AAAA-MM-DD HH:MM ?");
                String horarioStr = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime horario = LocalDateTime.parse(horarioStr, formatter); 
                System.out.println("Manda pros cara um resumo maneiro sobre o q eh!");
                String descricao = scanner.nextLine();
                Evento evento = new Evento(nomeDoEvento, endereco, categoria, horario, descricao);
                eventos.add(evento);
                try {
                    FileWriter writer = new FileWriter("eventos.dados", true);
                    writer.write(evento.nomeDoEvento + ";" +
                            evento.endereco + ";" +
                            evento.categoria + ";" +
                            evento.horario + ";" +
                            evento.descricao + "\n");
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Deu problema ao salvar, so vai fika em memório atual.");
                }
                System.out.println("\nEee! Mais um Eventão gerado.");
            }
            else if (opcao == 1) {
                eventos.sort((e1, e2) -> e1.horario.compareTo(e2.horario));
                if (eventos.isEmpty()) {
                    System.out.println("No momento osasco dorme sem roles!");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    for (int i = 0; i < eventos.size(); i++) {
                        Evento e = eventos.get(i);
                        System.out.println("\nEvento Numero -> (" + (i + 1) + ") <- Nome:" + e.nomeDoEvento);
                        System.out.println("Endereço: " + e.endereco);
                        System.out.println("Essa aba se trata do genêro ->" + e.categoria);
                        System.out.println("Data Prevista ->" + e.horario.format(formatter));
                        System.out.println("E que ele tem haver -> \n" + e.descricao);
                    }
                }
            }
            else if (opcao == 2) {
                if (eventos.isEmpty()) {
                    System.out.println("Cade os role? Tenta adc la fora!");
                } else {
                    for (int i = 0; i < eventos.size(); i++) {
                        System.out.println("Qual tu vai? (" + (i + 1) + ") pra " + eventos.get(i).nomeDoEvento);
                    }
                    int escolha = Integer.parseInt(scanner.nextLine());
                    if (escolha > 0 && escolha <= eventos.size()) {
                        eventosConfirmados.add(eventos.get(escolha - 1));
                        System.out.println("Legal, eu te adicionei na sua agenda local!");
                    } else {
                        System.out.println("Número sem lógica doido...");
                    }
                }
            }
            else if (opcao == 5) {
                if (eventosConfirmados.isEmpty()) {
                    System.out.println("Sua lista vip ta no ZERO.");
                } else {
                    for (int i = 0; i < eventosConfirmados.size(); i++) {
                        System.out.println("  Seus Planos =>  " + (i + 1) + ". Nome:  " + eventosConfirmados.get(i).nomeDoEvento + " \n");
                    }
                }
            }
            else if (opcao == 4) {
                if (eventosConfirmados.isEmpty()) {
                    System.out.println("Lista vip ja esta em 0");
                } else {
                    for (int i = 0; i < eventosConfirmados.size(); i++) {
                        System.out.println((i + 1) + ". O Seu " + eventosConfirmados.get(i).nomeDoEvento);
                    }
                    System.out.println("E ai digita o q vc nao quee nem pago q a gente apaga sua presensa...");
                    int escolha = Integer.parseInt(scanner.nextLine());
                    if (escolha > 0 && escolha <= eventosConfirmados.size()) {
                        eventosConfirmados.remove(escolha - 1);
                        System.out.println("Vazamos de evento.");
                    } else {
                        System.out.println("Tentou excluir vento nao existe id");
                    }
                }
            }
        } while (opcao != 0);
        System.out.println("Sistema Finalizado... Ate q deu pouco BO!");
        scanner.close();
    }
}
