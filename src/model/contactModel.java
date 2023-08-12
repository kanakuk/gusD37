package model;

/**contact model.*/
public class contactModel {
    private int contactID;
    private String contactName;
    private String email;

    /**Constructor for the contact model.
     @param contactID
     @param contactName
     @param email
     */
    public contactModel(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**Getter for the contactID.
     @return .*/
    public int getContactID() {return contactID;}
    /**Setter for the contactID.
     @param contactID*/
    public void setContactID(int contactID) {this.contactID = contactID;}


    /**Getter for the contactName.
     @return contactName.*/
    public String getContactName() {return contactName;}
    /**Setter for the contactName.
     @param contactName*/
    public void setContactName(String contactName) {this.contactName = contactName;}


    /**Getter for the email.
     @return email.*/
    public String getEmail() {return email;}
    /**Setter for the email.
     @param email*/
    public void setEmail(String email) {this.email = email;}


    /**Override the attribute contactName.*/
    @Override
    public String toString() {return contactName;}
}
