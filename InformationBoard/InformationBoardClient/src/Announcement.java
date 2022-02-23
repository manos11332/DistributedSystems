
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Announcement implements Serializable {
   
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

    @Override
    public String toString() {
        return "title : " + title + "  index: " + index +" date: "+ date +" owner of the announcement: "+ user_of_the_ann;
    }

    public int addAnn(Announcement a) throws FileNotFoundException, IOException {
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

    public void fordel(String t) {
        Object obj2 = null;
        ObjectInputStream in3;
        try {
            // Object obj1;

            FileInputStream fg = new FileInputStream("announcements.dat");
            in3 = new ObjectInputStream(fg);
            System.out.println("list size: " + ann1.size());
            System.out.println("file length" + file1.length());
            while (true) {
                obj2 = in3.readObject();
                if (((Announcement) obj2) != null) {
                    System.out.println("mpike sti while");
                    //ann1.add((Announcement) obj2);
                    System.out.println("obj1: " + obj2.toString());
                   // System.out.println("list : " + ann1.toString());
                }
                else
                {
                    break;
                }
            }

        } catch (FileNotFoundException ex) {
            //  Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Announcement.class.getName()).log(Level.SEVERE, null, ex);
            // System.out.println("error2");
        }
    }
}
