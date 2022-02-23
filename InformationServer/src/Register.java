
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Register extends Person implements KeyListener, Serializable {

    public int res = 0;
    public File file222 = new File("users.dat");
   
    public Register(String usern, String userp) throws FileNotFoundException, IOException {
         /*
        Η κλαση register αυτο που κανει ειναι να διαβαζει τα δεδομενα απο το αρχειο users.dat 
        και να ελεγει αν ο χρηστης που επιθυμει να κανει εγγραφη υπαρχει στο συτστημα (το username του)
        Αν υπαρχει η μεταβλητη res θα παρει τιμη -1, αλλιως θα μεινει 0 
        (αυτη ειναι προσβασιμη απο την κλαση main οταν δημιουργειται το αντικειμενο της κλασης
        Register)
        Αν ειναι 0 θα παει στο αρχειο users.dat και θα γραψει τα στοιχεια του χρηστη
        */
        Person m11 = new Person(usern, userp);
        
        Object obj;
        ObjectInputStream in;
        try {
            FileInputStream fg = new FileInputStream("users.dat");
            in = new ObjectInputStream(fg);
            while ((obj = in.readObject()) != null) {
                if ((obj instanceof Person)) {
                    if ((((((Person) obj).get_userName())).equals(m11.get_userName())) &&((((Person) obj).get_password()).equals(m11.get_password())) ) {
                        res = -1;
                        System.out.println("user identified registration failed");

                        break;
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (res == 0) {
            m11.addtodatabase(m11);
            ObjectOutputStream out;
                if (file222.length() != 0) {
                    FileOutputStream ffd = new FileOutputStream("users.dat",true);
                    out = new ObjectOutputStream(ffd) {
                        protected void writeStreamHeader() throws IOException {
                            reset();
                        }
                    };
                    out.writeObject(m11);
                    out.flush();
                    out.close();
                } else if(file222.length()==0) {
                    FileOutputStream ffd = new FileOutputStream("users.dat");
                    out = new ObjectOutputStream(ffd);
                    out.writeObject(m11);
                    out.flush();
                    out.close();
                }

        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
