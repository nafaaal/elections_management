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

    //Default constructor
    Voter(){
        this("test", "test", "test", "test", 1,1);
    }

    @Override
    public ArrayList<Person> get_data(){
        ArrayList<Person> voters = new ArrayList<>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT * FROM voters_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Person voter = new Voter(
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

    @Override
    public void print_data() {
        ArrayList<Person> voters = get_data();
        System.out.println("VOTERS LIST : \n");
        System.out.printf("%-28s %-19s %-19s %5s \n", "Name", "Island", "Address", "ID Card");
        System.out.println("----------------------------------------------------------------------------");
        for (Person v : voters) {
            System.out.printf("%-29s", v.name);
            System.out.printf("%-20s", v.island);
            System.out.printf("%-20s", v.address);
            System.out.printf("%5s", ((Voter)v).id_card+  "\n");
        }
    }
}
