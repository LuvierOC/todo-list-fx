package todo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotaItem {
    private Long id;
    private String nombreNota;
    private List<String> tags;
    private String descripcion;
    private String fechaHora;

    public NotaItem(String nombreNota, String fechaHora){
        this(null, nombreNota, fechaHora);
    }
    
    public NotaItem(Long id, String nombreNota, String fechaHora) {
        this.id = id;
        this.nombreNota = nombreNota;
        this.fechaHora = fechaHora;
        this.tags = new ArrayList<>();
    }

    public NotaItem(Long id, String nombreNota, String descripcion, String fechaHora) {
        this.id = id;
        this.nombreNota = nombreNota;
        this.descripcion = descripcion;
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
    public String getNombreNota() {
        return nombreNota;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
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

    public String getCadenaTags(){
        var listaTags = this.getTags();
        String cadenaTags = "#";
        for (String tag : listaTags) {
            cadenaTags = cadenaTags + tag;
            cadenaTags = " #" + cadenaTags;
        }
        return cadenaTags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

