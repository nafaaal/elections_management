package election;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Candidate extends Person{
    int candidate_number;
    String party;

    Candidate(String name, String island, String address, int candidate_number, String party){
        this.name = name;
        this.island = island;
        this.address = address;
        this.candidate_number = candidate_number;
        this.party = party;
    }

    //default
    Candidate(){
        this("test","test","test",-1,"test");
    }

    @Override
    public ArrayList<Person> get_data(){
        ArrayList<Person> candidates = new ArrayList<>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT * FROM candidate_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Person candidate = new Candidate(
                        rs.getString("name"),
                        rs.getString("island"),
                        rs.getString("address"),
                        rs.getInt("candidate_number"),
                        rs.getString("party")
                );
                candidates.add(candidate);
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
        return candidates;
    }

    @Override
    public void print_data() {
        ArrayList<Person> candidates = get_data();
        System.out.println("CANDIDATES LIST : \n");
        System.out.printf("%-18s %-20s %-18s %-11s %15s\n", "Candidate #", "Name", "Island", "Address", "Party");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Person c : candidates) {
            System.out.printf("%-18s", ((Candidate)c).candidate_number);
            System.out.printf("%-20s", c.name);
            System.out.printf("%-20s", c.island);
            System.out.printf("%-20s", c.address);
            System.out.printf("%5s", ((Candidate)c).party+ "\n");
        }
    }
}
