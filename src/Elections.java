import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
        return -1;
    }

}
