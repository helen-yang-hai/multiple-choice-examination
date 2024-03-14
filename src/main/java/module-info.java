module com.example.labtest2 {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.example.labtest2 to javafx.fxml;
  exports com.example.labtest2;
}