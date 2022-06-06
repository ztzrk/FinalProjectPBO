import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController extends App{

    @FXML
    private Button daftar;

    @FXML
    private Button masukPembeli;

    @FXML
    private Button masukPenjualBtn;

    @FXML
    private Label title;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/MasukPembeli.fxml"));
        Scene scene = new Scene(root);

        Stage stageMasukPembeli = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageMasukPembeli.setScene(scene);
        stageMasukPembeli.show();
    }

    @FXML
    void daftarHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/Daftar.fxml"));
        Scene scene = new Scene(root);

        Stage stageDaftar = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageDaftar.setScene(scene);
        stageDaftar.show();
    }

    @FXML
    void masukPenjualHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/HomePenjual.fxml"));
        Scene scene = new Scene(root);

        Stage stageHomePenjual = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageHomePenjual.setScene(scene);
        stageHomePenjual.show();
    }
}