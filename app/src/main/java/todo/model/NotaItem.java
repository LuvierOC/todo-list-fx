package todo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotaItem {
    private Long id;
    private String texto;
    private List<String> tags;
    private String fechaHora;

    public NotaItem(String texto, String fechaHora){
        this(null, texto, fechaHora);
    }
    
    public NotaItem(Long id, String texto, String fechaHora) {
        this.id = id;
        this.texto = texto;
        this.fechaHora = fechaHora;
        this.tags = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }
    public String getTexto() {
        return texto;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTags(String cadenaTags){
        if (cadenaTags == null || cadenaTags.isEmpty()){
            return;
        }
        Pattern patron = Pattern.compile("#([\\w\\d._-]+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = patron.matcher(cadenaTags);

        while (matcher.find()) {
            String tagEncontrado = matcher.group(1);
            if (!this.tags.contains(tagEncontrado)) {
                this.tags.add(tagEncontrado);
            }
        }
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

