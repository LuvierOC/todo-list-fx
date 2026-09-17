package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<String> TableViewItems;

    @FXML
    private TextField txtInput;

    @FXML
    void handleAgregar(ActionEvent event) {
        String item = txtInput.getText().trim();
        if (!item.isEmpty()) {
            TableViewItems.getItems().add(0, item);
            txtInput.clear();
        }
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        TableViewItems.getItems().remove(TableViewItems.getSelectionModel().getSelectedItem());
    }
}
