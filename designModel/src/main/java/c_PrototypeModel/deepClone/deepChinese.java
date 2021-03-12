package c_PrototypeModel.deepClone;

import c_PrototypeModel.prototype.Home;
import c_PrototypeModel.prototype.human;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName deepChinese.java
 * @Description TODO
 * @createTime 2021年03月12日 12:30:00
 */
public class deepChinese extends human {
    public deepChinese(String name, int age, Home home) {
        super(name, age, home);
    }

    public deepChinese cloneMethod1(){
        try {
            deepChinese clone = (deepChinese)super.clone();
            clone.setHome(clone.getHome().clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public deepChinese cloneMethod2(){
        return  deepClone();
    }

    private deepChinese deepClone() {


        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos);
             ){
            oos.writeObject(this);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);){
                return (deepChinese)ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}
