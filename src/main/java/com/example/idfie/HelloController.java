package com.example.idfie;

import com.mongodb.client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.ImageView;
import org.bson.Document;

public class HelloController {

    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField fatherName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField dept;
    @FXML
    private TextField batch;
    @FXML
    private TextField campus;
    @FXML
    public Button IdGenerator;

    @FXML
    private Label noFIle;

    @FXML
    private ImageView userPicture;

    User user  = new User();

    @FXML
    public void uploadBtnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("c:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"), new FileChooser.ExtensionFilter("PNG image", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            userPicture.setImage(image);
            userPicture.setFitWidth(120);
            userPicture.setFitHeight(120);
        }
        else {
            noFIle.setText("No file is selected");
        }
    }


    private Parent root;
    public void generateID(ActionEvent event) throws IOException {
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
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IDcard.fxml"));
            root = loader.load();
            IDCardController idCardController = loader.getController();
            idCardController.setIdInfo(userPicture.getImage(), user.firstName, user.fatherName, user.idNo, user.batchNo, user.campus, user.department, user.emailAddress, user.phoneNumber);

            //            MongoDb Connection

//            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
//                MongoDatabase database = mongoClient.getDatabase("idgenerator");
//                MongoCollection<Document> collection = database.getCollection("users");
//
//                // Check if the ID_No already exists in the database
//                Document query = new Document("ID_No", user.idNo);
//                FindIterable<Document> result = collection.find(query);
//                if (result.first() != null) {
//                    System.out.println("ID already exists in the database. Cannot create a new document.");
//                } else {
//                    // ID does not exist, create a new document
//                    Document doc = new Document("ID_No", user.idNo)
//                            .append("father_name", user.fatherName)
//                            .append("name", user.firstName)
//                            .append("email", user.emailAddress)
//                            .append("phone_number", user.phoneNumber)
//                            .append("department", user.department)
//                            .append("campus", user.campus)
//                            .append("batch", user.batchNo);
//
//                    collection.insertOne(doc);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

}