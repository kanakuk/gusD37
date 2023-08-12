package model;
/**month model.*/
public class monthModel {
    private String months;
    private int total;

    /**Constructor for month model.
     @param months
     @param total
     */
    public monthModel(String months, int total) {
        this.months = months;
        this.total = total;
    }

    /**Getter for the months.
     @return months.*/
    public String getMonths() {
        return months;
    }
    /**Setter for the months.
     @param months*/
    public void setMonths(String months) {
        this.months = months;
    }


    /**Getter for the total.
     @return total.*/
    public int getTotal() {
        return total;
    }
    /**Setter for the total.
     @param total*/
    public void setTotal(int total) {
        this.total = total;
    }
}
