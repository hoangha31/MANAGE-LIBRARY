package com.jetbrains.demo1.controller;

import com.jetbrains.demo1.alert.Notification;
import com.jetbrains.demo1.dao.DocumentDao;
import com.jetbrains.demo1.dao.UserDao;
import com.jetbrains.demo1.data.DocumentData;
import com.jetbrains.demo1.data.TransactionData;
import com.jetbrains.demo1.data.UserData;
import com.jetbrains.demo1.databaseconnection.Database;
import com.jetbrains.demo1.function.CommonFunciton;
//import com.sun.javafx.tk.quantum.PaintRenderJob;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class DashBoardController extends BaseController implements Initializable {

    @FXML
    private Button add_document_btn;

    @FXML
    private Button add_transaction_btn;

    @FXML
    private Button add_user_btn;

    @FXML
    private Button clear_document_button;

    @FXML
    private Button clear_transaction_btn;

    @FXML
    private Button clear_user_btn;

    @FXML
    private Button close_button_dashboard;

    @FXML
    private TableColumn<DocumentData,Integer> column_quanity_document;

    @FXML
    private TableColumn<DocumentData,String> column_author_document;

    @FXML
    private TableColumn<UserData, Date> column_birthDate_user;

    @FXML
    private TableColumn<TransactionData, Date> column_borrowDate_transaction;

    @FXML
    private TableColumn<DocumentData, String> column_category_document;

    @FXML
    private TableColumn<UserData, String> column_firstName_user;

    @FXML
    private TableColumn<UserData, String> column_gender_user;

    @FXML
    private TableColumn<TransactionData, String> column_idDocument_transaction;

    @FXML
    private TableColumn<TransactionData, Number> column_idUser_transaction;

    @FXML
    private TableColumn<DocumentData, String> column_id_document;

    @FXML
    private TableColumn<TransactionData, Number> column_id_transaction;

    @FXML
    private TableColumn<UserData, Long> column_id_user;

    @FXML
    private TableColumn<UserData, String> column_lastName_user;

    @FXML
    private TableColumn<DocumentData, String> column_name_document;

    @FXML
    private TableColumn<TransactionData, Date> column_returnDate_transaction;

    @FXML
    private Button delete_document_btn;

    @FXML
    private Button delete_transaction_btn;

    @FXML
    private Button delete_user_btn;

    @FXML
    private Button document_btn;

    @FXML
    private Button home_btn;

    @FXML
    private ImageView image_user;

    @FXML
    private Button insert_image_btn;

    @FXML
    private Button insert_document_btn;

    @FXML
    private ImageView image_document;

    @FXML
    private AnchorPane manage_document;

    @FXML
    private AnchorPane manage_home;

    @FXML
    private AnchorPane manage_transaction;

    @FXML
    private AnchorPane manage_user;

    @FXML
    private Button minimize_button_dashboard;

    @FXML
    private TextField search_transaction;

    @FXML
    private FontAwesomeIconView search_transaction_btn;

    @FXML
    private TextField search_user;

    @FXML
    private TextField search_user1;

    @FXML
    private FontAwesomeIconView search_user_btn;

    @FXML
    private FontAwesomeIconView search_user_btn1;

    @FXML
    private Button signout_btn;

    @FXML
    private TableView<DocumentData> table_document;

    @FXML
    private TableView<?> table_transaction;

    @FXML
    private TableView<UserData> table_user;

    @FXML
    private TextField text_author_document;

    @FXML
    private DatePicker text_birthDate_user;

    @FXML
    private DatePicker text_borrowDate_transaction;

    @FXML
    private TextField text_category_docment;

    @FXML
    private TextField text_firstName_user;

    @FXML
    private ComboBox<String> text_gender_user;

    @FXML
    private TextField text_id_document;

    @FXML
    private TextField text_id_document_transaction;

    @FXML
    private TextField text_id_transaction;

    @FXML
    private TextField text_id_user;

    @FXML
    private TextField text_id_user_transaction;

    @FXML
    private TextField text_lastName_user;

    @FXML
    private TextField text_linkAdress_document;

    @FXML
    private TextField text_name_document;

    @FXML
    private TextField text_quatity_document;

    @FXML
    private DatePicker text_returnDate_transaction;
    private String image_path;

    @FXML
    private Button transaction_btn;

    @FXML
    private Button update_document_btn;

    @FXML
    private Button update_transaction_btn;

    @FXML
    private Button update_user_btn;

    @FXML
    private Button user_btn;

    @FXML
    private Label home_mostSearching;

    @FXML
    private Label home_totalBook;

    @FXML
    private Label home_mostBorrowing;


    @FXML
    private BarChart<String, Number> home_totalBookChart;


    @FXML
    private AreaChart<String, Number> home_totalUserChart;

    @FXML
    private TableView<TransactionData> table_docment1;

    @FXML
    private LineChart<String, Number> home_totalBorrowingChart;

    @FXML
    private Label username_dashboard;

    private CommonFunciton commonFunciton;

    private String[] genderList = {"Male", "Female"};

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;

    public void homeDisplayTotalBooks() {

        String sql = "SELECT COUNT(id) FROM document";

        connect = Database.connectToDb();

        int countBook = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countBook = result.getInt("COUNT(id)");
            }

            home_totalBook.setText(String.valueOf(countBook));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayTotalUsers() {

        String sql = "SELECT COUNT(userId) FROM user";

        connect = Database.connectToDb();

        int countUsers = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countUsers = result.getInt("COUNT(userId)");
            }

            home_mostBorrowing.setText(String.valueOf(countUsers));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayTotalBorrowing() {
        String sql = "SELECT COUNT(id) FROM transaction";

        connect = Database.connectToDb();

        int countTransactions = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countTransactions = result.getInt("COUNT(id)");
            }

            home_mostSearching.setText(String.valueOf(countTransactions));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalBooksChart(){
        home_totalBookChart.getData().clear();
        String sql = "SELECT category, COUNT(id) FROM document GROUP BY category";

        connect = Database.connectToDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            if(home_totalBookChart.getData() != null){
            home_totalBookChart.getData().add(chart);}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalUsersChart(){
        home_totalUserChart.getData().clear();
        String sql = "SELECT gender, COUNT(userId) FROM user GROUP BY gender";

        connect = Database.connectToDb();

        try {
            XYChart.Series<String, Number> chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            home_totalUserChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalBorrowingChart(){
        home_totalBorrowingChart.getData().clear();
        String sql = "SELECT documentId, COUNT(userId) FROM transaction GROUP BY documentId";

        connect = Database.connectToDb();

        try {
            XYChart.Series<String, Number> chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            home_totalBorrowingChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<UserData> AddUserListData() {
           ObservableList<UserData> userList = FXCollections.observableArrayList();

           String sql = "SELECT * FROM user";

           connect = Database.connectToDb();

           try{
               UserData userD;
               prepare = connect.prepareStatement(sql);
               result = prepare.executeQuery();
               while (result.next()) {
                   userD = new UserData(result.getInt("userID"), result.getString("firstName")
                                      , result.getString("lastName"), result.getDate("dateOfBirth")
                                      , result.getString("gender"), result.getString("image"));
                   userList.add(userD);
               }
           } catch (Exception e){
               e.printStackTrace();
           }
           return userList;
    }

    private ObservableList<UserData> AddUserListD;

    public void addUserShowListData(){
        AddUserListD = AddUserListData();
        column_id_user.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_firstName_user.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column_lastName_user.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column_gender_user.setCellValueFactory(new PropertyValueFactory<>("gender"));
        column_birthDate_user.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        table_user.setItems(AddUserListD);
    }

    public void userGenderList() {
        List<String> genderL = Arrays.asList(genderList);
        ObservableList<String> genderList = FXCollections.observableArrayList(genderL);
        text_gender_user.setItems(genderList);
    }

    private UserDao userDao = new UserDao();

    private ObservableList<UserData> list_user_data = FXCollections.observableArrayList();

    public void addUserToListData() {
        list_user_data.clear();
        listUsers();

        column_id_user.setCellValueFactory(new PropertyValueFactory<>("userId"));
        column_firstName_user.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column_lastName_user.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column_gender_user.setCellValueFactory(new PropertyValueFactory<>("gender"));
        column_birthDate_user.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        table_user.setItems(list_user_data);
    }

    public void listUsers() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                userDao.getAllUsers(userData -> {
                    Platform.runLater(() -> list_user_data.addAll(userData));
                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void logout() {
        try {
            boolean confirmed = Notification.confirmationDialog("Confirmation Message", "Are you sure you want to logout?");
            if (confirmed) {
                signout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/Fxml/login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            manage_home.setVisible(true);
            manage_user.setVisible(false);
            manage_transaction.setVisible(false);
            manage_document.setVisible(false);

            home_btn.setStyle("-fx-background-color: linear-gradient( to bottom right,#3f82ae,#26bf7d);");
            user_btn.setStyle("-fx-background-color: transparent");
            document_btn.setStyle("-fx-background-color: transparent");
            transaction_btn.setStyle("-fx-background-color: transparent");

            homeDisplayTotalBooks();
            homeDisplayTotalUsers();
            homeDisplayTotalBorrowing();

            homeDisplayTotalUsersChart();
            homeDisplayTotalBooksChart();
            homeDisplayTotalBorrowingChart();
        } else if (event.getSource() == user_btn) {
            manage_user.setVisible(true);
            manage_home.setVisible(false);
            manage_transaction.setVisible(false);
            manage_document.setVisible(false);

            user_btn.setStyle("-fx-background-color: linear-gradient( to bottom right,#3f82ae,#26bf7d);");
            home_btn.setStyle("-fx-background-color: transparent");
            document_btn.setStyle("-fx-background-color: transparent");
            transaction_btn.setStyle("-fx-background-color: transparent");

            addUserToListData();
            // userGenderList();
            addUserShowListData();

            userSearch();
            //updateUser();
            //addUser();

        } else if (event.getSource() == document_btn) {
            manage_document.setVisible(true);
            manage_user.setVisible(false);
            manage_home.setVisible(false);
            manage_transaction.setVisible(false);

            addDocumentToListData();

            document_btn.setStyle("-fx-background-color: linear-gradient( to bottom right,#3f82ae,#26bf7d);");
            home_btn.setStyle("-fx-background-color: transparent");
            user_btn.setStyle("-fx-background-color: transparent");
            transaction_btn.setStyle("-fx-background-color: transparent");

            availableDocumentShowListData();

        } else if (event.getSource() == transaction_btn) {
            manage_transaction.setVisible(true);
            manage_document.setVisible(false);
            manage_user.setVisible(false);
            manage_home.setVisible(false);

            addTransactionListData();

            transaction_btn.setStyle("-fx-background-color: linear-gradient( to bottom right,#3f82ae,#26bf7d);");
            home_btn.setStyle("-fx-background-color: transparent");
            user_btn.setStyle("-fx-background-color: transparent");
            document_btn.setStyle("-fx-background-color: transparent");

            addTransactionsShowListData();
        }
    }

    /*public void addUserInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            image = new Image(file.toURI().toString(), 120, 149, false, true);
            image_user.setImage(image);

            getData.path = file.getAbsolutePath();

        }
    }*/

    public void userSelect() {
        UserData userData = table_user.getSelectionModel().getSelectedItem();
        int num = table_user.getSelectionModel().getSelectedIndex();

        if((num -1) < -1) {return;}

        text_id_user.setText(String.valueOf(userData.getId()));
        text_firstName_user.setText(userData.getFirstName());
        text_lastName_user.setText(userData.getLastName());
        text_birthDate_user.setValue(userData.getDateOfBirth().toLocalDate());
        text_gender_user.setValue(userData.getGender());

        //String uri = "file:" + userData.getImage();

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return userDao.getUserImage(userData.getId());
            }
        };

        task.setOnSucceeded(event -> {
            String imagePath = task.getValue();
            if (imagePath != null) {
                UserDao.urlImageCurrent = imagePath;
                Image image = new Image("file:" + imagePath, true);
                image_user.setImage(image);
            } else {
                image_user.setImage(null);
            }
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        System.exit(0);
    }

    public void insertImageUser() {
        Image image = commonFunciton.insertImage("user");
        image_path = image.getUrl();
        image_path = image_path.substring(5);
        image_user.setImage(image);
    }

    public void addUser(){
        String insertUser = "INSERT INTO user (userId,firstName,lastName,gender,dateOfBirth,image) VALUES(?,?,?,?,?,?)";
        connect = Database.connectToDb();
        try {
            Alert alert;

            System.out.println(text_id_user.getText().isEmpty());
            System.out.println(text_lastName_user.getText().isEmpty());
            System.out.println(text_firstName_user.getText().isEmpty());
            System.out.println(text_gender_user.getValue().isEmpty());
            System.out.println(String.valueOf(text_birthDate_user.getValue()).isEmpty());
            if (text_id_user.getText().isEmpty()
                    || text_firstName_user.getText().isEmpty()
                    || text_lastName_user.getText().isEmpty()
                    || text_gender_user.getValue().isEmpty()
                    || String.valueOf(text_birthDate_user.getValue()).isEmpty()
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT userId FROM user WHERE userId = '" + text_id_user.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User: " + text_id_user.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertUser);
                    prepare.setString(1, text_id_user.getText());
                    prepare.setString(2, text_firstName_user.getText());
                    prepare.setString(3, text_lastName_user.getText());
                    prepare.setString(5, String.valueOf(text_birthDate_user.getValue()));
                    prepare.setString(4, text_gender_user.getValue());

                    if (image_path != null && !image_path.isEmpty()) {
                        prepare.setString(6, image_path);  // Thêm đường dẫn ảnh vào trường image
                    } else {
                        prepare.setNull(6, java.sql.Types.VARCHAR);  // Nếu không có ảnh, set NULL
                    }

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addUserShowListData();
                    clearUser();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser() {
        String updateData = "UPDATE user SET "
                + "userId = '" + text_id_user.getText() + "', "
                + "firstName = '" + text_firstName_user.getText() + "', "
                + "lastName = '" + text_lastName_user.getText() + "', "
                + "gender = '" + text_gender_user.getValue() + "', "
                + "dateOfBirth = '" + text_birthDate_user.getValue() + "', "
                + "image = '" + (image_user.getImage() != null ? image_user.getImage().toString() : "") + "' "
                + "WHERE userId = '" + text_id_user.getText() + "'";

        try {
            Alert alert;
            if (text_id_user.getText().isEmpty()
                    || text_firstName_user.getText().isEmpty()
                    || text_lastName_user.getText().isEmpty()
                    || text_gender_user.getValue().isEmpty()
                    || String.valueOf(text_birthDate_user.getValue()).isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to update user with userId : '" + text_id_user.getText() + "'");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated");
                    alert.showAndWait();
                    addUserShowListData();
                    clearUser();
                } else return;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void deleteUser() {
        String deleteData = "DELETE FROM user WHERE userId = '"
                + text_id_user.getText() + "'";
        connect = Database.connectToDb();
        try {
            Alert alert;
            if (text_id_user.getText().isEmpty()
                    || text_firstName_user.getText().isEmpty()
                    || text_lastName_user.getText().isEmpty()
                    || text_gender_user.getValue().isEmpty()
                    || String.valueOf(text_birthDate_user.getValue()).isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete user with userId : '" + text_id_user.getText() + "'");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();
                    addUserShowListData();
                    clearUser();
                } else return;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearUser() {
        text_id_user.setText("");
        text_firstName_user.setText("");
        text_lastName_user.setText("");
        text_birthDate_user.setValue(null);
        text_gender_user.setValue(null);
        text_birthDate_user.setValue(null);
        image_user.setImage(null);
        UserDao.urlImageCurrent = null;
        getData.path = null;
    }

    public void userSearch(){

        FilteredList<UserData> filter = new FilteredList<>(AddUserListD, e->true);

        search_user.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUserData -> {
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateUserData.getFirstName().toLowerCase().contains(searchKey)){
                    return true;
                } else if( predicateUserData.getLastName().toLowerCase().contains(searchKey)){
                    return true;
                } else if (predicateUserData.getGender().toLowerCase().contains(searchKey)){
                    return true;
                } else {
                     return false;
                }

            });
        });

        SortedList<UserData> sortedList = new SortedList<>(filter);

        sortedList.comparatorProperty().bind(table_user.comparatorProperty());
        table_user.setItems(sortedList);
    }



    private DocumentDao documentDao = new DocumentDao();

    private ObservableList<DocumentData> list_document_data = FXCollections.observableArrayList();

    public void listDocuments() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                documentDao.getAllDocuments(documentData -> {
                    Platform.runLater(() -> list_document_data.addAll(documentData));
                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void addDocumentToListData() {
        list_document_data.clear();
        listDocuments();

        column_id_document.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_name_document.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_author_document.setCellValueFactory(new PropertyValueFactory<>("author"));
        column_category_document.setCellValueFactory(new PropertyValueFactory<>("category"));
        column_quanity_document.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table_document.setItems(list_document_data);
    }

    public void insertImageDocument() {
        Image image = commonFunciton.insertImage("document");
        image_path = image.getUrl();
        image_path = image_path.substring(5);
        image_document.setImage(image);
    }

    public void documentSelect() {
        DocumentData documentData = table_document.getSelectionModel().getSelectedItem();
        int num = table_document.getSelectionModel().getSelectedIndex();

        if((num -1) < -1) {return;}

        text_id_document.setText(String.valueOf(documentData.getId()));
        text_name_document.setText(documentData.getName());
        text_author_document.setText(documentData.getAuthor());
        text_category_docment.setText(documentData.getCategory());
        text_linkAdress_document.setText(documentData.getLinkaddress());
        text_quatity_document.setText(String.valueOf(documentData.getQuantity()));

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return documentDao.getDocumentImage(documentData.getId());
            }
        };

        task.setOnSucceeded(event -> {
            String imagePath = task.getValue();
            if (imagePath != null) {
                DocumentDao.urlImageCurrent = imagePath;
                Image image = new Image("file:" + imagePath, true);
                image_document.setImage(image);
            } else {
                image_document.setImage(null);
            }
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private boolean documentFieldsValid() {
        return (!text_linkAdress_document.getText().isEmpty() &&
                !text_category_docment.getText().isEmpty() &&
                !text_author_document.getText().isEmpty() &&
                !text_id_document.getText().isEmpty() &&
                !text_name_document.getText().isEmpty() &&
                image_document.getImage() != null);
    }

    public ObservableList<DocumentData> availableDocumentListData() {

        ObservableList<DocumentData> documentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM document";

        connect = Database.connectToDb();

        try{
            DocumentData documentD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                documentD = new DocumentData(result.getString("id")
                        , result.getString("name")
                        , result.getString("author")
                        , result.getString("category")
                        , result.getInt("quantity")
                        , result.getString("linkaddress")
                        , result.getString("image"));

                documentList.add(documentD);
            }
        } catch (Exception e) {e.printStackTrace();}
        return documentList;

    }

    private ObservableList<DocumentData> availableDocumentList;
    public void availableDocumentShowListData() {
        availableDocumentList = availableDocumentListData();

        column_id_document.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_name_document.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_author_document.setCellValueFactory(new PropertyValueFactory<>("author"));
        column_category_document.setCellValueFactory(new PropertyValueFactory<>("category"));
        column_quanity_document.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table_document.setItems(availableDocumentList);
    }

    public void addDocument(){
        String insertData = "INSERT INTO document(id, name, author, category, quantity, image) VALUES (?,?,?,?,?,?)";

        connect = Database.connectToDb();

        try{
            Alert alert;

            if(text_id_document.getText().isEmpty()
                    || text_name_document.getText().isEmpty()
                    ||  text_author_document.getText().isEmpty()
                    || text_category_docment.getText().isEmpty()
                    || text_quatity_document.getText().isEmpty() ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String checkData = "SELECT id FROM document WHERE id = '" + text_id_document.getText() + "'" ;
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Document" + text_id_document.getText() + "was already exist!");
                    alert.showAndWait();
                } else {
                    //FileChooser fileChooser = new FileChooser();
                    //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

                    //Mở cửa sổ chọn tệp và nhận đường dẫn tệp đã chọn
                    //File selectedFile = fileChooser.showOpenDialog(null); // null: có thể thay bằng Stage hiện tại
                    //String imagePath = null;
                    //if (selectedFile != null) {
                    //   imagePath = selectedFile.getAbsolutePath();  // Lấy đường dẫn tuyệt đối của ảnh
                    //}

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, text_id_document.getText());
                    prepare.setString(2, text_name_document.getText());
                    prepare.setString(3, text_author_document.getText());
                    prepare.setString(4, text_category_docment.getText());
                    prepare.setString(5, text_quatity_document.getText());

                    if (image_path != null && !image_path.isEmpty()) {
                        prepare.setString(6, image_path);  // Thêm đường dẫn ảnh vào trường image
                    } else {
                        prepare.setNull(6, java.sql.Types.VARCHAR);  // Nếu không có ảnh, set NULL
                    }


                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sccessfully added!");
                    alert.showAndWait();

                    availableDocumentShowListData();
                    clearDocument();
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void updateDocument(){

        String updateData = "UPDATE document SET id = '" + text_id_document.getText()
                             + "', name = '" + text_name_document.getText()
                             + "', author = '" + text_author_document.getText()
                             + "', category = '" + text_category_docment.getText()
                             + "', quantity = '" + text_quatity_document.getText() + "' WHERE id = '" + text_id_document.getText() + "'";

        connect = Database.connectToDb();

        try {
            Alert alert;

            if(text_id_document.getText().isEmpty()
                    || text_name_document.getText().isEmpty()
                    ||  text_author_document.getText().isEmpty()
                    || text_category_docment.getText().isEmpty()
                    || text_quatity_document.getText().isEmpty() ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Document: " + text_id_document.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();


                    availableDocumentShowListData();
                    clearDocument();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument(){
        String deleteData = "DELETE FROM document WHERE id = '"
                + text_id_document.getText() + "'";

        try {
            Alert alert;

            if(text_id_document.getText().isEmpty()
                    || text_name_document.getText().isEmpty()
                    ||  text_author_document.getText().isEmpty()
                    || text_category_docment.getText().isEmpty()
                    || text_quatity_document.getText().isEmpty() ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Course: " + text_name_document.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableDocumentShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    clearDocument();

                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearDocument() {
        //text_linkAdress_document.setText("");
        text_category_docment.setText("");
        text_author_document.setText("");
        text_id_document.setText("");
        text_name_document.setText("");
        text_quatity_document.setText("");
        //image_document.setImage(null);
        //DocumentDao.urlImageCurrent = null;
    }

    public void minimize() {
        Stage stage = (Stage) minimize_button_dashboard.getScene().getWindow();
        stage.setIconified(true);
    }


    public ObservableList<TransactionData> addTransactionListData(){

        ObservableList<TransactionData> listTransactions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM transaction";

        connect = Database.connectToDb();

        try {
            TransactionData transactionD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                transactionD = new TransactionData(result.getInt("id")
                                                 , result.getInt("userId")
                                                 , result.getString("documentId")
                                                 , result.getDate("borrowDate")
                                                 , result.getDate("returnDate"));
                listTransactions.add(transactionD);
            }
        } catch(Exception e){e.printStackTrace();}
        return listTransactions;
    }

    private ObservableList<TransactionData> addTransactionListDa;

    public void addTransactionsShowListData(){
        addTransactionListDa = addTransactionListData();

        column_id_transaction.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_idUser_transaction.setCellValueFactory(new PropertyValueFactory<>("userId"));
        column_idDocument_transaction.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        column_borrowDate_transaction.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        column_returnDate_transaction.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        table_docment1.setItems(addTransactionListDa);
    }

    public void addTransactionsSelect(){

        TransactionData transactionD = table_docment1.getSelectionModel().getSelectedItem();
        //int num = table_docment1.getSelectionModel().getSelectedIndex();

        //if((num -1) < -1) {return;}

        text_id_transaction.setText(String.valueOf(transactionD.getId()));
        text_id_user_transaction.setText(String.valueOf(transactionD.getUserId()));
        text_id_document_transaction.setText(transactionD.getDocumentId());
        text_borrowDate_transaction.setValue(transactionD.getBorrowDate().toLocalDate());
        text_returnDate_transaction.setValue(transactionD.getReturnDate().toLocalDate());
    }

    public void addTransaction() {
        String insertTransaction = "INSERT INTO transaction (id, userId, documentId, borrowDate, returnDate) VALUES(?,?,?,?,?)";
        connect = Database.connectToDb();
        try {
            Alert alert;

            // Kiểm tra các trường nhập liệu có bị bỏ trống không
            if (text_id_transaction.getText().isEmpty()
                    || text_id_user_transaction.getText().isEmpty()
                    || text_id_document_transaction.getText().isEmpty()
                    || String.valueOf(text_borrowDate_transaction.getValue()).isEmpty()
                    || String.valueOf(text_returnDate_transaction.getValue()).isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return; // Dừng hàm nếu có trường nhập liệu bị bỏ trống
            }

            String checkTransactionQuery = "SELECT id FROM transaction WHERE id = ?";
            prepare = connect.prepareStatement(checkTransactionQuery);
            prepare.setString(1, text_id_transaction.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                // Nếu tìm thấy transactionId đã tồn tại
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Transaction ID already exists!");
                alert.showAndWait();
                return; // Dừng hàm nếu transactionId đã tồn tại
            }

            // Kiểm tra xem userId có tồn tại trong bảng user
            String checkUserQuery = "SELECT userId FROM user WHERE userId = ?";
            prepare = connect.prepareStatement(checkUserQuery);
            prepare.setString(1, text_id_user_transaction.getText());
            result = prepare.executeQuery();

            if (!result.next()) {
                // Nếu không tìm thấy userId trong bảng user
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("User ID not found!");
                alert.showAndWait();
                return;  // Dừng hàm nếu không tìm thấy userId
            }

            // Kiểm tra xem documentId có tồn tại trong bảng document
            String checkDocumentQuery = "SELECT id FROM document WHERE id = ?";
            prepare = connect.prepareStatement(checkDocumentQuery);
            prepare.setString(1, text_id_document_transaction.getText());
            result = prepare.executeQuery();

            if (!result.next()) {
                // Nếu không tìm thấy documentId trong bảng document
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Document ID not found!");
                alert.showAndWait();
                return;  // Dừng hàm nếu không tìm thấy documentId
            }

            LocalDate borrowDate = text_borrowDate_transaction.getValue();
            LocalDate returnDate = text_returnDate_transaction.getValue();

            if (returnDate.isBefore(borrowDate)) {
                // Nếu returnDate nhỏ hơn borrowDate
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Return date cannot be earlier than borrow date!");
                alert.showAndWait();
                return;  // Dừng hàm nếu returnDate không hợp lệ
            }

            // Nếu cả userId và documentId đều hợp lệ, thực hiện thêm giao dịch
            prepare = connect.prepareStatement(insertTransaction);
            prepare.setString(1, text_id_transaction.getText());
            prepare.setString(2, text_id_user_transaction.getText());
            prepare.setString(3, text_id_document_transaction.getText());
            prepare.setString(4, String.valueOf(text_borrowDate_transaction.getValue()));
            prepare.setString(5, String.valueOf(text_returnDate_transaction.getValue()));

            int rowsAffected = prepare.executeUpdate(); // Thực thi câu lệnh INSERT và lấy số dòng bị ảnh hưởng

            if (rowsAffected > 0) {
                // Nếu giao dịch được thêm thành công
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Transaction Successfully Added!");
                alert.showAndWait();

                addTransactionsShowListData();  // Hiển thị lại danh sách giao dịch
                clearTransaction();  // Làm sạch các trường nhập liệu
            } else {
                // Nếu không có dòng nào bị ảnh hưởng, thông báo lỗi
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add the transaction!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTransaction() {
        String updateData = "UPDATE transaction SET "
                + "userId = '" + text_id_user_transaction.getText() + "', "
                + "documentId = '" + text_id_document_transaction.getText() + "', "
                + "borrowDate = '" + text_borrowDate_transaction.getValue() + "', "
                + "returnDate = '" + text_returnDate_transaction.getValue() + "'"
                + "WHERE id = '" + text_id_transaction.getText() + "'";

        try {
            Alert alert;

            // Kiểm tra các trường nhập liệu có bị bỏ trống không
            if (text_id_transaction.getText().isEmpty()
                    || text_id_user_transaction.getText().isEmpty()
                    || text_id_document_transaction.getText().isEmpty()
                    || String.valueOf(text_borrowDate_transaction.getValue()).isEmpty()
                    || String.valueOf(text_returnDate_transaction.getValue()).isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            // Kiểm tra xem userId có tồn tại trong bảng user
            String checkUserQuery = "SELECT userId FROM user WHERE userId = ?";
            prepare = connect.prepareStatement(checkUserQuery);
            prepare.setString(1, text_id_user_transaction.getText());
            result = prepare.executeQuery();

            if (!result.next()) {
                // Nếu không tìm thấy userId trong bảng user
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("User ID not found!");
                alert.showAndWait();
                return;  // Dừng hàm nếu không tìm thấy userId
            }

            // Kiểm tra xem documentId có tồn tại trong bảng document
            String checkDocumentQuery = "SELECT id FROM document WHERE id = ?";
            prepare = connect.prepareStatement(checkDocumentQuery);
            prepare.setString(1, text_id_document_transaction.getText());
            result = prepare.executeQuery();

            if (!result.next()) {
                // Nếu không tìm thấy documentId trong bảng document
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Document ID not found!");
                alert.showAndWait();
                return;  // Dừng hàm nếu không tìm thấy documentId
            }

            // Kiểm tra xem returnDate có nhỏ hơn borrowDate không
            LocalDate borrowDate = text_borrowDate_transaction.getValue();
            LocalDate returnDate = text_returnDate_transaction.getValue();

            if (returnDate.isBefore(borrowDate)) {
                // Nếu returnDate nhỏ hơn borrowDate
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Return date cannot be earlier than borrow date!");
                alert.showAndWait();
                return;  // Dừng hàm nếu returnDate không hợp lệ
            }

            // Nếu tất cả các dữ liệu hợp lệ, thực hiện cập nhật giao dịch vào bảng transaction
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to update transaction with id : '" + text_id_transaction.getText() + "'");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(updateData); // Thực hiện câu lệnh UPDATE

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated");
                alert.showAndWait();

                addTransactionsShowListData();  // Cập nhật lại danh sách giao dịch
                clearTransaction();  // Làm sạch các trường nhập liệu

            } else {
                return;  // Nếu người dùng không xác nhận cập nhật, không làm gì cả
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteTransaction(){
        String deleteData = "DELETE FROM transaction WHERE id = '"
                + text_id_transaction.getText() + "'";
        connect = Database.connectToDb();
        try {
            Alert alert;
            if (text_id_transaction.getText().isEmpty()
                    || text_id_user_transaction.getText().isEmpty()
                    || text_id_document_transaction.getText().isEmpty()
                    || String.valueOf(text_borrowDate_transaction.getValue()).isEmpty()
                    || String.valueOf(text_returnDate_transaction.getValue()).isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete transaction with id : '" + text_id_transaction.getText() + "'");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();


                    addTransactionsShowListData();
                    clearTransaction();

                } else return;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTransaction() {
        text_id_transaction.setText("");
        text_id_user_transaction.setText("");
        text_id_document_transaction.setText("");
        text_borrowDate_transaction.setValue(null);
        text_returnDate_transaction.setValue(null);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeDisplayTotalBooks();
        homeDisplayTotalUsers();
        homeDisplayTotalBorrowing();
        homeDisplayTotalUsersChart();
        homeDisplayTotalBooksChart();
        homeDisplayTotalBorrowingChart();

         addUserShowListData();
         addUserToListData();
         userGenderList();
         availableDocumentShowListData();

         addTransactionsShowListData();

    }
}

