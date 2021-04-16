
package election;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//NEED TO GET CANDIDATES
public class Elections {

    Voter voter = new Voter();
    Candidate candidate = new Candidate();
    DbConnection db_connection = new DbConnection();

    private ArrayList<Person> voters = voter.get_data();
    private ArrayList<Person> candidates = candidate.get_data();

    private void insert(Voter voter) {
        Connection con = db_connection.connect();
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

    private void enter_voter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter island: ");
        String island = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter ID number: ");
        String id_number;
        while (true) {
            id_number = scanner.nextLine();
            if (id_number.matches("[A]{1}\\d{6}")) {
                break;
            } else {
                System.out.print("ID Card format invalid.\nEnter ID Number : ");
            }
        }
        Voter voter = new Voter(name, island, address, id_number,0, 0);
        insert(voter);

    }


    public void admin() {
        while (true) {
            int process;
            System.out.println("\nAdmin Panel");
            System.out.println("--------------------------");
            System.out.println("-1. Return to main menu");
            System.out.println(" 1. Print Candidates");
            System.out.println(" 2. Print Voters");
            System.out.println(" 3. Add Voter to list");
            System.out.println(" 4. View election result. \n");
            System.out.print("Enter process number: ");

            Scanner scanner = new Scanner(System.in);
            try {
                process = scanner.nextInt();
                System.out.print("\n");
                if (process == -1) {
                    break;
                }
            } catch (Exception ex) {
                System.out.println("Enter Valid number");
                break;
            }

            if (process == 1) {
                candidate.print_data();
            } else if (process == 2) {
                voter   .print_data();
            } else if (process == 3) {
                enter_voter();
            } else if (process == 4) {
                statistics();
            } else {
                System.out.println("Enter valid process");
            }

        }
    }

    private void statistics() {
        ArrayList<Integer> votes = new ArrayList<>(candidates.size());
        int count = 1;
        int max = 0;
        String winner = "";
        int no_vote = voters.size();
        for (Person cand : candidates){
            int numberOfVotes = 0;
            for (Person v : voters){
                if (((Voter)v).voted_for == ((Candidate)cand).candidate_number){
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
        for (Person can : candidates){
            System.out.println("#"+ count + " - " + can.name + " - " + votes.get(count-1) + " votes.");
            count++;
        }
        System.out.println("Winner is "+ winner);
    }

    private ArrayList<Integer> candidate_numbers(){
        ArrayList<Integer> candidate_no = new ArrayList<>();
        for (Person c : candidates){
            candidate_no.add(((Candidate)c).candidate_number);
        }
        return candidate_no;
    }

    private int eligible_check(String id_card) {
        int res = -1;
        for (Person v : voters){
            String id = ((Voter)v).id_card;
            if (id.equals(id_card)) {
                if (((Voter)v).has_voted == 1) {
                    return 1;
                }
                else return 0;
            }
        }
        return res;
    }

    private void vote(String id_card, int vote) {
        PreparedStatement ps = null;
        Connection con = db_connection.connect();
        try {
            String sql = "UPDATE voters_list SET has_voted=1, voted_for="+vote+" WHERE id_no='"+id_card+"'";
            ps = con.prepareStatement(sql);
            ps.execute();
            System.out.println("\nSuccessfully Voted.");
            return;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

        }
        System.out.println("Error");
    }


    public void voting() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> candidates = candidate_numbers();

        System.out.print("Enter ID Card number: ");
        String id_card = scanner.nextLine();

        int is_eligible = eligible_check(id_card);
        int vote = 0;

        if (is_eligible == -1) {
            System.out.println( "Not registered");
            return;
        } else if (is_eligible == 1) {
            System.out.println("\nAlready voted.");
            return;
        } else {
            System.out.println("");
            candidate.print_data();
            System.out.print("\nEnter Candidate Number : ");


            try {
                vote = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Candidate invalid");
            }

        }
        if (candidates.contains(vote)) vote(id_card, vote);
        else System.out.println("\nCandidate not valid. Please try again.");
    }
}