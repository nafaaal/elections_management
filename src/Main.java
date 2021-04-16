package election;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("VOTING 2021");
        System.out.println("--------------------------");
        Scanner scanner = new Scanner(System.in);
        int process;
        Elections newElections = new Elections();
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
                newElections.admin();
            } else if (process == 2) {
                newElections.voting();
            } else {
                System.out.println("Enter valid process");
            }
        }
        scanner.close();

    }

}
