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

    public static ArrayList<Candidate> get_all_candidates(){
        ArrayList<Candidate> candidates = new ArrayList<>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT * FROM candidate_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Candidate candidate = new Candidate(
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
}
