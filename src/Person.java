package election;
import java.util.ArrayList;

public abstract class Person {
    String name;
    String island;
    String address;

    public abstract ArrayList<Person> get_data();

    public abstract void print_data();

}