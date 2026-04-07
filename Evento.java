import java.time.LocalDateTime;
public class Evento {
    public String nomeDoEvento;
    public String endereco;
    public String categoria;
    public LocalDateTime horario;
    public String descricao;
    public Evento(String nomeDoEvento, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nomeDoEvento = nomeDoEvento;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }
}
