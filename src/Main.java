import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void admin() {
        System.out.println("admin");
    }

    public static void voting(){
        System.out.println("voting");
    }
}
    public static void main(String[] args) {
//        insert("Ibrahim Rushdhee", "Lh.Hinnavaru", "Teerumage", "A483252", 0, 0);
//        Elections.query("SELECT * FROM voters_list");
//        System.out.println(Elections.has_voted("A22761"));
//        Elections.statistics();
//        System.out.println(Elections.vote("A26276s1", 2));

        System.out.println("WELCOME TO VOTING HEHEHEHE");
        Scanner scanner = new Scanner(System.in);
        int process;
        while(true){
            System.out.println("1. Elections Admin");
            System.out.println("2. Voting");
            System.out.println("Enter process number: ");
            try {
                process = scanner.nextInt();
            } catch (Exception e){
                System.out.println("Enter Valid number");
                break;
            }
            if (process == 1){
                admin();
            } else if (process == 2){
                voting();
            } else{
                System.out.println("Enter valid process");
            }
//
//        }
    }

}
