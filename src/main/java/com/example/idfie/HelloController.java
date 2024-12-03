package com.example.idfie;

import com.google.zxing.WriterException;
import com.mongodb.client.*;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javafx.scene.image.ImageView;
import org.bson.Document;

public class HelloController {

    @FXML
    private TextField id, firstName, fatherName, email, phone, dept, batch, campus;

    @FXML
    public Button IdGenerator;

    @FXML
    private Label noFIle, expDate, currDate;

    @FXML
    private ImageView userPicture;

    @FXML
    public Button delImage;

    String emailText = null;
    @FXML
    private Text invalidEmail;



    @FXML
    public void checkEmail(javafx.scene.input.KeyEvent keyEvent) {

        emailText = email.getText();
        if(!isValid(emailText)){
//
            invalidEmail.setVisible(true);
        }else{
//
            invalidEmail.setVisible(false);
        }
    }
    private boolean isValid(String emailText){
        String emailRegex = "^[a-zA-z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-z0-9-]+\\.)+[a-z"+
                            "A=z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if(email == null){
            return false;
        }
        return pat.matcher(emailText).matches();
    }


    User user  = new User();

    public void initialize() {
        delImage.setVisible(false);
        invalidEmail.setVisible(false);
        phone.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });



    }
    @FXML
    public void uploadBtnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("c:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"), new FileChooser.ExtensionFilter("PNG image", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        delImage.setVisible(false);
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            userPicture.setImage(image);
            userPicture.setFitWidth(120);
            userPicture.setFitHeight(120);
            noFIle.setVisible(false);
            delImage.setVisible(true);
        }
        else {
            noFIle.setText("No file is selected");
            delImage.setVisible(true);
        }
    }

    @FXML
    void delimageCross(ActionEvent event) {
        userPicture.setImage(null);
        delImage.setVisible(false);
    }
    public void delimageCross1(MouseEvent mouseEvent) {
        userPicture.setImage(null);
        delImage.setVisible(false);
    }

    private Parent root;
    public void generateID(ActionEvent event) throws IOException, WriterException {


        user.idNo = id.getText();
        user.firstName = firstName.getText();
        user.fatherName = fatherName.getText();
        user.phoneNumber = phone.getText();
        user.emailAddress = email.getText();
        user.batchNo = batch.getText();
        user.campus = campus.getText();
        user.department = dept.getText();

        if (userPicture.getImage() == null || user.idNo.isEmpty() || user.firstName.isEmpty() || user.fatherName.isEmpty() || user.phoneNumber.isEmpty() || user.emailAddress.isEmpty() || user.batchNo.isEmpty() || user.campus.isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialogbox.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Warning");
            stage.setScene(new Scene(root1));
            stage.show();
        } else if(!isValid(emailText)){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialogbox.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Dialogbox dialogbox = fxmlLoader.getController();
            dialogbox.setMsg("Please Enter Valid Email Address!");
            stage.setTitle("Warning");
            stage.setScene(new Scene(root1));
            stage.show();
        }else if(phone.getText().length() != 11){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialogbox.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Dialogbox dialogbox = fxmlLoader.getController();
            dialogbox.setMsg("Phone Number Must Be 11 Digits!");
            stage.setTitle("Warning");
            stage.setScene(new Scene(root1));
            stage.show();
        }else {
            QRGenerator qrGenerator = new QRGenerator();
            String qrData = "ID: " + user.idNo +
                    "\nName: " + user.firstName +
                    "\nFather Name: " + user.fatherName +
                    "\nEmail: " + user.emailAddress +
                    "\nPhone: " + user.phoneNumber +
                    "\nBatch: "+user.batchNo +
                    "\nCampus: " + user.campus+
                    "\nDepartment: "+ user.department+
                    "\n" +
                    "\n\nThis data was generated by IDFIE Card Generator.";

            String qrFilePath = "@../../../images/";
            qrGenerator.generateQRCode(qrData, qrFilePath);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("IDcard.fxml"));
            root = loader.load();
            IDCardController idCardController = loader.getController();
            idCardController.setIdInfo(userPicture.getImage(), user.firstName, user.fatherName, user.idNo, user.batchNo, user.campus, user.department, user.emailAddress, user.phoneNumber, qrFilePath);




            //            MongoDb Connection

            MongoConnection connection = new MongoConnection();
            connection.connectToMongo(user.idNo,user.firstName, user.fatherName, user.emailAddress, user.phoneNumber, user.department, user.campus, user.batchNo);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }



}