package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginFormController {

    public AnchorPane loginContext;
    public TextField txtUserName;
    public Button btnLogin;
    public static String name;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern usernamePattern = Pattern.compile("^[a-z]{3,}$");

    public void initialize(){
        storeValidation();
    }
    private void storeValidation() {
        map.put(txtUserName, usernamePattern);
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateTextFields(map, btnLogin);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        name = txtUserName.getText();
        URL resource = getClass().getResource("../view/ClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loginContext.getChildren().clear();
        loginContext.getChildren().add(load);
    }
}
