package todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

    @FXML
    private CheckBox checkNota;

    @FXML
    private CheckBox checkTag;

        @FXML
    private TextField txtNota;

    @FXML
    private TextField txtTags;

    private final NotaItemService notaItemService = new NotaItemService();

    @FXML
    private void initialize() {
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("texto"));
        
        txtInput.addEventFilter(KeyEvent.KEY_PRESSED, this::enfocarTableView);
        checkNota.setOnAction(this::manejarSeleccion);
        checkTag.setOnAction(this::manejarSeleccion);
    }

    private void manejarSeleccion(ActionEvent event){
        CheckBox checkPresionado = (CheckBox) event.getSource();

        if (checkPresionado.isSelected()) {
            if (checkPresionado == checkNota) {
                checkTag.setSelected(false);
                txtTags.setDisable(true);
                txtNota.setDisable(false);
            }else if (checkPresionado == checkTag) {
                checkNota.setSelected(false);
                txtNota.setDisable(true);
                txtTags.setDisable(false);
            }
            
        }else{
            txtNota.setDisable(true);
            txtTags.setDisable(true);
        }

    }

    private void enfocarTableView(KeyEvent event) {
        
        if (event.getCode() != KeyCode.TAB) {
        return;
        }
        
        if (TableViewItems.getItems().isEmpty()) {
            return;
        }

        event.consume();

        TableViewItems.requestFocus();

        int selectedIndex = TableViewItems.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            selectedIndex = 0;
            TableViewItems.getSelectionModel().select(selectedIndex);
        }

        TableViewItems.getFocusModel().focus(selectedIndex);
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
