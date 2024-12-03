package com.example.idfie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Dialogbox {
    @FXML
    private Button okBtn;
    @FXML
    private Label msg;

    @FXML
    void onClickToClose(ActionEvent event) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public void setMsg(String message) {
        msg.setText(message);
    }
}
