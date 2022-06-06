import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PenjualController {
    static TextField addMakanan = new TextField();
    static TextField addHarga = new TextField();
    static String[][] arrayMenu;

    @FXML
    private GridPane makananGP;
    
    @FXML
    private Button showMakananBtn;

    @FXML
    private Button simpanBtn;

    @FXML
    private Button hapus;

    @FXML
    private TextField hapusTf;

    @FXML
    private Button tambahBtn;

    @FXML
    void tambahHandler(ActionEvent event) throws IOException {
        arrayMenu = getMenu();
        boolean isSame = false;
        for (int i = 0; i < arrayMenu.length; i++) {
            if(addMakanan.getText().equals(arrayMenu[i][0])){
                arrayMenu[i][0] = addMakanan.getText();
                arrayMenu[i][1] = addHarga.getText();
                FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\menu.txt", false);
                for (int j = 0; j < arrayMenu.length; j++) {
                    writer.write(arrayMenu[j][0] + ", " + arrayMenu[j][1]);
                    writer.write("\n");
                }
                isSame = true;
                writer.close();
            }
            else if (!addMakanan.getText().equals(arrayMenu[i][0]) && i == arrayMenu.length - 1 && !isSame){
                FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\menu.txt", true);
                writer.write(addMakanan.getText() + ", " + addHarga.getText());
                writer.write("\n");
                writer.close();
            }
            else if (addMakanan.getText().isEmpty() || addHarga.getText().isEmpty()){
                Scene scene = changeScene("view/HomePenjual.fxml");
                Stage stageHomePenjual = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stageHomePenjual.setScene(scene);
                stageHomePenjual.show();
            }
        }
        addMakanan.setText(null);
        addHarga.setText(null);
        Scene scene = changeScene("view/HomePenjual.fxml");
        Stage stageHomePenjual = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageHomePenjual.setScene(scene);
        stageHomePenjual.show();
    }

    @FXML
    void showMakanan(ActionEvent event) throws IOException {
        arrayMenu = getMenu();
        for (int i = 1; i <= arrayMenu.length; i++) {
            makananGP.add(new Label(arrayMenu[i-1][0]), 0, i);
            makananGP.add(new Label(toRupiah(Integer.parseInt(arrayMenu[i-1][1]))), 1, i);
        }
        addMakanan.setMaxWidth(100);
        addHarga.setMaxWidth(70);
        makananGP.add(addMakanan, 0, arrayMenu.length + 1);
        makananGP.add(addHarga, 1, arrayMenu.length + 1);
        makananGP.setVisible(true);
        showMakananBtn.setDisable(true);
    }

    @FXML
    void hapusHandler(ActionEvent event) throws IOException {
        arrayMenu = getMenu();
        ArrayList<String[]> listMenu = ArrayToListConversion(arrayMenu);
        for (int i = 0; i < arrayMenu.length; i++) {
            if(hapusTf.getText().equals(arrayMenu[i][0])) {
                listMenu.remove(i);
            }
        }
        FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\menu.txt", false);
        for(String[] i: listMenu) {
            writer.write(i[0] + ", " + i[1] + "\n");
        }
        writer.close();
        arrayMenu = getMenu();
    }

    private ArrayList<String[]> ArrayToListConversion(String[][] input) {
        ArrayList<String[]> listMenu = new ArrayList<String[]>();
        for(String[] i: input){
            listMenu.add(i);
        }
        return listMenu;
    }

    @FXML
    void simpanHandler(ActionEvent event) throws IOException {
        Scene scene = changeScene("view/Home.fxml");
        Stage stageHome = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageHome.setScene(scene);
        stageHome.show();
    }

    //Method untuk memudahkan
    public String[][] getMenu() throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        File file = new File("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\menu.txt");
        BufferedReader br= new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null){
            arrayList.add(st);
        }
        br.close();
        
        arrayMenu = new String[arrayList.size()][2];
        for (int i = 0; i < arrayList.size(); i++) {
            arrayMenu[i] = arrayList.get(i).split(", ");
        }
        return arrayMenu;
    }

    public String toRupiah(int prize) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return String.format("%s %n", kursIndonesia.format(prize));
    }
    
    public Scene changeScene(String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene scene = new Scene(root);
        return scene;
    }
}