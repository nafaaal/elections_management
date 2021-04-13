package election;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Voter extends Person{
    String id_card;
    int has_voted;
    int voted_for;

    Voter(String name, String island, String address, String id_card, int has_voted, int voted_for){
        this.name = name;
        this.island = island;
        this.address = address;
        this.id_card = id_card;
        this.has_voted = has_voted;
        this.voted_for = voted_for;
    }

    public static ArrayList<Voter> get_all_voters(){
        ArrayList<Voter> voters = new ArrayList<>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT * FROM voters_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Voter voter = new Voter(
                        rs.getString("name"),
                        rs.getString("island"),
                        rs.getString("address"),
                        rs.getString("id_no"),
                        rs.getInt("has_voted"),
                        rs.getInt("voted_for"));
                voters.add(voter);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
        return voters;
    }
}
