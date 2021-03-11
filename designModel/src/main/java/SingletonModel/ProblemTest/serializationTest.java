package SingletonModel.ProblemTest;

import SingletonModel.Serializable.serializableSingleton;
import SingletonModel.hungry.hungrySingleton;

import java.io.*;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName serializationTest.java
 * @Description TODO
 * @createTime 2021年03月11日 17:11:00
 */
public class serializationTest {
    public static void main(String[] args) {
        /**
         * 使用序列化的方式进行单例的破坏
         */

        try {
            //序列化过程
            //hungrySingleton instance = hungrySingleton.getInstance();
            serializableSingleton instance = serializableSingleton.getInstance();
            FileOutputStream fileOutputStream = new FileOutputStream("single1.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(instance);


            //反序列化过程
            FileInputStream fileInputStream = new FileInputStream("single1.obj");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            serializableSingleton serializationSingleton = (serializableSingleton)ois.readObject();
            System.out.println(instance == serializationSingleton);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
