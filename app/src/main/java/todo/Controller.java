package todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<NotaItem> TableViewItems;


    @FXML
    private TableColumn<NotaItem, String> columnFecha;

    @FXML
    private TableColumn<NotaItem, String> columnNombre;


    @FXML
    private TextField txtInput;

    @FXML
    private void initialize() {
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("texto"));
    }

    @FXML
    void handleAgregar(ActionEvent event) {
        String item = txtInput.getText().trim();
        if (!item.isEmpty()) {
            // Generar fecha y hora actual
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaHoraFormateada = ahora.format(formato);

            // 4. Creamos el objeto NotaItem en lugar de pasar un String suelto
            NotaItem nuevaNota = new NotaItem(item, fechaHoraFormateada);
            
            TableViewItems.getItems().add(0, nuevaNota);
            txtInput.clear();
        }
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        NotaItem selectedItem = TableViewItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TableViewItems.getItems().remove(selectedItem);
        }
    }
}
