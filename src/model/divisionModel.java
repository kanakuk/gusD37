package model;
/**division model.*/
public class divisionModel {
    private int divisionID;
    private String division;
    private int countryID;

    /**Constructor for division model.
     @param divisionID
     @param division
     @param countryID
     */
    public divisionModel(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
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


    /**Overrides the divsion attribute.*/
    @Override
    public String toString() {
        return division;
    }
}
