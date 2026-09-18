package todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import todo.model.NotaItem;
import todo.service.NotaItemService;

public class TodoController {
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

    private void agregar(){
        try {
            var nuevaNota = notaItemService.crearNota(txtInput.getText());
            TableViewItems.getItems().add(0,nuevaNota);
            txtInput.clear();
        } catch (IllegalArgumentException e) {

        }
    }

    private void eliminar(){
        NotaItem selectedItem = TableViewItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            notaItemService.eliminarNota(selectedItem);
            TableViewItems.getItems().remove(selectedItem);
        }else{
            System.out.println("no hay ninguna nota que eliminar");
        }
    }

    @FXML
    void handleAgregar(ActionEvent event) {
        agregar();
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        eliminar();
    }

    @FXML
    void agregarNotaKey(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            agregar();
        }
    }

    @FXML
    void eliminarNotaKey(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            eliminar();
        }
    }
}
