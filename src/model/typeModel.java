package model;
/**type model.*/
public class typeModel {
    private String type;
    private int total;

    /**Constructor for type model.
     @param type
     @param total
     */
    public typeModel(String type, int total) {
        this.type = type;
        this.total = total;
    }

    /**Getter for the type.
     @return type.*/
    public String getType() {
        return type;
    }
    /**Setter for the type.
     @param type*/
    public void setType(String type) {
        this.type = type;
    }


    /**Getter for the total.
     @return total.*/
    public int getTotal() {
        return total;
    }
    /**Setter for the total.
     @param total */
    public void setTotal(int total) {
        this.total = total;
    }
}
