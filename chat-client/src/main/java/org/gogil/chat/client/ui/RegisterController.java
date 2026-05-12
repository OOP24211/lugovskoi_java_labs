package org.gogil.chat.client.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gogil.chat.client.viewmodel.LoginViewModel;

public class RegisterController {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    private LoginViewModel viewModel;

    public void setViewModel(LoginViewModel viewModel) {
        this.viewModel = viewModel;
        bindToViewModel();
    }

    private void bindToViewModel() {
        viewModel.userName.bind(userNameField.textProperty());
        viewModel.password.bind(passwordField.textProperty());
        errorLabel.textProperty().bind(viewModel.errorText);
    }

    @FXML
    private void onRegisterClicked() {
        String confirm = confirmPasswordField.getText();
        if (!confirm.equals(viewModel.password.get())) {
            viewModel.errorText.set("Пароли не совпадают");
            return;
        }
        viewModel.register();
    }

    @FXML
    private void onGoToLoginClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            LoginController controller = loader.getController();
            controller.setViewModel(viewModel);

            Stage stage = (Stage) userNameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Ошибка перехода: " + e.getMessage());
        }
    }
}
