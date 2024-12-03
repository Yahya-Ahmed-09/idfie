package com.example.idfie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;


public class IDCardController {
    @FXML
    private ImageView profimageSc2;

    @FXML
    private Text idCardName;

    @FXML
    private Label idDept, idEmail, idFName, idNumber, idPhone, idBatch, idCampus;

    @FXML
    private ImageView qrCodeImage;

    InputStream fontFile = IDCardController.class.getResourceAsStream("Poppins-SemiBold.ttf");
    Font nameFont = Font.font("Poppins", FontWeight.BOLD, FontPosture.ITALIC, 30);
    Font regFont = Font.font("Poppins", FontWeight.BOLD, FontPosture.REGULAR, 15);
    public void setIdInfo(Image image, String name, String fName, String idNum, String batch, String campus, String dept, String email, String phone, String qrCodeFilePath){
        idCardName.setText(name);
        idCardName.setFont(nameFont);
        idFName.setText(fName);
        idFName.setFont(regFont);
        idNumber.setText(idNum);
        idNumber.setFont(regFont);
        idBatch.setText(batch);
        idBatch.setFont(regFont);
        idCampus.setText(campus);
        idCampus.setFont(regFont);
        idDept.setText(dept);
        idDept.setFont(regFont);
        idEmail.setText(email);
        idEmail.setFont(regFont);
        idPhone.setText(phone);
        idPhone.setFont(regFont);
        profimageSc2.setImage(image);
        File qrFile = new File(qrCodeFilePath);
        Image qrCode = new Image(qrFile.toURI().toString());
        qrCodeImage.setImage(qrCode);
    }

    @FXML
    private Button goBackbtn;
    private Parent root;
    @FXML
    private void goBackbtnfunc() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        Stage stage = (Stage) goBackbtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private Button pdfDownload;


    @FXML
    private Pane card;

    @FXML
    private Pane card1;
    @FXML
    private Label expDate, currDate;
    public void initialize(){

        LocalDate issueDate = LocalDate.now();
        currDate.setText(issueDate.toString());

        LocalDate expiryDate = issueDate.plusYears(2);
        expDate.setText(expiryDate.toString());
    }
    @FXML
    void onDownload(ActionEvent event) {
        // Get the bounds of the card panes
        Bounds bounds = card.getBoundsInLocal();
        Bounds bounds1 = card1.getBoundsInLocal();

        // Convert the Panes to images with higher DPI for better resolution
        SnapshotParameters params = new SnapshotParameters();
        params.setTransform(Transform.scale(2, 2)); // Set a higher DPI value

        // Create a PDF document
        PDDocument document = new PDDocument();

        try {
            // Snapshot and save the first card
            WritableImage snapshot = card.snapshot(params, null);
            File tempFile = new File("temp.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", tempFile);

            PDPage page = new PDPage(new PDRectangle((float) card.getWidth() * 2, (float) card.getHeight() * 2)); // Adjusted for higher resolution
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(PDImageXObject.createFromFile("temp.png", document), 0, 0, (float) card.getWidth() * 2, (float) card.getHeight() * 2);
            contentStream.close();
            tempFile.delete();

            // Snapshot and save the second card
            WritableImage snapshot1 = card1.snapshot(params, null);
            File tempFile1 = new File("temp1.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot1, null), "png", tempFile1);

            PDPage page1 = new PDPage(new PDRectangle((float) card1.getWidth() * 2, (float) card1.getHeight() * 2)); // Adjusted for higher resolution
            document.addPage(page1);
            PDPageContentStream contentStream1 = new PDPageContentStream(document, page1);
            contentStream1.drawImage(PDImageXObject.createFromFile("temp1.png", document), 0, 0, (float) card1.getWidth() * 2, (float) card1.getHeight() * 2);
            contentStream1.close();
            tempFile1.delete();

            // Save the PDF file using a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("c:\\"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                document.save(selectedFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}