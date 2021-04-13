package election;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void enter_voter() {
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
        Elections.insert(voter);

    }

    public static void admin() {
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
            } catch (Exception e) {
                System.out.println("Enter Valid number");
                break;
            }

            if (process == 1) {
                Elections.candidates_list();
            } else if (process == 2) {
                Elections.voter_list();
            } else if (process == 3) {
                enter_voter();
            } else if (process == 4) {
                Elections.statistics();
            } else {
                System.out.println("Enter valid process");
            }

        }
    }

    public static String voting() {
        int vote = 0;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> candidates = Elections.candidate_numbers();
        System.out.print("Enter ID Card number: ");
        String id_card = scanner.nextLine();
        if (Elections.eligible_check(id_card) == -1) {
            return "Not registered";
        } else if (Elections.eligible_check(id_card) == 1) {
            System.out.println("");
            return "Already voted.";
        } else {
            System.out.println("");
            Elections.candidates_list();
            System.out.println("");
            System.out.print("Enter Candidate Number : ");

            while (true) {

                try {
                    vote = scanner.nextInt();
                } catch (Exception e) {
                    break;
                }
            }

        }

        if (candidates.contains(vote)) {
            return Elections.vote(id_card, vote);
        } else {
            System.out.println("");
            return "Candidate not valid. Please try again.";
        }
    }

    public static void main(String[] args) {
        System.out.println("VOTING 2021");
        System.out.println("--------------------------");
        Scanner scanner = new Scanner(System.in);
        int process;
        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("--------------------------");
            System.out.println("-1. Exit program.");
            System.out.println(" 1. Elections Admin");
            System.out.println(" 2. Voting \n");
            System.out.print("Enter process number: ");
            try {
                process = scanner.nextInt();
                System.out.print("\n");
                if (process == -1) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Enter Valid number");
                break;
            }
            if (process == 1) {
                admin();
            } else if (process == 2) {
                System.out.println(voting());
            } else {
                System.out.println("Enter valid process");
            }
        }
        scanner.close();

    }

}
