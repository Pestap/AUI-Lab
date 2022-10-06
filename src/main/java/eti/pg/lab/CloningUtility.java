package eti.pg.lab;

import java.io.*;


/**
 * cloning objects using streams
 */
public class CloningUtility {

    public static <T extends Serializable> T clone(T object){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T)ois.readObject();

        }catch(IOException | ClassNotFoundException ex){
            throw new IllegalStateException(ex);
        }
    }
}
