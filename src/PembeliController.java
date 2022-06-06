import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PembeliController {
    static ArrayList<String> jumlah = new ArrayList<String>();
    static ArrayList<String> listMenu = new ArrayList<String>();
    static String[][] arrayAkun;
    static String nama;
    static String pass;
    static String ZPay;
    static int totalHarga;

    @FXML
    private Button daftar;

    @FXML
    private TextField passwordDaftar;

    @FXML
    private TextField usernameDaftar;

    @FXML
    private Label wrongDaftar;

    @FXML
    void daftarHandler(ActionEvent event) throws IOException {
        arrayAkun = getListAkun();
        boolean isnew = true;
        for (int i = 0; i < arrayAkun.length; i++) {
            if(usernameDaftar.getText().equals(arrayAkun[i][0])) {
                isnew = false;
            }
        }
        FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\pembeli.txt", true);
        if(isnew) {
            writer.write(usernameDaftar.getText() + ", " + passwordDaftar.getText() + ", 0");
            writer.write("\n");
            Scene scene = changeScene("view/Home.fxml");
            Stage stageHome = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageHome.setScene(scene);
            stageHome.show();
        }
        else {
            wrongDaftar.setVisible(true);;
        }
        writer.close();
    }

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Label wrong;

    @FXML
    void loginHandler(ActionEvent event) throws IOException{
        arrayAkun = getListAkun();
        for (int i = 0; i < arrayAkun.length; i++) {
            if(username.getText().equals(arrayAkun[i][0]) && password.getText().equals(arrayAkun[i][1])) {
                nama = username.getText();
                ZPay = arrayAkun[i][2];
                pass = password.getText();
                Scene scene = changeScene("view/HomePembeli.fxml");
                Stage stageHomePembeli = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stageHomePembeli.setScene(scene);
                stageHomePembeli.show();
            }
            else {
                wrong.setText("Akun tidak valid");
            }
        }
    }

    @FXML
    private Label Zpay;

    @FXML
    private ScrollPane homePembeli;

    @FXML
    private GridPane menu;

    @FXML
    private Button makanan;

    @FXML
    private Button pesan;

    @FXML
    private Button topUp;

    @FXML
    private Button showSaldo;

    @FXML
    void showSaldoHandler(ActionEvent event) {
        setZPAy();
        showSaldo.setVisible(false);
    }

    @FXML
    void topUp(ActionEvent event) throws IOException {
        Scene scene = changeScene("view/TopUp.fxml");
        Stage stageTopUp = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageTopUp.setScene(scene);
        stageTopUp.show();
    }

    @FXML
    void showMenu(ActionEvent event) throws IOException {
        String[][] arrayMenu = getMenu();
        for (int i = 0; i < arrayMenu.length; i++) {
            menu.add(new CheckBox(arrayMenu[i][0]), 0 , i + 1);
            menu.add(new Label(toRupiah(Integer.parseInt(arrayMenu[i][1]))), 1 , i + 1);
            menu.add(new TextField(), 2, i + 1);
        }
    }

    @FXML
    void pesanHandler(ActionEvent event) throws IOException {
        ObservableList<Node> listNode = menu.getChildren();
        boolean isCheck = false;
        for (Node node : listNode) {
            if(node instanceof CheckBox) {
                CheckBox makanan = (CheckBox) node;
                if (makanan.isSelected()) {
                    listMenu.add(makanan.getText());
                    isCheck = true;
                }
            }
            else if(node instanceof TextField && isCheck) {
                TextField textField = (TextField) node;
                if(!textField.getText().isEmpty()){
                    jumlah.add(((TextField) node).getText());
                    isCheck = false;
                }
                else {
                    listMenu.remove(listMenu.size() - 1);
                    isCheck = false;
                }
            }
        }
        Scene scene = changeScene("view/RincianPembayaran.fxml");
        Stage stageRincian = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageRincian.setScene(scene);
        stageRincian.show();
    }

    @FXML
    private Button backHome;

    @FXML
    private Button btnTopUp;

    @FXML
    private TextField inputZpay;

    @FXML
    private Label topUpSukses;

    @FXML
    void TopUpHandler(ActionEvent event) throws IOException {
        arrayAkun = getListAkun();
        for (int i = 0; i < arrayAkun.length; i++) {
            if(nama.equals(arrayAkun[i][0]) && pass.equals(arrayAkun[i][1])) {
                int saldo = Integer.parseInt(inputZpay.getText()) + Integer.parseInt(ZPay);
                ZPay = Integer.toString(saldo);
                arrayAkun[i][2] = ZPay;
            }
        }
        topUpSukses.setVisible(true);

        FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\pembeli.txt", false);
        for (int i = 0; i < arrayAkun.length; i++) {
            writer.write(arrayAkun[i][0] + ", " + arrayAkun[i][1] + ", " + arrayAkun[i][2]);
            writer.write("\n");
        }
        writer.close();
    }

    @FXML
    void backHandler(ActionEvent event) throws IOException {
        Scene scene = changeScene("view/HomePembeli.fxml");
        Stage stageHomePembeli = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stageHomePembeli.setScene(scene);
        stageHomePembeli.show();
    }

    @FXML
    private Label TotalHarga;

    @FXML
    private Button bayar;

    @FXML
    private Label bayarLabel;

    @FXML
    private Label finalZpay;

    @FXML
    private GridPane rincian;

    @FXML
    private Button showRincian;

    @FXML
    void bayarHandler(ActionEvent event) throws IOException {
        for (int i = 0; i < arrayAkun.length; i++) {
            if(nama.equals(arrayAkun[i][0])){
                if(totalHarga>Integer.parseInt(arrayAkun[i][2])) {
                    bayarLabel.setText("Saldo ZPay Kurang");
                }
                else {
                    bayarLabel.setText("Pembayaran Berhasil");
                    for (int j = 0; j < arrayAkun.length; j++) {
                        if(nama.equals(arrayAkun[j][0]) && pass.equals(arrayAkun[j][1])) {
                            int saldo = Integer.parseInt(ZPay) - totalHarga;
                            ZPay = Integer.toString(saldo);
                            arrayAkun[j][2] = ZPay;
                        }
                    }
                    FileWriter writer = new FileWriter("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\pembeli.txt", false);
                    for (int j = 0; j < arrayAkun.length; j++) {
                        writer.write(arrayAkun[j][0] + ", " + arrayAkun[j][1] + ", " + arrayAkun[j][2]);
                        writer.write("\n");
                    }
                writer.close();
                }
            }
        }
    }

    @FXML
    void rincianUpdate(ActionEvent event) throws IOException {
        totalHarga = 0;
        for (int i = 0; i < jumlah.size(); i++) {
            int harga = Integer.parseInt(jumlah.get(i)) * getHarga(listMenu.get(i));
            totalHarga += harga;
            rincian.add(new Label(listMenu.get(i)), 0, i + 1);
            rincian.add(new Label(jumlah.get(i)), 1, i + 1);
            rincian.add(new Label(toRupiah(harga)), 2, i + 1);
        }
        rincian.add(new Label("Total"), 1, jumlah.size() + 1);
        rincian.add(new Label(toRupiah(totalHarga)), 2, jumlah.size() + 1);
        TotalHarga.setText(toRupiah(totalHarga));
        finalZpay.setText(toRupiah(Integer.parseInt(ZPay)));
    }

    //Method untuk memudahkan

    public String[][] getListAkun() throws IOException {
        ArrayList<String> listAkun = new ArrayList<String>();
        File file = new File("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\pembeli.txt");

        BufferedReader br= new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null){
            listAkun.add(st);
        }
        br.close();

        arrayAkun = new String[listAkun.size()][3];
        for (int i = 0; i < listAkun.size(); i++) {
            arrayAkun[i] = listAkun.get(i).split(", ");
        }
        return arrayAkun;
    }

    public void setZPAy() {
        int saldo = Integer.parseInt(ZPay);
        Zpay.setText(toRupiah(saldo));
    }

    public String[][] getMenu() throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        File file = new File("C:\\Users\\ralif\\Desktop\\JavaProject\\FinalProject\\src\\menu.txt");
        BufferedReader br= new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null){
            arrayList.add(st);
        }
        br.close();
        
        String[][] arrayMenu = new String[arrayList.size()][2];
        for (int i = 0; i < arrayList.size(); i++) {
            arrayMenu[i] = arrayList.get(i).split(", ");
        }
        return arrayMenu;
    }

    public int getHarga(String menu) throws IOException {
        int harga = 0;
        String[][] arrayMenu = getMenu();
        for (int i = 0; i < arrayMenu.length; i++) {
            if(arrayMenu[i][0].equals(menu)){
                harga = Integer.parseInt(arrayMenu[i][1]);
            }
        }
        return harga;
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