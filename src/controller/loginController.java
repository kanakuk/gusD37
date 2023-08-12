package controller;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.userModelL;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import model.*;
import query.*;

/**Initializes the login screen.*/
public class loginController implements Initializable {
    public Label titleLabel;
    public Label userNameLabel;
    public TextField userNameField;
    public Label passwordLabel;
    public TextField passwordField;
    public Button loginButton;
    public Button cancelButton;
    public Label timeZoneLabel;
    public Label languageLabel;
    public static userModelL user;

    /**Puts words into the labels.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageLabel.setText(String.valueOf(ZoneId.systemDefault()));
        ResourceBundle rb = resourceBundle.getBundle
                ("language", Locale.getDefault());
        titleLabel.setText(rb.getString("title"));
        userNameLabel.setText(rb.getString("userName"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        cancelButton.setText(rb.getString("cancel"));
        timeZoneLabel.setText(rb.getString("timezone"));
    }

    /**Sends the user to the main menu if the password and
     name are valid.
     @param actionEvent triggered when the login button is selected.*/
    public void onActionLogin(ActionEvent actionEvent)
            throws SQLException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if (validLogin(userName, password)) {
            Parent root = FXMLLoader.load
                    (getClass().getResource("/view/mainMenu.fxml"));
            Stage stage = (Stage)
                    ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 328, 400);
            stage.setScene(scene);
            stage.show();

        } else {
            ResourceBundle rb = ResourceBundle.getBundle
                    ("language", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("errortitle"));
            alert.setHeaderText(rb.getString("invalidaccount"));
            alert.setContentText(rb.getString("invaliddescription"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK))
                    .setText(rb.getString("ok"));
            alert.showAndWait();
        }
        ObservableList<appointmentModel> appointmentModel1 =
                appointmentQuery.getAllAppointments();
        ObservableList<appointmentModel> pendingAppointments =
                FXCollections.observableArrayList();
        if (appointmentModel1 != null) {
            for (appointmentModel appointmentInFifteen :
                    appointmentModel1) {
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime timeInFifteen = currentTime.plusMinutes(15);
                if (appointmentInFifteen.getStart().
                        isAfter(currentTime) && appointmentInFifteen.
                        getStart().isBefore(timeInFifteen)) {
                    pendingAppointments.add(appointmentInFifteen);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Starting Soon!");
                    alert.setContentText("An appointment is scheduled to " +
                            "begin within 15 minutes! Appointment ID: " +
                            appointmentInFifteen.getAppointmentID() +
                            "     Start Time: " +
                            appointmentInFifteen.getStart());
                    alert.showAndWait();
                    return;
                }
            }

        }
        if (pendingAppointments.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Appointments Within 15 Minutes!");
            alert.setContentText("There are no appointments scheduled within the next 15 minutes!");
            alert.showAndWait();
        }

    }

    /**Writes succesful/failed login info to a text file.*/
    public static void logToFile(String username, boolean success) {
        try (FileWriter fw = new FileWriter
                ("login_activity.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw))
        {pw.println("Timestamp: " + ZonedDateTime.now()
                + "    UserName: "
                + username
                + "    Attempt: "
                + (success ? "successful" : "failure"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**Compares user's credentials to the database
     to see if the credentials are valid.
     @param password takes the password.*/
    public static Boolean validLogin
            (String userName, String password) throws SQLException {
        Connection connection = JDBC.getConnection();
        ResultSet rs = connection.createStatement().executeQuery
                ("SELECT * FROM users WHERE User_Name='"
                        + userName
                        + "' AND Password='"
                        + password + "'");
        try {
            if(rs.next()) {
                user = new userModelL();
                user.setUsername(rs.getString("User_Name"));
                user.setUserId(rs.getInt("User_ID"));
                logToFile(userName, true);
                return true;
            } else {
                logToFile(userName, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return false;
    }
    public static userModelL getUser() { return user;}



}
























































