package todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotaItemService {
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public NotaItem crearNota(String texto){
        if (texto == null || texto.isBlank() ) {
            throw new IllegalArgumentException("el texto no puede estar vacio");
        }
        var textoLimpio = texto.trim();
        var fechaHora = LocalDateTime.now().format(formato);

        return  new NotaItem(textoLimpio, fechaHora);
    }
}
