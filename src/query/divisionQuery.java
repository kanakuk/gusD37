package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Group of query methods related to a country's states.*/
public class divisionQuery {
    /**Database query to grab data from the division table .*/
    public static ObservableList<divisionModel>
    getAllDivisions(int country) throws SQLException {
        ObservableList<divisionModel> division =
                FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().
                prepareStatement
                        ("SELECT * FROM first_level_divisions " +
                                "WHERE Country_ID = ?");
        ps.setInt(1, country);
        try{
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                divisionModel newDivision = new divisionModel(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID"));
                //System.out.println(rs.getString("Division"));
                division.add(newDivision);
            }
            return division;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Pulls divisions by associated column "divisionID'
     @param division */
    public static divisionModel pullDivisionID(String division)
            throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement
                ("SELECT * FROM first_level_divisions WHERE Division = ?");
        ps.setString(1, division);
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    divisionModel divisionModel2 = new divisionModel(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            rs.getInt("Country_ID"));
                    return divisionModel2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
}































































