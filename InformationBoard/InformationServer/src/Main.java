//Galanis Manouil Ioannis 3212017021

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Person implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //οι τυπώσεις σε αυτή τη κλάση είναι για έλεγχο ότι όλα τα δεδομένα εισέρχονται σωστά
        try {
            Date currdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

            ServerSocket server;
            InetAddress bindAddr = InetAddress.getByName("127.0.0.1");
            Person p11 = new Person();

            server = new ServerSocket(80, 5, bindAddr);

            while (true) {
                Socket sock = server.accept();
                ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
                System.out.println("Connection accepted");
                
                String exitcode = "a";
                Object obj;
                Object ch;
                Object userName;
                Object passWord;
                Object ok;
                Object finish;
                //περιμενει να δει τι θελει να κανει ο χρηστης
                obj = input.readObject();
                if (obj.toString().equals("1")) {    //εισαγωγη ανακοινωσης
                    System.out.println(obj.toString());
                    //ζηταει username and password απο το χρηστη
                    output.writeObject("enter userName and passWord");
                    output.flush();
                    //διαβαζει τα στοιχεια
                    userName = input.readObject();
                    passWord = input.readObject();
                    System.out.println("userName : " + userName.toString());
                    System.out.println("passWord : " + passWord.toString());
                    //ρωταει αν θέλει να κανει registration or login
                    output.writeObject("you want to login or register?");
                    output.flush();
                    ch = input.readObject();
                    System.out.println("user want to : " + ch.toString());
                    while (true) {
                        if ("login".equals(ch.toString())) {
                            while (true) {
                                Login l = new Login(userName.toString(), passWord.toString());
                                /*καλει την κλαση login για να ελεγξει αν υπαρχει ο χρηστης στο αρχειο users.dat
                            αν δεν υπαρχει επιστρεφει τη τιμη 1 αλλιως 0 (αν επιστρεψει 0 ζηταει ξανα τα στοιχεια του χρηστη μεχρι να δωσει τα σωστα)
                            και κατοπιν ζηταει την ανακοινωση απο το χρηστη (τιτλο και περιεχόμενο)
                            Δημιουργεί ενα αντικειμενο της κλασης Announcement (ειναι η κλαση που διαχειρίζεται τις ανακοινώσεις)
                            και περναει ως παραμετρους τιτλο , περιεχομενο, τρεχουσα ημερομηνια απο το συστημα (ημερομηνια καταχωρισης της ανακοινωσης).
                            Κατοπιν καλει την συναρτηση addAnn που προσθέτει την ανακοινωση στο αντιστοιχο αρχειο
                            Αν προστεθει με επιτυχια επιστρεφει την τιμη 1 και ενημερωνει τον χρηστη
                            αλλιως στέλνει μηνυμα λαθους
                                 */
                                if (l.res1 == 1) {
                                    System.out.println("login complete");
                                    output.writeObject("login complete");
                                    output.flush();
                                    output.writeObject("write the title of your announcement");
                                    output.flush();
                                    Object title = input.readObject();
                                    System.out.println("title: " + title.toString());
                                    output.writeObject("write the index of your announcement");
                                    output.flush();
                                    Object index = input.readObject();
                                    System.out.println("index: " + index.toString());
                                    Announcement an1 = new Announcement(title.toString(), index.toString(), userName.toString(), currdate);
                                    int re = an1.addAnn(an1);
                                    if (re == 1) {
                                        System.out.println("Announcement written complete");
                                        output.writeObject("your announcement has been stored succesfully");
                                        output.flush();
                                        output.close();
                                        // sock.close();
                                    } else {
                                        System.out.println("problem with announcement");
                                        output.writeObject("your announcement has not been stored succesfully");
                                        output.flush();
                                    }

                                    break;
                                } else if (l.res1 == 0) {
                                    System.out.println("login failed");
                                    output.writeObject("login failed. username or pass wasnt corret. Sent me agian your username and password");
                                    output.flush();
                                    userName = input.readObject();
                                    passWord = input.readObject();
                                    System.out.println("userName : " + userName.toString());
                                    System.out.println("passWord : " + passWord.toString());

                                }
                            }
                            break;
                        } else if ("register".equals(ch.toString())) {
                            while (true) {
                                Register r = new Register(userName.toString(), passWord.toString());
                                /*καλει την κλαση register για να ελεγξει αν υπαρχει ο χρηστης στο αρχειο users.dat
                            αν δεν υπαρχει επιστρεφει τη τιμη 0 αλλιως 1 (αν επιστρεψει 1 ζηταει ξανα τα στοιχεια του χρηστη μεχρι να δωσει τα σωστα)
                            και κατοπιν ζηταει την ανακοινωση απο το χρηστη (τιτλο και περιεχόμενο)
                            Δημιουργεί ενα αντικειμενο της κλασης Announcement (ειναι η κλαση που διαχειρίζεται τις ανακοινώσεις)
                            και περναει ως παραμετρους τιτλο , περιεχομενο, τρεχουσα ημερομηνια απο το συστημα (ημερομηνια καταχωρισης της ανακοινωσης).
                            Κατοπιν καλει την συναρτηση addAnn που προσθέτει την ανακοινωση στο αντιστοιχο αρχειο
                            Αν προστεθει με επιτυχια επιστρεφει την τιμη 1 και ενημερωνει τον χρηστη
                            αλλιως στέλνει μηνυμα λαθους
                                 */
                                if (r.res == 0) {
                                    System.out.println("registration comlete");
                                    output.writeObject("registration complete");
                                    output.flush();
                                    output.writeObject("write the title of your announcement");
                                    output.flush();
                                    Object title = input.readObject();
                                    System.out.println("title: " + title.toString());
                                    output.writeObject("write the index of your announcement");
                                    output.flush();
                                    Object index = input.readObject();
                                    System.out.println("index: " + index.toString());
                                    Announcement an1 = new Announcement(title.toString(), index.toString(), userName.toString(), currdate);
                                    int re = an1.addAnn(an1);
                                    if (re == 1) {
                                        System.out.println("Announcement written complete");
                                        output.writeObject("your announcement has been stored succesfully");
                                        output.flush();
                                        output.close();
                                        // sock.close();
                                    } else {
                                        System.out.println("problem with announcement");
                                        output.writeObject("your announcement has not been stored succesfully");
                                        output.flush();
                                    }
                                    break;
                                } else if (r.res == -1) {
                                    System.out.println("registration failed");
                                    output.writeObject("registration failed sent me agian your username and password");
                                    output.flush();
                                    userName = input.readObject();
                                    passWord = input.readObject();
                                    System.out.println("userName : " + userName.toString());
                                    System.out.println("passWord : " + passWord.toString());
                                }
                            }
                            break;
                        } else {
                            System.out.println("neither login or register was choosen. Plz try again");
                            output.writeObject("neither login or register was choosen. Plz try again");
                            output.flush();
                            ch = input.readObject();
                            System.out.println("new ch: " + ch);

                        }
                    }
                } else if (obj.equals("2")) //Τροποποίηση Ανακοίνωσης 
                {
                    ArrayList<Object> ann12 = new ArrayList<>();
                    File file12 = new File("announcements.dat");
                    System.out.println(obj.toString());
                    //ζηταει username and password απο το χρηστη
                    output.writeObject("enter userName and passWord");
                    output.flush();
                    //διαβαζει τα στοιχεια
                    userName = input.readObject();
                    passWord = input.readObject();
                    System.out.println("userName : " + userName.toString());
                    System.out.println("passWord : " + passWord.toString());
                    //Login log = new Login(userName.toString(), passWord.toString());
                    while (true) {
                        Login l1 = new Login((String) userName, (String) passWord);
                        /*καλει την κλαση login για να ελεγξει αν υπαρχει ο χρηστης στο αρχειο users.dat
                            αν  υπαρχει επιστρεφει τη τιμη 1 αλλιως 0 (αν επιστρεψει 0 ζηταει ξανα τα στοιχεια του χρηστη μεχρι να δωσει τα σωστα)
                            και κατοπιν ζηταει απο το χρηστη ττην ανακοινωση που θέλει να τροποποιήσει (τιτλο και το νέο περιεχόμενο)
                            Δημιουργεί ενα αντικειμενο της κλασης Announcement (ειναι η κλαση που διαχειρίζεται τις ανακοινώσεις)
                            Κατοπιν καλει την συναρτηση changeAnnouncement που τροποποιεί την ανακοινωση στο αντιστοιχο αρχειο
                            Αν τροποποιηθεί με επιρτυχια επιστρεφει την τιμη 1 και ενημερωνει τον χρηστη
                            αλλιως στέλνει μηνυμα λαθους
                         */
                        if (l1.res1 == 1) {
                            System.out.println("login complete");
                            output.writeObject("login complete");
                            output.flush();
                            while (true) {
                                output.writeObject("write the title of the announcement you want to change");
                                output.flush();
                                Object title1 = input.readObject();
                                System.out.println("title: " + title1.toString());
                                output.writeObject("Rewrite the index of your announcement");
                                output.flush();
                                Object index1 = input.readObject();
                                System.out.println("index: " + index1.toString());
                                Announcement b = new Announcement();
                                int result;
                                result = b.changeAnnouncement(title1.toString(), index1.toString(), userName.toString());
                                if (result == 1) {
                                    System.out.println("Announcement written complete");
                                    output.writeObject("your announcement has been Restored succesfully");
                                    output.flush();
                                    break;
                                } else {
                                    System.out.println("Wrong title please try again");
                                    output.writeObject("Wrong title please try again");
                                    output.flush();
                                }

                            }
                        } else if (l1.res1 == 0) {
                            System.out.println("login failed");
                            output.writeObject("login failed. username or pass wasnt corret. Sent me agian your username and password");
                            output.flush();
                            userName = input.readObject();
                            passWord = input.readObject();
                            System.out.println("userName : " + userName.toString());
                            System.out.println("passWord : " + passWord.toString());
                        }
                        break;
                    }
                } else if (obj.equals("3")) //διαγραφη ανακοινωσεων 
                {
                    System.out.println(obj.toString());
                    //ζηταει username and password απο το χρηστη
                    output.writeObject("enter userName and passWord");
                    output.flush();
                    //διαβαζει τα στοιχεια
                    userName = input.readObject();
                    passWord = input.readObject();
                    System.out.println("userName : " + userName.toString());
                    System.out.println("passWord : " + passWord.toString());
                    while (true) {
                        Login l2 = new Login(userName.toString(), passWord.toString());
                        /*καλει την κλαση login για να ελεγξει αν υπαρχει ο χρηστης στο αρχειο users.dat
                            αν  υπαρχει επιστρεφει τη τιμη 1 αλλιως 0 (αν επιστρεψει 0 ζηταει ξανα τα στοιχεια του χρηστη μεχρι να δωσει τα σωστα)
                            και κατοπιν ζηταει απο το χρηστη ττην ανακοινωση που θέλει να διαγραψει (τιτλο και το νέο περιεχόμενο)
                            Δημιουργεί ενα αντικειμενο της κλασης Announcement (ειναι η κλαση που διαχειρίζεται τις ανακοινώσεις)
                            Κατοπιν καλει την συναρτηση deleteAnnouncement που διαγραφει την ανακοινωση στο αντιστοιχο αρχειο
                            Αν διαγραφει με επιρτυχια επιστρεφει την τιμη 1 και ενημερωνει τον χρηστη
                            αλλιως στέλνει μηνυμα λαθους
                         */
                        if (l2.res1 == 1) {
                            System.out.println("login complete");
                            output.writeObject("login complete");
                            output.flush();
                            while (true) {
                                output.writeObject("write the title of the announcement you want to delete");
                                output.flush();
                                Object title1 = input.readObject();
                                System.out.println("title: " + title1.toString());
                                output.writeObject("write the index of your announcement");
                                output.flush();
                                Object index2 = input.readObject();
                                System.out.println("index: " + index2.toString());
                                Announcement b = new Announcement();
                                int result;
                                result = b.deleteAnnouncement(title1.toString(), index2.toString(), userName.toString());
                                if (result == 1) {
                                    System.out.println("Announcement deleted complete");
                                    output.writeObject("your announcement has been deleted succesfully");
                                    output.flush();
                                    output.close();
                                    input.close();
                                    //  sock.close();
                                    break;
                                } else {
                                    System.out.println("Announcement deleted faild");
                                    output.writeObject("your announcement has not been deleted succesfully");
                                    output.flush();
                                    output.close();
                                    input.close();
                                    // sock.close();
                                }
                            }

                        } else if (l2.res1 == 0) {
                            System.out.println("login failed");
                            output.writeObject("login failed. username or pass wasnt corret. Sent me agian your username and password");
                            output.flush();
                            userName = input.readObject();
                            passWord = input.readObject();
                            System.out.println("userName : " + userName.toString());
                            System.out.println("passWord : " + passWord.toString());
                        }

                        break;
                    }

                } else if (obj.equals("4")) //εμφανιση ανακοινωσεων.
                {
                    System.out.println(obj.toString());
                    //ζηταει username and password απο το χρηστη
                    output.writeObject("enter userName and passWord");
                    output.flush();
                    //διαβαζει τα στοιχεια
                    userName = input.readObject();
                    passWord = input.readObject();
                    System.out.println("userName : " + userName.toString());
                    System.out.println("passWord : " + passWord.toString());

                    while (true) {
                        Login l2 = new Login(userName.toString(), passWord.toString());
                        /*καλει την κλαση login για να ελεγξει αν υπαρχει ο χρηστης στο αρχειο users.dat
                            αν  υπαρχει επιστρεφει τη τιμη 1 αλλιως 0 (αν επιστρεψει 0 ζηταει ξανα τα στοιχεια του χρηστη μεχρι να δωσει τα σωστα)
                            και κατοπιν ζηταει απο το χρηστη αρχικη και τελικη ημερομηνια για να εμφανισει τις αντιστοιχες ανακοινωσεις.
                            Δημιουργεί ενα αντικειμενο της κλασης Announcement (ειναι η κλαση που διαχειρίζεται τις ανακοινώσεις)
                            Κατοπιν καλει την συναρτηση showAnn που εμφανιζει την/τις ανακοινωση απο το αντιστοιχο αρχειο
                            Στη συνέχεια στέλνει στο client κάθε ανακοίνωση που βρέθηκε ξεχωριστα και περιμενει μια απάντση. 
                            Αν λάβει οκ στέλνει την επόμενη ανακοινωση μεχρι να τελειωσουν ολες
                         */
                        if (l2.res1 == 1) {
                            System.out.println("login complete");
                            output.writeObject("login complete");
                            output.flush();
                            while (true) {
                                output.writeObject("write start date:");
                                output.flush();
                                Object sdate = input.readObject();
                                output.writeObject("write finish date");
                                output.flush();
                                Object fdate = input.readObject();
                                System.out.println("sdate: " + sdf.format(sdate));
                                System.out.println("fdate: " + sdf.format(fdate));
                                Date startDate = (Date) sdate;
                                Date finishDate = (Date) fdate;
                                Announcement ann = new Announcement();
                                int c = ann.showAnn(startDate, finishDate);
                                System.out.println("c: " + c);
                                output.writeObject(c);
                                output.flush();
                                for (int h = 0; h < c; h++) {
                                    Object obj6 = ann.ann4.get(h);
                                    System.out.println("obgj: " + ann.ann4.get(h));
                                    output.writeObject(obj6);
                                    output.flush();
                                    Object ans = input.readObject();
                                    if (ans.equals("ok")) {
                                    }
                                }
                                break;
                            }

                            break;

                        } else if (l2.res1 == 0) {
                            System.out.println("login failed");
                            output.writeObject("login failed. username or pass wasnt corret. Sent me agian your username and password");
                            output.flush();
                            userName = input.readObject();
                            passWord = input.readObject();
                            System.out.println("userName : " + userName.toString());
                            System.out.println("passWord : " + passWord.toString());
                        }

                        break;
                    }
                }else if (obj.equals("5")) //exit
                {
                    System.out.println(obj.toString());
                    sock.close();
                    break;
                }
                //  break;

            }
        } catch (IOException e) {

        }
    }
}
