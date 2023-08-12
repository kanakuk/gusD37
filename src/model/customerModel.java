package model;
/**customer Model.*/
public class customerModel {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;
    private int divisionID;
    private int countryID;

    /**Constructor for the customer model.
     @param customerID
     @param customerName
     @param address
     @param postalCode
     @param phone
     @param division
     @param country
     @param divisionID
     @param countryID
     */
    public customerModel(int customerID,
                         String customerName,
                         String address,
                         String postalCode,
                         String phone,
                         String division,
                         String country,
                         int divisionID,
                         int countryID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    /**Getter for the customerID.
     @return customerID.*/
    public int getCustomerID() {return customerID;}
    /**Setter for the customerID.
     @param customerID*/
    public void setCustomerID(int customerID) {this.customerID = customerID;}


    /**Getter for the customerName.
     @return customerName.*/
    public String getCustomerName() {return customerName;}
    /**Setter for the customerName.
     @param customerName*/
    public void setCustomerName(String customerName) {this.customerName = customerName;}


    /**Getter for the address.
     @return address.*/
    public String getAddress() {return address;}
    /**Setter for the address.
     @param address*/
    public void setAddress(String address) {this.address = address;}


    /**Getter for the postalCode.
     @return postalCode.*/
    public String getPostalCode() {return postalCode;}
    /**Setter for the postalCode.
     @param postalCode*/
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}


    /**Getter for the phone.
     @return phone.*/
    public String getPhone() {return phone;}
    /**Setter for the phone.
     @param phone*/
    public void setPhone(String phone) {this.phone = phone;}


    /**Getter for the division.
     @return division.*/
    public String getDivision() {
        return division;
    }
    /**Setter for the division.
     @param division*/
    public void setDivision(String division) {
        this.division = division;
    }


    /**Getter for the country.
     @return country.*/
    public String getCountry() {
        return country;
    }
    /**Setter for the country.
     @param country*/
    public void setCountry(String country) {
        this.country = country;
    }


    /**Getter for the divisionID.
     @return divisionID.*/
    public int getDivisionID() {
        return divisionID;
    }
    /**Setter for the divisionID.
     @param divisionID*/
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }


    /**Getter for the countryID.
     @return countryID.*/
    public int getCountryID() {
        return countryID;
    }
    /**Setter for the countryID.
     @param countryID*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }


    /**Overrides the country attribute .*/
    @Override
    public String toString() {
        return country;
    }

}

