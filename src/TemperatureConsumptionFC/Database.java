/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */

package TemperatureConsumptionFC;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Connection connection;

    public Database() {
        this.connection = null;
        connect();
    }

    private void connect() {
        System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
            return;
        }

        //System.out.println("PostgreSQL JDBC Driver Registered!");
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://192.168.90.103/dadosF207", "postgres",
                    "1234");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    /**
     * Retrieves all data (power) from the last X minutes
     *
     * @param minutesInterval
     * @return List with all data (Float)
     */
    public List retrieveConsumptionData(Integer minutesInterval) {
        //Queries
        String SQL = "SELECT dado FROM potencia "
                + "WHERE id_disp='30' AND data > current_timestamp - interval '" + minutesInterval + " minutes'";//round(cast(potencia as numeric), 2)

        List<Float> res = new ArrayList();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String data = rs.getString("dado");
                //System.out.println("dC: "+data);
                res.add(Float.parseFloat(data));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    /**
     * Average temperature in the last X minutes
     *
     * @return
     */
    public Float retrieveTemperatureData(Integer minutesInterval) {
        //Queries
        String SQL = "SELECT AVG(dado) AS temp FROM temperatura "
                + "WHERE id_disp='30' OR id_disp='20' AND data > current_timestamp - interval '" + minutesInterval + " minutes'";

        Float res = 0.0f;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String data = rs.getString("temp");
                //System.out.println("dT: "+data);
                if (data != null) {
                    return Float.parseFloat(data);
                }
            } 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void main(String[] argv) {
        Database d = new Database();
        d.retrieveConsumptionData(2);
        d.retrieveTemperatureData(5);
    }

}
