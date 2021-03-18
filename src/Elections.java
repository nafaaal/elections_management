import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//NEED TO GET CANDIDATES
public class Elections {

    public static void insert(String name, String island, String address, String id_no) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO voters_list(name, island, address, id_no, has_voted, voted_for) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, island);
            ps.setString(3, address);
            ps.setString(4, id_no);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.execute();
            System.out.println("Data has been inserted!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    }

    public static void candidates_list() {
        Connection con = DbConnection.connect();
        ResultSet rs  =  null;
        try {
            Statement stmt  = con.createStatement();
            String sql = "SELECT * FROM candidate_list";
            rs = stmt.executeQuery(sql);
            System.out.println("CANDIDATES LIST : ");
            System.out.printf("%-18s %-20s %-18s %-11s %15s\n", "Candidate #", "Name", "Island", "Address", "Party");
            while (rs.next()) {
                System.out.printf("%-18s", rs.getInt("candidate_number"));
                System.out.printf("%-20s", rs.getString("name"));
                System.out.printf("%-20s", rs.getString("island"));
                System.out.printf("%-20s", rs.getString("address"));
                System.out.printf("%5s", rs.getString("party")+"\n");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    }

    public static void voter_list() {
        Connection con = DbConnection.connect();
        ResultSet rs  =  null;
        try {
            Statement stmt  = con.createStatement();
            String sql = "SELECT * FROM voters_list";
            rs = stmt.executeQuery(sql);
            System.out.println("VOTERS LIST : ");
            System.out.printf("%-28s %-20s %-18s %-11s %5s %15s\n", "Name", "Island", "Address", "ID Card", "Voted?", "Voted_for");
            while (rs.next()) {
                System.out.printf("%-29s", rs.getString("name"));
                System.out.printf("%-20s", rs.getString("island"));
                System.out.printf("%-20s", rs.getString("address"));
                System.out.printf("%-10s", rs.getString("id_no"));
                System.out.printf("%5s", rs.getInt("has_voted"));
                System.out.printf("%15s", rs.getInt("voted_for")+"\n");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    }

    public static ArrayList<Integer> candidate_numbers(){
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT DISTINCT candidate_number FROM candidate_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                candidates.add(rs.getInt("candidate_number"));
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

    public static ArrayList<String> candidate_names(){
        ArrayList<String> candidates = new ArrayList<String>();
        ResultSet rs  =  null;
        Connection con = DbConnection.connect();
        try {
            String sql = "SELECT name FROM candidate_list;";
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                candidates.add(rs.getString("name"));
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



    public static int eligible_check(String id_card) {
        Connection con = DbConnection.connect();
        ResultSet rs  =  null;
        try {
            Statement stmt  = con.createStatement();
            String sql = "SELECT * FROM voters_list WHERE id_no='"+id_card+"'";
            rs = stmt.executeQuery(sql);
            return rs.getInt("has_voted");
        } catch (SQLException e) {
            return -1;
//            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                return -1;
//                System.out.println(e.toString());
            }

        }
    }

    public static String vote(String id_card, int vote) {
        PreparedStatement ps = null;
        Connection con = DbConnection.connect();
        try {
            String sql = "UPDATE voters_list SET has_voted=1, voted_for="+vote+" WHERE id_no='"+id_card+"'";
            ps = con.prepareStatement(sql);
            ps.execute();
            return "Successfully Voted.";
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    return "Error";
    }


    public static void statistics() {
        Connection con = DbConnection.connect();
        ResultSet rs  =  null;
        ArrayList<String> candidates = candidate_names();
        int max = 0;
        try {
            Statement stmt  = con.createStatement();
            //IDEALLY GET COUNT FROM DB
            System.out.println("VOTING RESULTS: ");
            for (int i=0; i<4; i++){
                String sql = "SELECT COUNT(voted_for) as vote FROM voters_list WHERE voted_for="+i;
                rs = stmt.executeQuery(sql);
                int vote_amount = rs.getInt("vote");
                if(i==0){
                    System.out.print("Didn't vote : ");
                    System.out.println(vote_amount);
                } else {
                    System.out.print("Candidate Number "+i+ " - " +candidates.get(i-1)+ " : ");
                    System.out.println(vote_amount);
                }

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
    }

}
