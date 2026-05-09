// Utilizând aceiași temă ca la lucrarea de laborator nr. 1 creați o interfață grafică 
// care permite crearea, distrugerea, prelucrarea obiectelor. Pentru notă maximă trebuie de 
// înscris datele în fișier.

import javafx.collections.FXCollections;
import javafx.collections.ObservableList; // trigger data change in real time (ui application)
import javafx.application.Application;
import javafx.application.Platform; // just for the focus thing
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

// scene
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

// ui and controls
import javafx.scene.control.ListView; // visual container for the ObservableList
import javafx.scene.control.TextField; // input label for typing
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.util.*;

public class App extends Application {
  private FileManager fileService;
  private String fileName;

  @Override
  public void start(Stage primaryStage) {
    GridPane gridPane = new GridPane();
    HBox lineBoxCreateFile = new HBox(30);
    HBox lineBoxEditFile = new HBox(30);

    // this doesn't work
    // gridPane.setFocusTraversable(true);
    // gridPane.requestFocus();

    Platform.runLater(() -> gridPane.requestFocus());

    // UI COMPONENTS ===============================================================
    Label greetingLabel = new Label("Welcome to my project");
    Label messageLabel = new Label("Message: This is the welcome stage. Create a file to begin.");
    Label statusLabel = new Label("Status: Good");
    Button createFileButton = new Button("Create File");
    Button addDataButton = new Button("Add Data");
    Button viewFileButton = new Button("View File");
    Button editFileButton = new Button("Edit File By ID");
    Button deleteFileButton = new Button("Delete File");
    Button executeTaskButton = new Button("Execute Task");
    Button exitProgramButton = new Button("Exit");

    // TODO: maybe add a clear buttons like for the fields

    // text fields
    TextField fileNameField = new TextField();
    TextField nrField = new TextField();
    TextField fNameField = new TextField();
    TextField lNameField = new TextField();
    TextField addressField = new TextField();
    TextField phoneField = new TextField();
    TextField profileField = new TextField();
    TextField yearField = new TextField();
    TextField gradeField = new TextField();

    TextField findIdField = new TextField();
    TextField editNrField = new TextField();
    TextField editFNameField = new TextField();
    TextField editLNameField = new TextField();
    TextField editAddressField = new TextField();
    TextField editPhoneField = new TextField();
    TextField editProfileField = new TextField();
    TextField editYearField = new TextField();
    TextField editGradeField = new TextField();

    // placeholders
    fileNameField.setPromptText("e.g. elevi");
    nrField.setPromptText("000");
    editNrField.setPromptText("000");
    fNameField.setPromptText("Alan");
    editFNameField.setPromptText("Alan");
    lNameField.setPromptText("Turing");
    editLNameField.setPromptText("Turing");
    addressField.setPromptText("New Bridge 5/6 st.");
    editAddressField.setPromptText("New Bridge 5/6 st.");
    phoneField.setPromptText("1234 56 789");
    editPhoneField.setPromptText("1234 56 789");
    profileField.setPromptText("Science");
    editProfileField.setPromptText("Science");
    yearField.setPromptText("1-12");
    editYearField.setPromptText("1-12");
    gradeField.setPromptText("4.00-10.00");
    editGradeField.setPromptText("4.00-10.00");
    findIdField.setPromptText("ID");

    // disable initial buttons
    createFileButton.disableProperty().bind(fileNameField.textProperty().isEmpty());
    addDataButton.disableProperty().bind(nrField.textProperty().isEmpty()
        .or(fNameField.textProperty().isEmpty())
        .or(lNameField.textProperty().isEmpty())
        .or(addressField.textProperty().isEmpty())
        .or(phoneField.textProperty().isEmpty())
        .or(profileField.textProperty().isEmpty())
        .or(yearField.textProperty().isEmpty())
        .or(gradeField.textProperty().isEmpty())
        .or(fileNameField.textProperty().isEmpty()));
    viewFileButton.disableProperty().bind(fileNameField.textProperty().isEmpty());
    deleteFileButton.disableProperty().bind(findIdField.textProperty().isEmpty());
    editFileButton.disableProperty().bind(findIdField.textProperty().isEmpty()
        .or(fileNameField.textProperty().isEmpty())
        .or(editNrField.textProperty().isEmpty())
        .or(editLNameField.textProperty().isEmpty())
        .or(editFNameField.textProperty().isEmpty())
        .or(editAddressField.textProperty().isEmpty())
        .or(editPhoneField.textProperty().isEmpty())
        .or(editProfileField.textProperty().isEmpty())
        .or(editYearField.textProperty().isEmpty())
        .or(editGradeField.textProperty().isEmpty()));
    executeTaskButton.disableProperty().bind(fileNameField.textProperty().isEmpty());

    // FUNCTIONALITY =====================================
    createFileButton.setOnAction(event -> { // create file
      fileService = new FileManager(fileNameField.getText());
      fileService.createFile();

      statusLabel.setText("Status: " + (fileService.getStatus() == "Success"));
      messageLabel.setText("Message: " + fileService.getMessage());

      if (fileService.getStatus() == "Success") {
        addDataButton.disableProperty().unbind();
        addDataButton.setDisable(false);
        deleteFileButton.disableProperty().unbind();
        deleteFileButton.setDisable(false);
        createFileButton.disableProperty().unbind();
        createFileButton.setDisable(true);

        // fileNameField.clear();
        fileNameField.setEditable(false);
      }
    });

    addDataButton.setOnAction(event -> { // add
      try {
        String nr = nrField.getText();
        String fName = fNameField.getText();
        String lName = lNameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String profile = profileField.getText();
        int year = Integer.parseInt(yearField.getText());
        double grade = Double.parseDouble(gradeField.getText());

        String rawData = String.format("%s, %s, %s, %s, %s, %s, %d, %.2f",
            nr, fName, lName, address, phone, profile, year, grade); // to no reinvent the wheel, this is what fm is
                                                                     // expecting

        fileService.addData(rawData);
        statusLabel.setText("Status: " + fileService.getStatus());
        messageLabel.setText("Message: " + fileService.getMessage());

        // clearing fields
        nrField.clear();
        fNameField.clear();
        lNameField.clear();
        addressField.clear();
        phoneField.clear();
        profileField.clear();
        yearField.clear();
        gradeField.clear();
      } catch (Exception e) {
        messageLabel.setText("Message: Please enter valid data for the user, be careful on double and int fields.");
        statusLabel.setText("Status: Error");
      }
    });

    viewFileButton.setOnAction(event -> {
      // code here
    });

    exitProgramButton.setOnAction(event -> {
      Platform.exit();
    });

    // LAYOUT BUILDUP ========================================
    gridPane.setGridLinesVisible(false); // show grid lines

    // layout first column
    gridPane.add(addDataButton, 1, 5);
    gridPane.add(nrField, 1, 6);
    gridPane.add(fNameField, 1, 7);
    gridPane.add(lNameField, 1, 8);
    gridPane.add(addressField, 1, 9);
    gridPane.add(phoneField, 1, 10);
    gridPane.add(profileField, 1, 11);
    gridPane.add(yearField, 1, 12);
    gridPane.add(gradeField, 1, 13);

    gridPane.setHalignment(addDataButton, HPos.CENTER);
    gridPane.setHalignment(nrField, HPos.CENTER);
    gridPane.setHalignment(fNameField, HPos.CENTER);
    gridPane.setHalignment(lNameField, HPos.CENTER);
    gridPane.setHalignment(addressField, HPos.CENTER);
    gridPane.setHalignment(phoneField, HPos.CENTER);
    gridPane.setHalignment(profileField, HPos.CENTER);
    gridPane.setHalignment(yearField, HPos.CENTER);
    gridPane.setHalignment(gradeField, HPos.CENTER);

    // layout second column
    gridPane.add(greetingLabel, 2, 1);
    gridPane.add(messageLabel, 2, 2);
    gridPane.add(statusLabel, 2, 3);

    lineBoxCreateFile.getChildren().addAll(createFileButton, fileNameField);
    lineBoxCreateFile.setAlignment(Pos.CENTER);
    gridPane.add(lineBoxCreateFile, 2, 4);

    lineBoxEditFile.getChildren().addAll(editFileButton, findIdField);
    lineBoxEditFile.setAlignment(Pos.CENTER);
    gridPane.add(lineBoxEditFile, 2, 5);

    gridPane.add(editNrField, 2, 6);
    gridPane.add(editFNameField, 2, 7);
    gridPane.add(editLNameField, 2, 8);
    gridPane.add(editAddressField, 2, 9);
    gridPane.add(editPhoneField, 2, 10);
    gridPane.add(editProfileField, 2, 11);
    gridPane.add(editYearField, 2, 12);
    gridPane.add(editGradeField, 2, 13);

    // layout third column
    Label otherFunctionsText = new Label("Other Functions");
    gridPane.setHalignment(otherFunctionsText, HPos.CENTER);
    gridPane.setHalignment(viewFileButton, HPos.CENTER);
    gridPane.setHalignment(deleteFileButton, HPos.CENTER);
    gridPane.setHalignment(executeTaskButton, HPos.CENTER);
    gridPane.setHalignment(exitProgramButton, HPos.CENTER);
    gridPane.add(otherFunctionsText, 3, 5);
    gridPane.add(viewFileButton, 3, 6);
    gridPane.add(deleteFileButton, 3, 7);
    gridPane.add(executeTaskButton, 3, 8);
    gridPane.add(exitProgramButton, 3, 9);

    // positioning
    gridPane.setHalignment(messageLabel, HPos.CENTER);
    gridPane.setHalignment(statusLabel, HPos.CENTER);
    gridPane.setHalignment(greetingLabel, HPos.CENTER);

    gridPane.setVgap(20);
    gridPane.setHgap(20);
    gridPane.setAlignment(Pos.TOP_CENTER);

    // Box layout = new VBox(15);
    // layout.setAlignment(Pos.CENTER);
    // layout.getChildren().addAll(greetingLabel, createFileButton, addDataButton,
    // viewFileButton, editFileButton,
    // deleteFileButton, executeTaskButton, exitProgramButton);
    //

    // create scene and attach layout
    Scene scene = new Scene(gridPane, 1200, 600);

    // the actual application window
    primaryStage.setTitle("Project Elevi");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    // this launches javafx
    launch(args);
  }
}
