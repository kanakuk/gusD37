package model;
/**country model.*/
public class countryModel {
    private int countryID;
    private String country;

    /**Constructor for the country model.
     @param country
     @param countryID
     */
    public countryModel(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**Getter for the countryID.
     @return countryID.*/
    public int getCountryID() {return countryID;}
    /**Setter for the countryID.
     @param countryID*/
    public void setCountryID(int countryID) {this.countryID = countryID;}


    /**Getter for the country.
     @return .*/
    public String getCountry() {return country;}
    /**Setter for the country.
     @param country*/
    public void setCountry(String country) {this.country = country;}


    /**Overrides the attribute country.*/
    @Override
    public String toString() { return country; }
}
