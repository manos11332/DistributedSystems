
import java.io.Serializable;
import java.util.ArrayList;

public class Person implements  Serializable {
    /*
    κλαση για τη διαχειριση των users
    */
    private final ArrayList<Person> database = new ArrayList<>();
    private String username;
    private String password;

    public Person() {
        Person p1 = new Person("manos", "1234");
        Person p2 = new Person("kostas", "5678");
        database.add(p1);
        database.add(p2);
    }

    public Person(String pusername, String ppassword) {
        username = pusername;
        password = ppassword;


    }
    public void addtodatabase(Person a)
    {
        database.add(a);
    }

    public void set_userName(String pusername) {
        username = pusername;
    }

    public void set_user_password(String ppassword) {
        password = ppassword;
    }

    public String get_userName() {
        return username;
    }

    public String get_password() {
        return password;
    }

   

   
    @Override
    public String toString() {
        return ("userName: " + this.get_userName() + " password: " + this.get_password());
    }

}
