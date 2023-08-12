package model;
import java.time.LocalDateTime;

/**Appointment Model*/
public class appointmentModel {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private int contact;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;

    /**Constructor for appointment model.
     @param appointmentID
     @param contact
     @param customerID
     @param description
     @param end
     @param location
     @param start
     @param title
     @param type
     @param userID
     */
    public appointmentModel(
            int appointmentID,
            String title,
            String description,
            String location,
            int contact,
            String type,
            LocalDateTime start,
            LocalDateTime end,
            int customerID,
            int userID)
    {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
    }

    /**Getter for the appointmentID.
     @return appointmentID.*/
    public int getAppointmentID() { return appointmentID; }
    /**Setter for the appointmentID.
     @param appointmentID*/
    public void setAppointmentID(int appointmentID) {this.appointmentID = appointmentID;}

    /**Getter for the title.
     @return title.*/
    public String getTitle() {return title;}
    /**Setter for the appointmentID.
     @param title*/
    public void setTitle(String title) {this.title = title;}

    /**Getter for the .
     @return description.*/
    public String getDescription() {return description;}
    /**Setter for the .
     @param description*/
    public void setDescription(String description) {this.description = description;}

    /**Getter for the .
     @return location.*/
    public String getLocation() {return location;}
    /**Setter for the appointmentID.
     @param location*/
    public void setLocation(String location) {this.location = location;}

    /**Getter for the .
     @return contact.*/
    public int getContact() {return contact;}
    /**Setter for the appointmentID.
     @param contact*/
    public void setContact(int contact) {this.contact = contact;}

    /**Getter for the .
     @return type.*/
    public String getType() {return type;}
    /**Setter for the appointmentID.
     @param type*/
    public void setType(String type) {this.type = type;}

    /**Getter for the .
     @return start.*/
    public LocalDateTime getStart() {return start;}
    /**Setter for the appointmentID.
     @param start*/
    public void setStart(LocalDateTime start) {this.start = start;}

    /**Getter for the .
     @return end.*/
    public LocalDateTime getEnd() {return end;}
    /**Setter for the appointmentID.
     @param end*/
    public void setEnd(LocalDateTime end) {this.end = end;}

    /**Getter for the .
     @return customerID.*/
    public int getCustomerID() {return customerID;}
    /**Setter for the appointmentID.
     @param customerID*/
    public void setCustomerID(int customerID) {this.customerID = customerID;}

    /**Getter for the .
     @return userID.*/
    public int getUserID() {return userID;}
    /**Setter for the appointmentID.
     @param userID*/
    public void setUserID(int userID) {this.userID = userID;}

}