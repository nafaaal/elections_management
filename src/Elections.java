
package election;

import java.sql.*;
import java.util.ArrayList;

//NEED TO GET CANDIDATES
public abstract class Elections {

    public static void voter_list() {
        ArrayList<Voter> voters = new ArrayList<>(Voter.get_all_voters());
        System.out.println("VOTERS LIST : \n");
        System.out.printf("%-28s %-19s %-19s %5s \n", "Name", "Island", "Address", "ID Card");
        System.out.println("----------------------------------------------------------------------------");
        for (Voter v : voters) {
            System.out.printf("%-29s", v.name);
            System.out.printf("%-20s", v.island);
            System.out.printf("%-20s", v.address);
            System.out.printf("%5s", v.id_card + "\n");
        }
    }

    public static void candidates_list() {
            ArrayList<Candidate> candidates = new ArrayList<>(Candidate.get_all_candidates());
            System.out.println("CANDIDATES LIST : \n");
            System.out.printf("%-18s %-20s %-18s %-11s %15s\n", "Candidate #", "Name", "Island", "Address", "Party");
            System.out.println("---------------------------------------------------------------------------------------");
            for (Candidate c : candidates) {
                System.out.printf("%-18s", c.candidate_number);
                System.out.printf("%-20s", c.name);
                System.out.printf("%-20s", c.island);
                System.out.printf("%-20s", c.address);
                System.out.printf("%5s", c.party+ "\n");
            }
    }

    public static ArrayList<Integer> candidate_numbers(){
        ArrayList<Candidate> candidates = new ArrayList<>(Candidate.get_all_candidates());
        ArrayList<Integer> candidate_no = new ArrayList<>();
        for (Candidate c : candidates){
            candidate_no.add(c.candidate_number);
        }
        return candidate_no;
    }

    public static int eligible_check(String id_card) {
        ArrayList<Voter> voters = new ArrayList<>(Voter.get_all_voters());
        int res = -1;
        for (Voter v : voters){
            String id = v.id_card;
            if (id.equals(id_card)) {
                if (v.has_voted == 1) {
                    return 1;
                }
                else return 0;
            }
        }
        return res;
    }

    public static String vote(String id_card, int vote) {
        PreparedStatement ps = null;
        Connection con = DbConnection.connect();
        try {
            String sql = "UPDATE voters_list SET has_voted=1, voted_for="+vote+" WHERE id_no='"+id_card+"'";
            ps = con.prepareStatement(sql);
            ps.execute();
            System.out.println("");
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

    public static void insert(Voter voter) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO voters_list(name, island, address, id_no, has_voted, voted_for) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, voter.name);
            ps.setString(2, voter.island);
            ps.setString(3, voter.address);
            ps.setString(4, voter.id_card);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.execute();
            System.out.println("\nVoter has been added.");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                assert ps != null;
                ps.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
    }

    public static void statistics() {
        ArrayList<Voter> voters = new ArrayList<>(Voter.get_all_voters());
        ArrayList<Candidate> allcandidates = new ArrayList<>(Candidate.get_all_candidates());
        ArrayList<Integer> votes = new ArrayList<>(allcandidates.size());
        int count = 1;
        int max = 0;
        String winner = "";
        int no_vote = voters.size();
        for (Candidate cand : allcandidates){
            int numberOfVotes = 0;
            for (Voter v : voters){
                if (v.voted_for == cand.candidate_number){
                    numberOfVotes += 1;
                }
            }
            if (numberOfVotes > max) {
                max = numberOfVotes;
                winner = cand.name;
            }
            votes.add(numberOfVotes);
            no_vote -= numberOfVotes;
        }
        System.out.println("Haven't voted : " + no_vote);
        for (Candidate can : allcandidates){
            System.out.println("#"+ count + " - " + can.name + " - " + votes.get(count-1) + " votes.");
            count++;
        }
        System.out.println("Winner is "+ winner);
    }

}