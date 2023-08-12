package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Initializes the main menu screen.*/
public class mainMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**Takes the user to the appointment screen.
     @param actionEvent triggered when appointment's button is pressed.*/
    public void onActionAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 919, 287);
        stage.setScene(scene);
        stage.show();
    }

    /**Takes the user to the customer screen.
     @param actionEvent triggered when customer's button is pressed.*/
    public void onActionCustomers(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 648, 287);
        stage.setScene(scene);
        stage.show();
    }

    /**Takes the user to the report screen.
     @param actionEvent triggered when report's button is pressed.*/
    public void onActionReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/report.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1008, 557);
        stage.setScene(scene);
        stage.show();
    }

    /**Takes the user back to the login screen.
     @param actionEvent triggered when the cancel button is pressed.*/
    public void onActionLogout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 343);
        stage.setScene(scene);
        stage.show();
    }

}




































