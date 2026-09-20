package todo.repository;

import java.util.List;

import todo.model.NotaItem;

public interface INotaItemRepository {

    void guardar(NotaItem nota);
    void eliminar(NotaItem nota);
    List<NotaItem> obtenertodos();
}
