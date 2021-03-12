package b_SingletonModel.ProblemTest;

import b_SingletonModel.Serializable.serializableSingleton;

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

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream oos = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream ois = null;
        try {
            //序列化过程
            //hungrySingleton instance = hungrySingleton.getInstance();
            serializableSingleton instance = serializableSingleton.getInstance();
             fileOutputStream = new FileOutputStream("single1.obj");
             oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(instance);


            //反序列化过程
            fileInputStream = new FileInputStream("single1.obj");
            ois = new ObjectInputStream(fileInputStream);
            serializableSingleton serializationSingleton = (serializableSingleton)ois.readObject();
            System.out.println(instance == serializationSingleton);

        }catch (Exception e){
            e.printStackTrace();
        }finally {

                try {
                    if (fileInputStream!=null){
                    fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (ois != null){
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fileOutputStream != null){
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (oos != null){
                        oos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

}
