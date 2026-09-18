package todo.repository;

import java.util.List;

import todo.model.NotaItem;

public interface NotaItemRepository {

    void guardar(NotaItem nota);
    void eliminar(NotaItem nota);
    List<NotaItem> obtenertodos();
}
