
import java.io.*;
import java.nio.file.*;
import java.util.*;

class FileManager {
  private String fileName;
  private String message;
  private String status;

  FileManager(String fileName) {
    this.fileName = fileName;
  }

  public void createFile() {
    try {
      File file = new File(this.fileName + ".txt");

      // if (fileName.trim() == "") {
      // this.status = "Failed";
      // this.message = "The field should not be empty";
      // }
      // throw new Error();

      if (file.createNewFile()) {
        this.status = "Success";
        this.message = "File '" + file.getName() + "' was created.";
      } else {
        this.status = "Informational";
        this.message = "File '" + file.getName() + "' already exists.";
      }
    } catch (IOException e) {
      this.status = "Error";
      this.message = "An Error Occured";
    }
  }

  public void addData(String elevDataString) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName + ".txt", true));

      bw.write(elevDataString);
      bw.newLine();
      bw.close();
      this.message = "Data written successfully.";
      this.status = "Success";
    } catch (IOException e) {
      this.message = "An error occured: " + e.getMessage();
      this.status = "Error";
    }
  }

  public void showFileContent() {
    try {
      String content = Files.readString(Path.of(this.fileName + ".txt"));
      this.message = content;
      this.status = "Success";
    } catch (IOException e) {
      this.message = "An error occured: " + e.getMessage();
      this.status = "Error";
    }
  }

  public void modifyFileContent(String id, String fullUserDataRaw) {
    try {
      String file = this.fileName + ".txt";

      if (!new File(file).exists()) {
        this.message = "File doesn't exist";
        this.status = "Informational";
        return;
      }

      List<String> listOfRawData = Files.readAllLines(Path.of(file));
      Boolean isIdFound = false;

      for (int i = 0; i < listOfRawData.size(); ++i) {
        String[] rawDataString = listOfRawData.get(i).trim().split(",");

        if (rawDataString[0].trim().equals(id.trim())) {
          listOfRawData.set(i, fullUserDataRaw);
          isIdFound = true;
          break;
        }
      }

      if (!isIdFound) {
        this.message = "There's no data with such ID.";
        this.status = "Informational";
      } else {
        Files.write(Path.of(file), listOfRawData);
        this.message = "Line updated successfully.";
        this.status = "Success";
      }

    } catch (IOException e) {
      this.message = "An error occured.";
      this.status = "Error";
    }
  }

  public void executeTask() {
    try {
      TreeSet<Ele> ts = new TreeSet<>(new Comparator<Ele>() {
        public int compare(Ele el1, Ele el2) {
          return el1.getGrade().compareTo(el2.getGrade());
        }
      });
      BufferedReader br = new BufferedReader(new FileReader(this.fileName + ".txt"));
      String line = br.readLine();

      while (line != null) {
        String[] elevDataRaw = line.trim().split(","); // [nr, fname, lsname, address, phone, year, grade]
        Ele el = new Ele(elevDataRaw[0].trim(), elevDataRaw[1].trim(), elevDataRaw[2].trim(), elevDataRaw[3].trim(),
            elevDataRaw[4].trim(), elevDataRaw[5].trim(), Integer.parseInt(elevDataRaw[6].trim()),
            Double.parseDouble(elevDataRaw[7].trim()));
        ts.add(el);

        line = br.readLine();
      }
      br.close();

      for (Ele e : ts) {
        if (e.getGrade() >= 8.00) { // logic itself
          System.out.println(e.getFName() + ": " + e.getGrade());
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void deleteFile() {
    File file = new File(fileName + ".txt");
    if (file.exists()) {
      if (file.delete()) {
        System.out.println("File was deleted. Good job.");
      } else {
        System.out.println("Unfortunatelly we couldn't delete the file.");
      }
    } else {
      System.out.println("This file no longer exists. It's gone.");
    }
  }

  public String getMessage() {
    return this.message;
  }

  public String getStatus() {
    return this.status;
  }
}
