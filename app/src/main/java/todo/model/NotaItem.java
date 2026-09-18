package todo.model;

public class NotaItem {
    private String texto;
    private String fechaHora;


    public NotaItem(String texto, String fechaHora) {
        this.texto = texto;
        this.fechaHora = fechaHora;
    }

    public String getFechaHora() {
        return fechaHora;
    }
    public String getTexto() {
        return texto;
    }

}

