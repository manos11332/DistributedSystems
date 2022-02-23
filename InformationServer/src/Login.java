
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login extends Person implements Serializable {

    public int res1 = 0;
    public File file2222 = new File("users.dat");

    public Login(String usern, String userp) throws FileNotFoundException, IOException {
        /*
        Η κλαση login αυτο που κανει ειναι να διαβαζει τα δεδομενα απο το αρχειο users.dat 
        και να ελεγει αν ο χρηστης που επιθυμει να συνδεθει υπαρχει στο συτστημα
        Αν υπαρχει η μεταβλητη res θα παρει τιμη 1 , αλλιως θα μεινει 0 
        (αυτη ειναι προσβασιμη απο την κλαση main οταν δημιουργειται το αντικειμενο της κλασης
        login)
        */
        String fileName = "users.dat";
        Person m12 = new Person(usern, userp);
        System.out.println("m12: " + m12.toString());

         try {
            SerializationUtil.deserialize(fileName);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object obj11;
        ObjectInputStream ine;
        try {
            FileInputStream fga = new FileInputStream("users.dat");
            ine = new ObjectInputStream(fga);
            while ((obj11 = ine.readObject()) != null) {
                System.out.println("obj1 " + obj11.toString());
                if ((obj11 instanceof Person)) {
                    if (obj11.toString().equals(m12.toString())) 
                    {
                        res1 = 1;
                        System.out.println("user identified  login succesfull");
                        ine.close();
                        break;
                    } else {
                    }
                }
            }
            ine.close();
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
