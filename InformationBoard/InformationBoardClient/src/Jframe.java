
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Jframe extends JFrame implements KeyListener {

    /*
    οι τυπώσεις σε αυτή τη κλάση είναι για έλεγχο ότι όλα τα δεδομένα εισέρχονται σωστά
    η κλαση announcement σε αυτο το project υπαρχει για να μπορω να τυπωνω (με την tostring)
     τα αντικειμενα τυπου annoncement
   
    Αυτη η κλαση περιλαμβάνει τη αλληλουχια των απαντήσεων του client ανάλογα με 
    αυτο που λαμβάνει καθε φορά απο το server
    Επισης εδω υλοποιω και τα γραφικά της εφαρμογης:
    Εχω 4 κουμπια ένα για κάθε λειτουργία της εφαρμογης 
     */
    public JFrame fx = new JFrame("Announcement System");
    public JButton ch1 = new JButton("δημιουργία μίας νέας ανακοίνωσης");
    public JButton ch2 = new JButton("Τροποποίησημιας ανακοίνωσης");
    public JButton ch3 = new JButton("Διαγραφή μιας ανακοίνωσης");
    public JButton ch4 = new JButton("Εμφάνιση ανακοινώσεων");
    public JButton ch5 = new JButton("exit");
    public int a;
    public String ch, ch11;

    public Jframe() {

 /*
        Κουμπ1 για εισαγωγη ανακοινωσης:
        Ο server ζηατει username και pssword
        Ο client στελνει τα στοιχεια
        Ο server ρωταει αν θελει να κανιε login η register
          Αν ο client στειλει login
           ο server τσεκαρει αν ο χρηστης υπαρχει στο συστημα
           Αν υπαρχει τοτε ζηταει τιτλο και περιεχομανο ανακοινωσης
            ο client στελενιε τα στοιχεια
            ο server καταχωρει την ανακοινωση και στελνει αντιστοιχο μηνυμα
            ο client λαμβανει το μηνυμα
           Αν δεν υπαρχει εμφανιζει σκετικο μηνυμα στο client και ζηταει ξανα τα στοιχεια
            ο client ξαναστελνει τα στοιχεια(usernmae , password)
            Επαναλλαμβάνεται η ιδια διαδικασια
          Αν ο client επιλεξει register
           ο server τσεκαρει αν ο χρηστης υπαρχει στο συστημα
           Αν υπαρχει δεν τοτε ζηταει τιτλο και περιεχομανο ανακοινωσης
           ο client στελενιε τα στοιχεια
           ο server καταχωρει την ανακοινωση και στελνει αντιστοιχο μηνυμα
           ο client λαμβανει το μηνυμα
           Αν  υπαρχει εμφανιζει σκετικο μηνυμα στο client και ζηταει ξανα τα στοιχεια
           ο client ξαναστελνει τα στοιχεια(usernmae , password)
           Επαναλλαμβάνεται η ιδια διαδικασια
          Αν δεν επιλέξει ουτε το ένα ουτε το αλλο εμφανιζεται καταλληλο μηνυμα και συνεχιζεται η διαδικασια απο εδω
         */
        ch1.addActionListener(new ActionListener() { //εισαγωγη ανακοινωσης
            public void actionPerformed(ActionEvent e) {
                try {

                    String address = "127.0.0.1";
                    int port = 80;
                    Socket sock = new Socket(address, port);
                    Object request = "connect to 127.0.0.1";
                    System.out.println("Sending Messages to the Server... :" + request);
                    System.out.println("Connecting to " + sock.getInetAddress() + " andport " + sock.getPort());
                    System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());

                    ch = "1";
                    Object s3 = ch;
                    outstream.writeObject(s3);
                    outstream.flush();
                    do {
                        Object response;
                        Object response1;
                        Object response2;
                        Object response4;
                        Object response5;
                        Object response6;
                        Object response22;
                        Object response33;
                        Object exitResponse1;
                        // εισαγωγη username και password
                        response = instream.readObject();
                        if ("enter userName and passWord".equals(response.toString())) {
                            System.out.println("server asks: " + response.toString());
                            String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                            String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                            outstream.writeObject(Name);
                            outstream.flush();
                            outstream.writeObject(Pass);
                            outstream.flush();
                        }
                        response1 = instream.readObject();
                        if ((response1.toString()).equals("you want to login or register?")) {
                            System.out.println("server asked: " + response1.toString());
                            String choise = (String) JOptionPane.showInputDialog(fx, "login or register?", "login or register?");
                            outstream.writeObject(choise);
                            outstream.flush();
                        }

                        //ερωωημα για login η register
                        response4 = instream.readObject();
                        if ((response4.toString()).equals("login complete")) {

                            JOptionPane.showMessageDialog(fx, response4.toString());

                        } else if ((response4.toString()).equals("registration complete")) {

                            JOptionPane.showMessageDialog(fx, response4.toString());

                        } else if (response4.toString().equals("registration failed sent me agian your username and password")) {
                            while (true) {
                                JOptionPane.showMessageDialog(fx, response4.toString());
                                String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                                String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                                outstream.writeObject(Name);
                                outstream.flush();
                                outstream.writeObject(Pass);
                                outstream.flush();

                                response5 = instream.readObject();
                                if ((response5.toString()).equals("registration comlete")) {

                                    System.out.println("server says: " + response5.toString());
                                    JOptionPane.showMessageDialog(fx, response5.toString());
                                    break;
                                }
                            }
                        } else if (response4.toString().equals("login failed. username or pass wasnt corret. Sent me agian your username and password")) {
                            while (true) {
                                JOptionPane.showMessageDialog(fx, response4.toString());
                                String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                                String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                                outstream.writeObject(Name);
                                outstream.flush();
                                outstream.writeObject(Pass);
                                outstream.flush();
                                response6 = instream.readObject();
                                if ((response6.toString()).equals("login complete")) {
                                    System.out.println("server says: " + response6.toString());
                                    JOptionPane.showMessageDialog(fx, response6.toString());
                                    break;
                                }
                            }
                        } else if (response4.toString().equals("neither login or register was choosen. Plz try again")) {
                            while (true) {
                                JOptionPane.showMessageDialog(fx, response4.toString());
                                Object newch = (String) JOptionPane.showInputDialog(fx, "login or register?", "login or register?");
                                outstream.writeObject(newch);
                                outstream.flush();

                                response2 = instream.readObject();
                                if (response2.equals("login complete") || response2.equals("registration comlete")) {
                                    JOptionPane.showMessageDialog(fx, response2.toString());
                                    break;
                                }
                            }

                        }
                        response22 = instream.readObject();
                        if ("write the title of your announcement".equals(response22.toString())) {
                            System.out.println("server asked: " + response22.toString());

                            String x = (String) JOptionPane.showInputDialog(fx, "write the title of your announcement", "write the title of your announcement");
                            outstream.writeObject(x);
                            outstream.flush();
                        }
                        response33 = instream.readObject();
                        if ("write the index of your announcement".equals(response33.toString())) {
                            System.out.println("server asked: " + response33.toString());

                            String x1 = (String) JOptionPane.showInputDialog(fx, "write the index of your announcement", "write the index of your announcement");
                            outstream.writeObject(x1);
                            outstream.flush();
                        }
                        exitResponse1 = instream.readObject();
                        if ("your announcement has been stored succesfully".equals(exitResponse1.toString())) {
                            System.out.println("server says: " + exitResponse1.toString());
                            JOptionPane.showMessageDialog(fx, exitResponse1.toString());
                            outstream.close();
                            instream.close();
                            sock.close();
                            break;
                        } else if ("your announcement has not been stored succesfully".equals(exitResponse1.toString())) {
                            System.out.println("server says: " + exitResponse1.toString());
                            JOptionPane.showMessageDialog(fx, exitResponse1.toString());
                            outstream.close();
                            instream.close();
                            sock.close();
                            break;
                        }
                    } while (true);
                    sock.close();
                } catch (IOException | ClassNotFoundException ex) {
                    //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        /*
        Κουμπ1 για τροποποιηση ανακοινωσης:
        Ο server ζηατει username και pssword
        Ο client στελνει τα στοιχεια
        Αν ειναι σωστα 
         ο server τσεκαρει αν ο χρηστης υπαρχει στο συστημα
         Αν υπαρχει τοτε ζηταει τιτλο και περιεχομανο ανακοινωσης προς τροποποιηση
          ο client στελενιε τα στοιχεια
          ο server τροποποιει την ανακοινωση και στελνει αντιστοιχο μηνυμα
          ο client λαμβανει το μηνυμα
         Αν δεν υπαρχει ο τιτλος της ανακοινωσης εμφανιζει σκετικο μηνυμα στο client και ζηταει ξανα τα στοιχεια (τιτλο κ περιεχομενο)
          ο client ξαναστελνει τα στοιχεια
          Επαναλλαμβάνεται η ιδια διαδικασια
        Αν δεν ειναι σωστα
         εμφανιζει καταλληλο μηνυμα και ξαναζηταει τα στοιχεια(username κ password)
         ο client ξανα στελνει τα στοιχεια
         Επαναλλαμβάνεται η ιδια διαδικασια
         */
        ch2.addActionListener(new ActionListener() { //τροποποιηση ανακοινωσης
            public void actionPerformed(ActionEvent e) {
                try {
                    ch = "2";

                    String address = "127.0.0.1";
                    int port = 80;
                    Socket sock = new Socket(address, port);
                    Object request = "connect to 127.0.0.1";
                    System.out.println("Sending Messages to the Server... :" + request);
                    System.out.println("Connecting to " + sock.getInetAddress() + " andport " + sock.getPort());
                    System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());

                    Object s3 = ch;
                    Object obj;
                    outstream.writeObject(s3);
                    outstream.flush();
                    do {
                        Object response;
                        Object response1;
                        Object response2;
                        Object response3;
                        Object response4;
                        Object response6;
                        Object response22;
                        Object response33;
                        Object exitResponse1;
                        if ("enter userName and passWord".equals((response = instream.readObject()).toString())) {
                            System.out.println("server asks: " + response.toString());
                            String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                            String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                            outstream.writeObject(Name);
                            outstream.flush();
                            outstream.writeObject(Pass);
                            outstream.flush();
                        }
                        response4 = instream.readObject();
                        if ((response4.toString()).equals("login complete")) {
                            System.out.println("server says: " + response4.toString());
                            JOptionPane.showMessageDialog(fx, response4.toString());
                        } else if (response4.toString().equals("login failed. username or pass wasnt corret. Sent me agian your username and password")) {
                            while (true) {
                                System.out.println("server says: " + response4.toString());
                                String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                                String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                                outstream.writeObject(Name);
                                outstream.flush();
                                outstream.writeObject(Pass);
                                outstream.flush();
                                response6 = instream.readObject();
                                if ((response6.toString()).equals("login complete")) {
                                    System.out.println("server says: " + response6.toString());
                                    JOptionPane.showMessageDialog(fx, response6.toString());
                                    break;
                                }
                            }
                        }
                        response22 = instream.readObject();
                        if ("write the title of the announcement you want to change".equals(response22.toString())) {
                            System.out.println("server asked: " + response22.toString());
                            String x = (String) JOptionPane.showInputDialog(fx, response22.toString(), response22.toString());
                            outstream.writeObject(x);
                            outstream.flush();
                        }
                        response33 = instream.readObject();
                        if (response33.toString().equals("Rewrite the index of your announcement")) {
                            System.out.println("server asked: " + response33.toString());
                            String x1 = (String) JOptionPane.showInputDialog(fx, response33.toString(), response33.toString());
                            outstream.writeObject(x1);
                            outstream.flush();
                        }
                        exitResponse1 = instream.readObject();
                        if ((exitResponse1.toString()).equals("your announcement has been Restored succesfully")) {
                            System.out.println("server says: " + exitResponse1.toString());
                            JOptionPane.showMessageDialog(fx, exitResponse1.toString());
                            outstream.close();
                            //sock.close();
                        } else if ((exitResponse1.toString()).equals("Wrong title please try again")) {
                            while (true) {
                                System.out.println("server says: " + exitResponse1.toString());
                                JOptionPane.showMessageDialog(fx, exitResponse1.toString());
                                response2 = instream.readObject();
                                String x11 = (String) JOptionPane.showInputDialog(fx, response2.toString(), response2.toString());
                                outstream.writeObject(x11);
                                outstream.flush();
                                response3 = instream.readObject();
                                String x12 = (String) JOptionPane.showInputDialog(fx, response3.toString(), response3.toString());
                                outstream.writeObject(x12);
                                outstream.flush();
                                response1 = instream.readObject();
                                if ((response1.toString()).equals("your announcement has been Restored succesfully")) {
                                    System.out.println("server says: " + exitResponse1.toString());
                                    JOptionPane.showMessageDialog(fx, exitResponse1.toString());
                                    outstream.close();
                                    instream.close();
                                    //sock.close();
                                    break;
                                }
                            }

                        }

                    } while (true);
                } catch (IOException | ClassNotFoundException ex) {
                    // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        /*
        Κουμπ1 για Διαγραφη ανακοινωσης:
        Ο server ζηατει username και pssword
        Ο client στελνει τα στοιχεια
        Αν ειναι σωστα 
         ο server τσεκαρει αν ο χρηστης υπαρχει στο συστημα
         Αν υπαρχει τοτε ζηταει τιτλο και περιεχομανο ανακοινωσης προς διαγραφη
          ο client στελενιε τα στοιχεια
          Αν είναι σωστά
           ο server διαγραφει την ανακοινωση και στελνει αντιστοιχο μηνυμα
           ο client λαμβανει το μηνυμα
          Αν δεν υπαρχει ο τιτλος της ανακοινωσης εμφανιζει σκετικο μηνυμα στο client και ζηταει ξανα τα στοιχεια (τιτλο κ περιεχομενο)
          ο client ξαναστελνει τα στοιχεια
          Επαναλλαμβάνεται η ιδια διαδικασια
        Αν δεν ειναι σωστα
         εμφανιζει καταλληλο μηνυμα και ξαναζηταει τα στοιχεια(username κ password)
         ο client ξανα στελνει τα στοιχεια
         Επαναλλαμβάνεται η ιδια διαδικασια
         */
        ch3.addActionListener(new ActionListener() {  //Διαγραφη ανακοινωσης
            public void actionPerformed(ActionEvent e) {
                Scanner scan = new Scanner(System.in);
                try {
                    ch = "3";

                    String address = "127.0.0.1";
                    int port = 80;
                    Socket sock = new Socket(address, port);
                    Object request = "connect to 127.0.0.1";
                    System.out.println("Sending Messages to the Server... :" + request);
                    System.out.println("Connecting to " + sock.getInetAddress() + " andport " + sock.getPort());
                    System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                    Object s3 = ch;
                    outstream.writeObject(s3);
                    outstream.flush();
                    do {
                        Object response;
                        Object response1;
                        Object response2;
                        Object response3;
                        Object response4;
                        Object response5;
                        response = instream.readObject();
                        if ("enter userName and passWord".equals(response.toString())) {
                            System.out.println("server asks: " + response.toString());
                            String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                            String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                            outstream.writeObject(Name);
                            outstream.flush();
                            outstream.writeObject(Pass);
                            outstream.flush();
                        }

                        response1 = instream.readObject();
                        if ((response1.toString()).equals("login complete")) {
                            System.out.println("server says: " + response1.toString());
                            JOptionPane.showMessageDialog(fx, response1.toString());
                        } else if (response1.toString().equals("login failed. username or pass wasnt corret. Sent me agian your username and password")) {
                            while (true) {
                                System.out.println("server says: " + response1.toString());
                                String Name = scan.nextLine();
                                String Pass = scan.nextLine();
                                outstream.writeObject(Name);
                                outstream.flush();
                                outstream.writeObject(Pass);
                                outstream.flush();

                                response2 = instream.readObject();
                                if ((response2.toString()).equals("login complete")) {
                                    System.out.println("server says: " + response2.toString());
                                    break;
                                }
                            }
                        }
                        response3 = instream.readObject();
                        if ((response3.toString()).equals("write the title of the announcement you want to delete")) {
                            System.out.println("server asked: " + response3);
                            String x = (String) JOptionPane.showInputDialog(fx, response3.toString(), response3.toString());
                            outstream.writeObject(x);
                            outstream.flush();
                        }
                        response5 = instream.readObject();
                        if ("write the index of your announcement".equals(response5.toString())) {
                            System.out.println("server asked: " + response5.toString());
                            String x1 = (String) JOptionPane.showInputDialog(fx, response5.toString(), response5.toString());
                            outstream.writeObject(x1);
                            outstream.flush();
                        }

                        response4 = instream.readObject();
                        if ((response4.toString()).equals("your announcement has been deleted succesfully")) {
                            System.out.println("server says: " + response4);
                            JOptionPane.showMessageDialog(fx, response4.toString());
                            outstream.close();
                            // sock.close();
                        } else if ((response4.toString()).equals("your announcement has not been deleted succesfully")) {
                            System.out.println("server says: " + response4);
                            JOptionPane.showMessageDialog(fx, response4.toString());
                            outstream.close();
                            // sock.close();
                        }

                    } while (true);
                } catch (IOException | ClassNotFoundException ex) {
                    //  Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        /*
        Κουμπ1 για εμφανιση ανακοινωσης:
        Ο server ζηατει username και pssword
        Ο client στελνει τα στοιχεια
        Αν ειναι σωστα 
         ο server τσεκαρει αν ο χρηστης υπαρχει στο συστημα
         Αν υπαρχει τοτε ζηταει ημερομηνια εναρξης και ληξης αναζητησης ανακοινωσεων προς εμφανιση
          ο client στελενιε τα στοιχεια
          ο server στέλενι μια μια την ανακοινωση και περιμενει το μηνυμα "οκ" για να στελνει την επομενη (αν υπαρχει)
          ο client λαμβανει το μηνυμα και στέλνει το "οκ"
          Επαναλαμβανεται μεχρι να τελειωσουν οι ανακοινωσεις
        Αν δεν ειναι σωστα
         εμφανιζει καταλληλο μηνυμα και ξαναζηταει τα στοιχεια(username κ password)
         ο client ξανα στελνει τα στοιχεια
         Επαναλλαμβάνεται η ιδια διαδικασια
         */
        ch4.addActionListener(new ActionListener() { //εμφανιση ανακοινωσεων
            public void actionPerformed(ActionEvent e) {
                ch = "4";
                Scanner scan = new Scanner(System.in);
                try {

                    String address = "127.0.0.1";
                    int port = 80;
                    Socket sock = new Socket(address, port);
                    Object request = "connect to 127.0.0.1";
                    System.out.println("Sending Messages to the Server... :" + request);
                    System.out.println("Connecting to " + sock.getInetAddress() + " andport " + sock.getPort());
                    System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                    Object s3 = ch;
                    outstream.writeObject(s3);
                    outstream.flush();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    do {
                        Object response;
                        Object response1;
                        Object response2;
                        Object response3;
                        Object response4;
                        Object response5;
                        int count = 0;
                        response = instream.readObject();
                        if ("enter userName and passWord".equals(response.toString())) {
                            System.out.println("server asks: " + response.toString());
                            String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                            String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                            outstream.writeObject(Name);
                            outstream.flush();
                            outstream.writeObject(Pass);
                            outstream.flush();
                        }

                        response1 = instream.readObject();
                        if ((response1.toString()).equals("login complete")) {
                            System.out.println("server says: " + response1.toString());
                            JOptionPane.showMessageDialog(fx, response1.toString());
                        } else if (response1.toString().equals("login failed. username or pass wasnt corret. Sent me agian your username and password")) {
                            while (true) {
                                System.out.println("server says: " + response1.toString());
                                String Name = (String) JOptionPane.showInputDialog(fx, "enter userName", "Enter userName ");
                                String Pass = (String) JOptionPane.showInputDialog(fx, "enter passWord", "Enter passWord");
                                outstream.writeObject(Name);
                                outstream.flush();
                                outstream.writeObject(Pass);
                                outstream.flush();

                                response2 = instream.readObject();
                                if ((response2.toString()).equals("login complete")) {
                                    System.out.println("server says: " + response2.toString());
                                    break;
                                }
                            }
                        }
                        response3 = instream.readObject();
                        if ((response3.toString()).equals("write start date:")) {
                            System.out.println("server asked: " + response3);
                            String x1 = (String) JOptionPane.showInputDialog(fx, response3.toString() + "\nformat: MM-dd-yyyy\n", response3.toString());
                            Date s = sdf.parse(x1);
                            outstream.writeObject(s);
                            outstream.flush();
                        }

                        response4 = instream.readObject();
                        if ((response4.toString()).equals("write finish date")) {
                            System.out.println("server says: " + response4);
                            String x2 = (String) JOptionPane.showInputDialog(fx, response4.toString() + "\nformat: MM-dd-yyyy\n", response4.toString());
                            Date s = sdf.parse(x2);
                            outstream.writeObject(s);
                            outstream.flush();
                        }

                        response5 = instream.readObject();
                        System.out.println("server says: " + response5);
                        count = (int) response5;
                        System.out.println("count: " + count);
                        Object response7[] = new Object[20];

                        if (count > 0) {
                            for (int i = 0; i < count; i++) {
                                response7[i] = instream.readObject();
                                outstream.writeObject("ok");
                                outstream.flush();
                                System.out.println("Announcement " + (i + 1) + " : " + ((Announcement) response7[i]).toString());
                                JOptionPane.showMessageDialog(fx, ((Announcement) response7[i]));
                                outstream.close();
                                instream.close();
                                // sock.close();
                            }
                        } else {
                            System.out.println("no Announcementsto this dates");
                            JOptionPane.showMessageDialog(fx, "no Announcementsto this dates");
                            outstream.close();
                            instream.close();
                            // sock.close();
                        }

                    } while (true);

                } catch (IOException | ClassNotFoundException | ParseException ex) {
                    // System.out.println("Error during I/O");
                    // ex.getMessage();
                }

            }
        });

        ch5.addActionListener(new ActionListener() { //exit
            public void actionPerformed(ActionEvent e) {
                try {
                    do {
                        String address = "127.0.0.1";
                        int port = 80;
                        Socket sock = new Socket(address, port);
                        Object request = "connect to 127.0.0.1";
                        System.out.println("Sending Messages to the Server... :" + request);
                        System.out.println("Connecting to " + sock.getInetAddress() + " andport " + sock.getPort());
                        System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());
                        ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                        ch = "5";
                        Object s3 = ch;
                        outstream.writeObject(s3);
                        outstream.flush();
                        sock.close();
                        System.exit(0);
                        break;
                    } while (true);
                } catch (IOException ex) {
                    //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        ch1.setBounds(0, 0, 400, 30);
        ch2.setBounds(0, 40, 400, 30);
        ch3.setBounds(0, 80, 400, 30);
        ch4.setBounds(0, 120, 400, 30);
        ch5.setBounds(0, 160, 400, 30);
        fx.add(ch1);
        fx.add(ch2);
        fx.add(ch3);
        fx.add(ch4);
        fx.add(ch5);
        fx.setLayout(null);
        fx.setBounds(700, 180, 400, 250);
        fx.setVisible(true);

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
