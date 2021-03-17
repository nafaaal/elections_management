import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void admin() {
        System.out.println("admin");
    }

    public static String voting() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID Card number");
        String id_card = scanner.nextLine();
        if (Elections.eligible_check(id_card) == -1) {
            return "Not registered";
        } else if (Elections.eligible_check(id_card) == 1) {
            return "Already voted.";
         } else {
            // PRINT CANDIDATES
            System.out.println("Enter Vote: ");
            int vote = scanner.nextInt();
            return Elections.vote(id_card, vote);
        }
    }

    public static void main(String[] args) {
//        insert("Ibrahim Rushdhee", "Lh.Hinnavaru", "Teerumage", "A483252", 0, 0);
//        Elections.query("SELECT * FROM voters_list");
//        System.out.println(Elections.has_voted("A22761"));
//        Elections.statistics();

        System.out.println("WELCOME TO VOTING HEHEHEHE");
        Scanner scanner = new Scanner(System.in);
        int process;
        while (true) {
            System.out.println("1. Elections Admin");
            System.out.println("2. Voting");
            System.out.println("Enter process number: ");
            try {
                process = scanner.nextInt();
                if (process == -1) break;
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
//
//        }
        }
        scanner.close();

    }
}