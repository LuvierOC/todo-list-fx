package todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import todo.model.NotaItem;
import todo.service.NotaItemService;

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

    private NotaItem nota;

    private final NotaItemService notaItemService = new NotaItemService();

    private Runnable alGuardar;

    public void setNota(NotaItem notaSeleccionada){
        this.nota = notaSeleccionada;
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
        // 1. Obtenemos el Stage actual usando el mismo botón que disparó el evento
        Stage stage = (Stage) btnAnclar.getScene().getWindow();
        
        // 2. Evaluamos el estado actual para alternarlo (Toggle)
        if (stage.isAlwaysOnTop()) {
            stage.setAlwaysOnTop(false);
            btnAnclar.setText("Anclar"); // Opcional: cambias el texto si quieres
        } else {
            stage.setAlwaysOnTop(true);
            btnAnclar.setText("Desanclar"); // Opcional
        }
    }

    @FXML
    void actionExtraer(ActionEvent event) {

    }

    public void setAlGuardar(Runnable alGuardar) {
        this.alGuardar = alGuardar;
    }

    @FXML
    void actionGuardar(ActionEvent event) {
        if (nota == null) return;
        nota.setNombreNota(txtNota.getText());
        nota.setDescripcion(areaDesc.getText().isBlank() ? null : areaDesc.getText());
        nota.reemplazarTags(txtTags.getText());
        notaItemService.actualizar(nota);

        if (alGuardar != null) {
            alGuardar.run();
        }

        System.out.println("Guardado");
    }

    @FXML
    void actionSalir(ActionEvent event) {
        Stage stage = (Stage)btnSalir.getScene().getWindow();
        stage.close();
    }

}
