package todo.repository;

import java.util.ArrayList;
import java.util.List;

import todo.model.NotaItem;

public class MemoryNotaItemRepository implements INotaItemRepository{

    private final List<NotaItem> notas = new ArrayList<>();

    @Override
    public void guardar(NotaItem nota) {
        notas.add(nota);
    }

    @Override
    public void eliminar(NotaItem nota) {
        notas.remove(nota);
    }

    @Override
    public List<NotaItem> obtenertodos() {
        return List.copyOf(notas);
    }

}
