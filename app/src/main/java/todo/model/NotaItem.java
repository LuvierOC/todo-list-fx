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

    public NotaItem(){

    }

    public NotaItem(String nombreNota, String fechaHora){
        this(null, nombreNota, fechaHora);
    }

    public NotaItem(String nombreNota){
        this.nombreNota = nombreNota;
        this.tags =  new ArrayList<>();
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


    public NotaItem( String nombreNota, String tags, String descripcion, String fechaHora) {
        this.nombreNota = nombreNota;
        addTags(tags);
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
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
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreNota() {
        return nombreNota;
    }

    public void setNombreNota(String nombreNota) {
        this.nombreNota = nombreNota;
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

    private void addTags(String cadenaTags){
        
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

    public void reemplazarTags(String cadenaTags) {
        this.tags.clear();
        addTags(cadenaTags);
    }

    public String getCadenaTags(){
        var listaItem = getTags();
        
        if (listaItem.isEmpty()) {
            return null;
        }
        var listaTags = listaItem;

        String cadenaNueva = "#";

        for (int i = 0; i < listaTags.size(); i++) {
            cadenaNueva = cadenaNueva +listaTags.get(i) + " #";
        }
        var resultado = cadenaNueva.substring(0, cadenaNueva.length() -2);
        return resultado;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "NotaItem [id=" + id + ", nombreNota=" + nombreNota + ", tags=" + tags + ", descripcion=" + descripcion
                + ", fechaHora=" + fechaHora + "]";
    }
}

