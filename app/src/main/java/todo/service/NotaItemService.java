package todo.service;



import java.util.List;
import todo.model.NotaItem;
import todo.repository.SqliteNotaRepository;

public class NotaItemService {

    private SqliteNotaRepository repo = new SqliteNotaRepository();

    public NotaItem guardar(String texto){
        if (texto == null || texto.isBlank() ) {
            throw new IllegalArgumentException("el texto no puede estar vacio");
        }
        var textoLimpio = texto.trim();
        var nuevaNota = repo.guardar(textoLimpio);
        return  nuevaNota;
    }

    public void actualizar(NotaItem notaItem){
        repo.actualizar(notaItem);

    }

    public boolean eliminarNota(NotaItem nota){
        return repo.eliminar(nota);
    }
    public List<NotaItem> obtenerNotas(){
        return repo.obtenertodos();
    }
}
