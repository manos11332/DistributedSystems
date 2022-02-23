
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {
    /*
    κλαση για να κανε serialize και deserialize τα αντικειμενα
    */
     private String fileName1;

    public File file = new File(fileName1);
    public static Object deserialize(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj=ois.readObject();
        ois.close();
        return obj;
        
    }
    public static void serialize(Object obj,String fileName) throws FileNotFoundException, IOException{
        
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        fos.close();
}
    public static Object deserialize1(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj=(Announcement) ois.readObject();
        ois.close();
        return obj;
        
    }
}
   

