import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        insert("Ibrahim Rushdhee", "Lh.Hinnavaru", "Teerumage", "A483252", 0, 0);
//        Elections.query("SELECT * FROM voters_list");
//        System.out.println(Elections.has_voted("A22761"));
        ArrayList<int[]> votes =  Elections.statistics();
        for (int[] arr : votes){
            System.out.println(arr[0]);
            System.out.println(arr[1]);
            System.out.println("");
        }
    }
}
