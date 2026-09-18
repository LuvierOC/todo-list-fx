package todo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import todo.model.NotaItem;

public class NotaItemService {
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final List<NotaItem> notas = new ArrayList<>();

    public NotaItem crearNota(String texto){
        if (texto == null || texto.isBlank() ) {
            throw new IllegalArgumentException("el texto no puede estar vacio");
        }
        var textoLimpio = texto.trim();
        var fechaHora = LocalDateTime.now().format(formato);
        var nuevaNota =  new NotaItem(textoLimpio, fechaHora);
        notas.add(nuevaNota);        
        return  nuevaNota;
    }

    public void eliminarNota(NotaItem nota){
        notas.remove(nota);
    }
    public List<NotaItem> obtenerNotas(){
        return List.copyOf(notas);
    }
}
