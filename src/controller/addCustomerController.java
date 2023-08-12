package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.*;
import query.*;

/**Allows the user to add a customer to the customer screen .*/
public class addCustomerController implements Initializable {
    public TextField customerIdField1;
    public TextField nameField2;
    public TextField addressField3;
    public TextField postalCodeField4;
    public TextField phoneNumberField5;
    public ComboBox<countryModel> countryBox6;
    public ComboBox<divisionModel> divisionBox7;

    /**This method initializes the combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateCountryBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Populates the countries into their combo box.*/
    public void populateCountryBox() throws SQLException {
        try {
                for (countryModel country :
                        countryQuery.getAllCountries()) {
                    countryBox6.getItems().add(country);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Saves the inputted data to the database.
     * Takes the user back to the customer screen.
     @param actionEvent triggered when save button is pressed. */
    public void saveOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String customerName = nameField2.getText();
        String address = addressField3.getText();
        String postalCode = postalCodeField4.getText();
        String phone = phoneNumberField5.getText();
        String division = String.valueOf(divisionBox7.getValue());
        customerQuery.createCustomer
                (customerName,
                address,
                postalCode,
                phone,
                division);
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 648, 287);
        stage.setScene(scene);
        stage.show();
    }

    /**Takes the user back to the main screen
     @param actionEvent triggered when cancel button is pressed. */
    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 648, 287);
        stage.setScene(scene);
        stage.show();
    }

    /**Once the country is chosen, the divisions populate
     into the division box
     @param actionEvent triggered when program reaches this method .*/
    public void onActionFilterDivision(ActionEvent actionEvent)
            throws SQLException {
        divisionBox7.setItems(divisionQuery.getAllDivisions
                (countryBox6.getValue().getCountryID()));
    }
}























































