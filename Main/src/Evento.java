import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evento {
    private String nome;
    private String categoria;
    private String endereco;
    private String horario;
    private List<String> listaVip;

    public Evento(String nome, String categoria, String endereco, String horario) {
        this.nome = nome;
        this.categoria = categoria;
        this.endereco = endereco;
        this.horario = horario;
        this.listaVip = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getHorario() {
        return horario;
    }

    public List<String> getListaVip() {
        return Collections.unmodifiableList(listaVip);
    }

    public void adicionarVip(String nomeParticipante) {
        listaVip.add(nomeParticipante);
    }

    // Serializa o evento para salvar no arquivo .dados
    public String serializar() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(";")
          .append(categoria).append(";")
          .append(endereco).append(";")
          .append(horario).append(";");
        for (int i = 0; i < listaVip.size(); i++) {
            sb.append(listaVip.get(i));
            if (i < listaVip.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // Desserializa uma linha do arquivo .dados para um objeto Evento
    public static Evento desserializar(String linha) {
        String[] partes = linha.split(";", -1);
        if (partes.length < 4) return null;
        Evento evento = new Evento(partes[0], partes[1], partes[2], partes[3]);
        if (partes.length >= 5 && !partes[4].isEmpty()) {
            String[] vips = partes[4].split(",");
            for (String vip : vips) {
                if (!vip.isEmpty()) {
                    evento.adicionarVip(vip);
                }
            }
        }
        return evento;
    }

    @Override
    public String toString() {
        return "Evento: " + nome
                + " | Categoria: " + categoria
                + " | Endereço: " + endereco
                + " | Horário: " + horario;
    }
}
