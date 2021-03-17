import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Elections {

    public static void insert(String name, String island, String address, String id_no, int has_voted, int voted_for) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO voters_list(name, island, address, id_no, has_voted, voted_for) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, island);
            ps.setString(3, address);
            ps.setString(4, id_no);
            ps.setInt(5, has_voted);
            ps.setInt(6, voted_for);
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

    public static void query(String sql) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs  =  null;
        try {
            Statement stmt  = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("island")+ "\t" +
                        rs.getString("address")+ "\t" +
                        rs.getString("id_no")+ "\t" +
                        rs.getInt("has_voted")+ "\t" +
                        rs.getInt("voted_for"));
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


    public static int has_voted(String id_card) {
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
//        return -1;
    }

    public static String vote(String id_card, int vote) {
        if (has_voted(id_card) == 1) return "Already voted";
        if (has_voted(id_card) == -1) return "Person not registered to vote";
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
        try {
            Statement stmt  = con.createStatement();
            for (int i=0; i<4; i++){
                String sql = "SELECT COUNT(voted_for) as vote FROM voters_list WHERE voted_for="+i;
                rs = stmt.executeQuery(sql);
                if(i==0){
                    System.out.print("Didn't vote : ");
                    System.out.println(rs.getInt("vote"));
                } else {
                    System.out.print("Voted for "+i+" : ");
                    System.out.println(rs.getInt("vote"));
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
