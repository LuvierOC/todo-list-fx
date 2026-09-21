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
    public void actualizar(NotaItem nota) {
        if (nota.getId() == null) {
            return;
        }
        for (int i = 0; i < notas.size(); i++) {
            if (nota.getId().equals(notas.get(i).getId())) {
                notas.set(i, nota);
                return;
            }
        }
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
