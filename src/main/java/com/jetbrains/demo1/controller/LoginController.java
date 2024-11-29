package com.jetbrains.demo1.controller;


import com.jetbrains.demo1.alert.Notification;
import com.jetbrains.demo1.dao.AdminDao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private AdminDao adminDao = new AdminDao();

    public void close() {
        System.exit(0);
    }

    public void loginAdmin() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            Notification.showAlert(Alert.AlertType.ERROR
                    ,"Error Message","Please enter all the fields");
        } else {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    if (adminDao.checkLogin(username.getText(), password.getText())) {
                        Platform.runLater(() -> {
                            try {
                                Notification.showAlert(Alert.AlertType.INFORMATION
                                        ,"Success","Successfully logged in");
                                loginBtn.getScene().getWindow().hide();

                                Parent root = FXMLLoader.load(getClass().getResource("/Fxml/dashboard.fxml"));
                                Scene scene = new Scene(root);

                                Stage stage = new Stage();
                                stage.setScene(scene);
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
                                stage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            Notification.showAlert(Alert.AlertType.ERROR
                                    ,"Error Message","Wrong username or password");
                        });
                    }
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
