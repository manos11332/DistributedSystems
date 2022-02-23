
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Announcement implements Serializable {

    private static final long serialVersionUID = -3148841077569109765L;
    public ArrayList<Announcement> ann4 = new ArrayList<>();
    ArrayList<Announcement> ann1 = new ArrayList<>();
    public File file1 = new File("announcements.dat");
    private String title;
    private String index;
    private Date date;
    private String user_of_the_ann;

    public Announcement(String a, String b, String c, Date d) {

        title = a;
        index = b;
        user_of_the_ann = c;
        date = d;
    }

    public Announcement() {

    }

    public String get_title() {
        return title;
    }

    public String get_index() {
        return index;
    }

    public String get_user_of_the_ann() {
        return user_of_the_ann;
    }

    public void set_index(String s) {
        index = s;
    }

    public void print() {
        System.out.println("Announcement title :" + get_title() + " index:" + get_index());
    }

    public int addAnn(Announcement a) throws FileNotFoundException, IOException {
        /*
        Συναρτηση η οποια προσθέτει το αντικειμενο (ανακοινωση) που πηρε σαν ορισμα 
        στο αρχειο announcements.dat
         */
        ObjectOutputStream out2;
        if (file1.length() != 0) {
            FileOutputStream f2 = new FileOutputStream("announcements.dat", true);
            out2 = new ObjectOutputStream(f2) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            out2.writeObject(a);
            out2.flush();
            out2.close();
            return 1;
        } else if (file1.length() == 0) {
            FileOutputStream f2 = new FileOutputStream("announcements.dat");
            out2 = new ObjectOutputStream(f2);
            out2.writeObject(a);
            out2.flush();
            out2.close();
            return 1;
        } else {
            return 0;
        }
    }

    public int changeAnnouncement(String t, String index1, String u) throws IOException {
        int counter = 0;

        System.out.println("index1: " + index1);
        /*
        Η τρποπποίηση γίνεται ως εξης:
        Αρχικά περνάω όλα τα στοιχεία του αρχείου(onjects τυπου Announcement) σε μία λιστα τυπου Announcement. 
        Μετα δημιουργώ ενα αντικειμενο της κλασσης Announcemnet με τα στοιχεια που εδωσε ο χρηστης (τον τιτλο ,το νεο περιεχόμενο , την ημερα τροποποιησης και το username του).
        Στη συνέχεια αναζητώ στη λιστα αν υπαρχει αντικειμενο με τιτλο ιδιο με αυτον που εδωσε ο χρηστης. Αν υπαρχει το διαγράφω και θέτω  τιμη στον counter ιση με 1
        Στην ουσια δηλαδη επικυρωνω οτι βρεθηκε η ανακοινωση και εγινε η διαγραφη προκειμενου να εκτελεστει ο επομενος κωδικας
        και κατοπιν προσθετω το αντικειμενο που εφτιαξα στη λιστα. 
        Τελος γράφω καθε στοιχειο της λιστας ξανα στο αρχειο
        Αν η τιμη του counter μεινει 0 τοτε σημαινει οτι δεν βρεθηκε ανακοινωση με τον τιτλο που εδωσε ο χρηστης οποτε δεν τροποποιείται καμια και εμφανιζεται αντιστοιχο μηνυμα
         */
        Object obj2 = null;
        ObjectInputStream in3;
        FileInputStream fg;
        try {

            fg = new FileInputStream("announcements.dat");
            in3 = new ObjectInputStream(fg);
            while ((obj2 = in3.readObject()) != null) {
                if ((obj2 instanceof Announcement)) {
                    System.out.println("mpike sti if");
                    ann1.add((Announcement) obj2);
                    System.out.println("onj added to list: " + obj2);
                } else {
                    break;
                }
            }
            in3.close();
            fg.close();
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("error");
        } catch (IOException | ClassNotFoundException ex) {
          //  Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("error2");
        }

        System.out.println("list before rm" + ann1.toString());
        Announcement obj22 = new Announcement(t, index1, u, date);
        for (int i = 0; i < ann1.size(); i++) {

            System.out.println("mpika ston itr");
            if ((ann1.get(i)).title.equals(obj22.title)) {
               // System.out.println("mpika mesa stin if");
                counter = 1;
               // System.out.println("obj to rm : " + ann1.get(i));
                ann1.remove(i);
                break;
            }
        }

      //  System.out.println("list after remove" + ann1.toString());
        ann1.add(obj22);
      //  System.out.println("list after add" + ann1.toString());

        if (counter == 1) {

            try {
                ObjectOutputStream o1;
                FileOutputStream f1;
                f1 = new FileOutputStream("announcements.dat");
                o1 = new ObjectOutputStream(f1);
                o1.writeObject(ann1);
            } catch (FileNotFoundException ex) {
               // Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
               // Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        } else {
            return 0;
        }

    }

    public int deleteAnnouncement(String title, String ind, String us) {
        int counter = 0;

        /*
            Η διαγραφη γίνεται ως εξης:
            Αρχικά περνάω όλα τα στοιχεία του αρχείου(onjects τυπου Announcement) σε μία λιστα τυπου Announcement.
            Μετα δημιουργώ ενα αντικειμενο της κλασσης Announcemnet με τα στοιχεια που εδωσε ο χρηστης (τον τιτλο ,το περιεχόμενο, το username του χρηστη και τηνημερομηνια).
            Στη συνέχεια αναζητώ στη λιστα αν υπαρχει αντικειμενο με τιτλο ιδιο με αυτον που εδωσε ο χρηστης. Αν υπαρχει το διαγράφω και θέτω την τιμη του counter ιση με 1
            Στην ουσια δηλαδη επικυρωνω οτι βρεθηκε η ανακοινωση και εγινε η διαγραφη προκειμενου να εκτελεστει ο επομενος κωδικας
            Τελος γράφω καθε στοιχειο της λιστας ξανα στο αρχειο
         */
        
        ArrayList<Announcement> ann2 = new ArrayList<>();
        Object obj3 = null;
        ObjectInputStream in4;
        FileInputStream fg2;
        try {
            fg2 = new FileInputStream("announcements.dat");
            in4 = new ObjectInputStream(fg2);
            while((obj3=in4.readObject())!=null)
            {
                if ((obj3 instanceof Announcement)) {
                    System.out.println("mpike sti if");
                    ann2.add((Announcement) obj3);
                    System.out.println("onj added to list: " + obj3);
                } else {
                    break;
                }
            }
            in4.close();
            fg2.close();
           // ann2.add((Announcement) in4.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println("list before rm" + ann2.toString());

        Announcement obj22 = new Announcement(title, ind, us, date);
        for (int k = 0; k < ann2.size(); k++) {

            //System.out.println("mpika ston itr");
            if ((ann2.get(k)).title.equals(obj22.title)) {
                //System.out.println("mpika mesa stin if");
                //System.out.println("obj to rm : " + ann2.get(k));
                ann2.remove(k);
                counter = 1;
                break;
            }

        }

        //System.out.println("list after remove" + ann1.toString());

        if (counter == 1) {

            try {

                ObjectOutputStream o1;
                FileOutputStream f1;
                f1 = new FileOutputStream("announcements.dat");
                o1 = new ObjectOutputStream(f1);
                o1.writeObject(ann2);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
               // Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        } else {
            return 0;
        }

    }

    public int showAnn(Date start, Date finish) {
        /*
        Η εμφανιση γίνεται ως εξης:
        Αρχικά περνάω όλα τα στοιχεία του αρχείου(onjects τυπου Announcement) σε μία λιστα τυπου Announcement. 
        Στην συνέχεια ελέγχω για όλα τα στοιχεία της λίστας αν η ημερομηνία τους είναι μέσα στο διάστημα ημερομηνιων που έδωσε ο χρήστης
        Αν είναι τοτε αυξάνω εναν μετρητη (count) ωστε να ξερει τοσο ο client οσο και ο server ποσες ανακοινωσεις βρεθηκαν και προσθετω την 
        αντιστοιχη ανακοινωση στη λιστα ann4
        Ο server για καθε τιμη του count ανατρεχει στην λιστα που τους πρόσθεσα προηγουμενος (ann4) και καθε φορα στέλενι την αντιστοιχη 
        ανακοινωση στον clinet να την τυπώσει , περιμενει απάντηση ok και συνεχιζει στην επομενη ανακοινωση
         */
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Announcement> ann3 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        int count = 0;
        
        Object obj2 = null;
        System.out.println("start: " + sdf.format(start));
        System.out.println("finish:" + sdf.format(finish));
        ObjectInputStream in5;
        try {
            FileInputStream fg3 = new FileInputStream("announcements.dat");
            in5 = new ObjectInputStream(fg3);
            while ((obj2 = in5.readObject()) != null) {
                if ((obj2 instanceof Announcement)) {
                    System.out.println("mpike sti if");
                    ann3.add((Announcement) obj2);
                }
            }
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println("list size: " + ann3.size());

        for (int i = 0; i < ann3.size(); i++) {
            Date date1 = ann3.get(i).date;
            System.out.println("date1: " + sdf.format(date1));
            if (date1.compareTo(start) > 0 && date1.compareTo(finish) < 0) {
                dates.add(date1);
                ann4.add(ann3.get(i));
                count++;
            }

        }
        return count;
    }

    @Override
    public String toString() {
        return "title : " + title + "  index: " + index + " date: " + date + "user: " + user_of_the_ann;
    }
}
