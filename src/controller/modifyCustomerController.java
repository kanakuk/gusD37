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

/**Initializes the modifyCustomer screen.*/
public class modifyCustomerController implements Initializable {
    public TextField customerIdField1;
    public TextField nameField2;
    public TextField addressField3;
    public TextField postalCodeField4;
    public TextField phoneNumberField5;
    public ComboBox<countryModel> countryBox6;
    public ComboBox<divisionModel> divisionBox7;
    private customerModel selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = customerController.customerModify();
        try {
            populateCountryBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerIdField1.setText(String.valueOf
                (selectedCustomer.getCustomerID()));
        nameField2.setText(String.valueOf
                (selectedCustomer.getCustomerName()));
        addressField3.setText(String.valueOf
                (selectedCustomer.getAddress()));
        postalCodeField4.setText(String.valueOf
                (selectedCustomer.getPostalCode()));
        phoneNumberField5.setText(String.valueOf
                (selectedCustomer.getPhone()));
        //going thru list then finds match, then selects that match
        for (countryModel c : countryBox6.getItems()) {
            if (c.getCountryID() == selectedCustomer.getCountryID()) {
                countryBox6.setValue(c);
            }
        }
        try {
            divisionBox7.setItems(divisionQuery.getAllDivisions
                    (countryBox6.getValue().getCountryID()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (divisionModel d: divisionBox7.getItems()) {
            if (d.getDivisionID() == selectedCustomer.getDivisionID()) {
                divisionBox7.setValue(d);
            }
        }
    }

    /**Populates the countries into their combo box.*/
    public void populateCountryBox() throws SQLException {
        try {
            for(countryModel country : countryQuery.getAllCountries()) {
                countryBox6.getItems().add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Takes the user back to the customer screen.
     @param actionEvent triggered when cancel button is pressed. */
    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 648, 287);
        stage.setScene(scene);
        stage.show();
    }

    /**Saves the inputted data to the database.
     * Takes the user back to the customer screen.
     @param actionEvent triggered when save button is pressed. */
    public void onActionSave(ActionEvent actionEvent) {
        String customerID = customerIdField1.getText();
        String name = nameField2.getText();
        String address = addressField3.getText();
        String postalCode = postalCodeField4.getText();
        String phoneNumber = phoneNumberField5.getText();
        String division = divisionBox7.getValue().toString();

        try {
            customerQuery.modifySaveCustomer(
                    customerID,
                    name,
                    address,
                    postalCode,
                    phoneNumber,
                    division);
            Parent root = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 648, 287);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
































































