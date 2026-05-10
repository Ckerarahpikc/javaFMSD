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
import java.io.*;
import java.nio.file.*;

public class App extends Application {
  private FileManager fileService;
  private String fileName;

  @Override
  public void start(Stage primaryStage) {
    GridPane gridPane = new GridPane();
    HBox lineBoxCreateFile = new HBox(30);
    HBox lineBoxEditFile = new HBox(10);
    HBox nrBox = new HBox(10);
    HBox fNameBox = new HBox(10);
    HBox lNameBox = new HBox(10);
    HBox addressBox = new HBox(10);
    HBox phoneBox = new HBox(10);
    HBox profileBox = new HBox(10);
    HBox yearBox = new HBox(10);
    HBox gradeBox = new HBox(10);

    HBox editNrBox = new HBox(10);
    HBox editFNameBox = new HBox(10);
    HBox editLNameBox = new HBox(10);
    HBox editAddressBox = new HBox(10);
    HBox editPhoneBox = new HBox(10);
    HBox editProfileBox = new HBox(10);
    HBox editYearBox = new HBox(10);
    HBox editGradeBox = new HBox(10);

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
    Button findPersonByIdButton = new Button("Find Person by ID");
    Button editFileButton = new Button("Edit Data");
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
    fNameField.setPromptText("Alan");
    lNameField.setPromptText("Turing");
    addressField.setPromptText("New Bridge 5/6 st.");
    phoneField.setPromptText("1234 56 789");
    profileField.setPromptText("Science");
    yearField.setPromptText("1-12");
    gradeField.setPromptText("4.00-10.00");
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
    viewFileButton.setDisable(true);
    deleteFileButton.setDisable(true);
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
    executeTaskButton.setDisable(true);

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
        viewFileButton.disableProperty().unbind();
        viewFileButton.setDisable(false);
        executeTaskButton.disableProperty().unbind();
        executeTaskButton.setDisable(false);

        // fileNameField.clear();
        fileNameField.setEditable(false);
      }
    });

    addDataButton.setOnAction(event -> { // add data
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

    viewFileButton.setOnAction(event -> { // view data
      fileService.showFileContent();

      // stage (window)
      Stage secondaryStage = new Stage();
      secondaryStage.setTitle("Content");

      VBox secondaryLayout = new VBox(20);
      Label contentLabel = new Label(fileService.getMessage());

      Button closeLayoutButton = new Button("Close View");
      closeLayoutButton.setOnAction(e -> secondaryStage.close());

      secondaryLayout.getChildren().addAll(contentLabel, closeLayoutButton);

      // create the scene and show the stage
      Scene sceneContent = new Scene(secondaryLayout, 500, 300);
      secondaryStage.setScene(sceneContent);

      secondaryStage.show();
    });

    findPersonByIdButton.setOnAction(event -> { // edit data
      String file = fileNameField.getText() + ".txt";

      if (!new File(file).exists()) {
        messageLabel.setText("Message: File doesn't exist");
        statusLabel.setText("Status: Informational");
        return;
      }

      // ioexception error handling
      try {
        List<String> listOfRawData = Files.readAllLines(Path.of(file));

        for (int i = 0; i < listOfRawData.size(); ++i) {
          String[] listOfData = listOfRawData.get(i).trim().split(",");
          if (listOfData[0].trim().equals(findIdField.getText().trim())) {
            editNrField.setText(listOfData[0]);
            editFNameField.setText(listOfData[1]);
            editLNameField.setText(listOfData[2]);
            editAddressField.setText(listOfData[3]);
            editPhoneField.setText(listOfData[4]);
            editProfileField.setText(listOfData[5]);
            editYearField.setText(listOfData[6]);
            editGradeField.setText(listOfData[7]);

            messageLabel.setText("Message: Data loaded for editin.");
            statusLabel.setText("Status: Success");
            return;
          }
          // if not found
          messageLabel.setText("Message: Couldn't find this ID.");
          statusLabel.setText("Status: Informational");
        }
      } catch (IOException e) {
        messageLabel.setText("An error occured.");
        statusLabel.setText("Error");
      }
    });

    editFileButton.setOnAction(e -> {
      String rawData = String.join(",", editNrField.getText(),
          editFNameField.getText(),
          editLNameField.getText(),
          editAddressField.getText(),
          editPhoneField.getText(),
          editProfileField.getText(),
          editYearField.getText(),
          editGradeField.getText());
      fileService.modifyFileContent(findIdField.getText().trim(), rawData);

      messageLabel.setText("Message: " + fileService.getMessage());
      statusLabel.setText("Status: " + fileService.getStatus());
    });

    exitProgramButton.setOnAction(event -> {
      Platform.exit();
    });

    // LAYOUT BUILDUP ========================================
    gridPane.setGridLinesVisible(false); // show grid lines

    // layout first column
    nrBox.getChildren().addAll(new Label("ID"), nrField);
    nrBox.setAlignment(Pos.CENTER_LEFT);
    fNameBox.getChildren().addAll(new Label("First Name"), fNameField);
    fNameBox.setAlignment(Pos.CENTER_LEFT);
    lNameBox.getChildren().addAll(new Label("Last Name"), lNameField);
    lNameBox.setAlignment(Pos.CENTER_LEFT);
    addressBox.getChildren().addAll(new Label("Address"), addressField);
    addressBox.setAlignment(Pos.CENTER_LEFT);
    phoneBox.getChildren().addAll(new Label("Phone"), phoneField);
    phoneBox.setAlignment(Pos.CENTER_LEFT);
    profileBox.getChildren().addAll(new Label("Profile"), profileField);
    profileBox.setAlignment(Pos.CENTER_LEFT);
    yearBox.getChildren().addAll(new Label("Year (int)"), yearField);
    yearBox.setAlignment(Pos.CENTER_LEFT);
    gradeBox.getChildren().addAll(new Label("Grade (float)"), gradeField);
    gradeBox.setAlignment(Pos.CENTER_LEFT);

    gridPane.add(addDataButton, 1, 5);
    gridPane.add(nrBox, 1, 6);
    gridPane.add(fNameBox, 1, 7);
    gridPane.add(lNameBox, 1, 8);
    gridPane.add(addressBox, 1, 9);
    gridPane.add(phoneBox, 1, 10);
    gridPane.add(profileBox, 1, 11);
    gridPane.add(yearBox, 1, 12);
    gridPane.add(gradeBox, 1, 13);

    gridPane.setHalignment(addDataButton, HPos.LEFT);

    // layout second column
    gridPane.add(greetingLabel, 2, 1);
    gridPane.add(messageLabel, 2, 2);
    gridPane.add(statusLabel, 2, 3);

    gridPane.setHalignment(messageLabel, HPos.CENTER);
    gridPane.setHalignment(statusLabel, HPos.CENTER);
    gridPane.setHalignment(greetingLabel, HPos.CENTER);

    lineBoxCreateFile.getChildren().addAll(createFileButton, fileNameField);
    lineBoxCreateFile.setAlignment(Pos.CENTER);
    gridPane.add(lineBoxCreateFile, 2, 4);

    lineBoxEditFile.getChildren().addAll(editFileButton, findPersonByIdButton, findIdField);
    lineBoxEditFile.setAlignment(Pos.CENTER);
    gridPane.add(lineBoxEditFile, 2, 5);

    editNrBox.getChildren().addAll(new Label("Edit ID"), editNrField);
    editNrBox.setAlignment(Pos.CENTER);
    editFNameBox.getChildren().addAll(new Label("Edit First Name"), editFNameField);
    editFNameBox.setAlignment(Pos.CENTER);
    editLNameBox.getChildren().addAll(new Label("Edit Last Name"), editLNameField);
    editLNameBox.setAlignment(Pos.CENTER);
    editAddressBox.getChildren().addAll(new Label("Edit Address"), editAddressField);
    editAddressBox.setAlignment(Pos.CENTER);
    editPhoneBox.getChildren().addAll(new Label("Edit Phone"), editPhoneField);
    editPhoneBox.setAlignment(Pos.CENTER);
    editProfileBox.getChildren().addAll(new Label("Edit Profile"), editProfileField);
    editProfileBox.setAlignment(Pos.CENTER);
    editYearBox.getChildren().addAll(new Label("Edit Year"), editYearField);
    editYearBox.setAlignment(Pos.CENTER);
    editGradeBox.getChildren().addAll(new Label("Edit Grade"), editGradeField);
    editGradeBox.setAlignment(Pos.CENTER);

    gridPane.add(editNrBox, 2, 6);
    gridPane.add(editFNameBox, 2, 7);
    gridPane.add(editLNameBox, 2, 8);
    gridPane.add(editAddressBox, 2, 9);
    gridPane.add(editPhoneBox, 2, 10);
    gridPane.add(editProfileBox, 2, 11);
    gridPane.add(editYearBox, 2, 12);
    gridPane.add(editGradeBox, 2, 13);

    // layout third column
    Label otherFunctionsText = new Label("Other Functions");
    gridPane.setHalignment(otherFunctionsText, HPos.RIGHT);
    gridPane.setHalignment(deleteFileButton, HPos.RIGHT);
    gridPane.setHalignment(executeTaskButton, HPos.RIGHT);
    gridPane.setHalignment(exitProgramButton, HPos.RIGHT);
    gridPane.setHalignment(viewFileButton, HPos.RIGHT);
    gridPane.add(otherFunctionsText, 3, 5);
    gridPane.add(viewFileButton, 3, 6);
    gridPane.add(deleteFileButton, 3, 7);
    gridPane.add(executeTaskButton, 3, 8);
    gridPane.add(exitProgramButton, 3, 9);

    // positioning
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
