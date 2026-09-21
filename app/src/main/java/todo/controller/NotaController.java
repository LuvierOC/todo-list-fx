package todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import todo.model.NotaItem;

public class NotaController {

    @FXML
    private TextArea areaDesc;

    @FXML
    private Button btnAnclar;

    @FXML
    private Button btnExtraer;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtNota;

    @FXML
    private TextField txtTags;

    public void setNota(NotaItem nota){
        txtNota.setText(nota.getNombreNota());
        txtTags.setText(nota.getCadenaTags());
        if (nota.getDescripcion() == null || nota.getDescripcion().isEmpty()) {
            areaDesc.setText("# " + nota.getNombreNota());
        }else{
            areaDesc.setText(nota.getDescripcion());
        }
    }

    @FXML
    void actionAnclar(ActionEvent event) {

    }

    @FXML
    void actionExtraer(ActionEvent event) {

    }

    @FXML
    void actionGuardar(ActionEvent event) {

    }

    @FXML
    void actionSalir(ActionEvent event) {

    }

}
