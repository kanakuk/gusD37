package controller;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import query.*;
import query.customerQuery;

/**Initializes the appointment table.*/
public class customerController implements Initializable {
    public TableView customerTable;
    public TableColumn customerId1;
    public TableColumn customerName2;
    public TableColumn address3;
    public TableColumn postalCode4;
    public TableColumn phoneNumber5;
    public TableColumn divisionID6;
    public TableColumn country7;
    private static customerModel selectedCustomer;
    public static customerModel customerModify() {
        return selectedCustomer;
    }


    /**Populates the customer table with current info
     * in each column.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customerModel> customerList = customerQuery.getAllCustomers();
        customerTable.setItems(customerList);
        customerId1.setCellValueFactory
                (new PropertyValueFactory<>("customerID"));
        customerName2.setCellValueFactory
                (new PropertyValueFactory<>("customerName"));
        address3.setCellValueFactory
                (new PropertyValueFactory<>("address"));
        postalCode4.setCellValueFactory
                (new PropertyValueFactory<>("postalCode"));
        phoneNumber5.setCellValueFactory
                (new PropertyValueFactory<>("phone"));
        divisionID6.setCellValueFactory
                (new PropertyValueFactory<>("division"));
        country7.setCellValueFactory
                (new PropertyValueFactory<>("country"));
    }

    /**Takes the user to the add customer screen.
     @param actionEvent triggered when add button is pressed.*/
    public void onActionAdd(ActionEvent actionEvent)
            throws IOException {
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/addCustomer.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 358, 358);
        stage.setScene(scene);
        stage.show();
    }

    /**Takes the user to the modify customer screen.
     @param actionEvent triggered when modify button is pressed.*/
    public void onActionModify(ActionEvent actionEvent) throws IOException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectedCustomer = (customerModel) customerTable.
                getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/modifyCustomer.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 358, 358);
        stage.setScene(scene);
        stage.show();

    }

    /**Deletes the chosen customer from its list.
     @param actionEvent triggered when delete button is pressed.*/
    public void onActionDelete(ActionEvent actionEvent)
            throws IOException, SQLException {
        selectedCustomer = (customerModel)
                customerTable.getSelectionModel().getSelectedItem();
        int customerID = selectedCustomer.getCustomerID();
        ObservableList<appointmentModel> customerAppointment =
                appointmentQuery.getAllAppointments();
        ObservableList<customerModel> allCustomer =
                customerQuery.getAllCustomers();

        for (appointmentModel selectedAppointment :
                customerAppointment) {
            if (selectedAppointment.getCustomerID() ==
            selectedCustomer.getCustomerID()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointments Present");
                alert.setContentText("Cannot delete this customer " +
                        "due to him having appoinments.");
                alert.showAndWait();

                return;
            }
        }

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
        Parent root = FXMLLoader.load(getClass().getResource
                ("/view/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).
                getScene().getWindow();
        Scene scene = new Scene(root, 648, 287);
        stage.setScene(scene);
        stage.show();

        Alert nextAlert = new Alert(Alert.AlertType.INFORMATION);
        nextAlert.setTitle("Appointment Deleted");
        nextAlert.setContentText("Appointment successfully deleted.");
        nextAlert.showAndWait();
    }

    /**Takes the user to the main menu screen.
     @param actionEvent triggered when cancel button is pressed.*/
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 328, 400);
        stage.setScene(scene);
        stage.show();
    }
}


































