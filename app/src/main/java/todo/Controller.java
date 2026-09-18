package todo;

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

    private final NotaItemService notaItemService = new NotaItemService();

    @FXML
    private void initialize() {
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("texto"));
    }

    @FXML
    void handleAgregar(ActionEvent event) {
        try {
            var nuevaNota = notaItemService.crearNota(txtInput.getText());
            TableViewItems.getItems().add(0,nuevaNota);
            txtInput.clear();
        } catch (IllegalArgumentException e) {
            // TODO: handle exception
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
