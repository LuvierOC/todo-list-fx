package todo.repository;

import java.util.List;

import todo.model.NotaItem;

public interface INotaItemRepository {

    NotaItem guardar(String nueva);
    void actualizar(NotaItem nota);
    boolean eliminar(NotaItem nota);
    List<NotaItem> obtenertodos();
}
