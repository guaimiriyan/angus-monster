package com.InnerClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class T012_ClassReloading2 {
    private static class MyLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {

            File f = new File("C:\\Users\\Angus_Lee\\IdeaProjects\\angus-monster\\base\\target\\classes\\com\\InnerClass\\InnerTest.class");
            if(!f.exists()||name.contains("java")) return super.loadClass(name);

            try {

                InputStream is = new FileInputStream(f);

                byte[] b = new byte[is.available()];
                is.read(b);
                return defineClass(name, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.loadClass(name);
        }
    }

    public static void main(String[] args) throws Exception {
        MyLoader m = new MyLoader();
        Class clazz = m.loadClass("com.InnerClass.InnerTest");

        MyLoader m1 = new MyLoader();
        Class clazzNew = m1.loadClass("com.InnerClass.InnerTest");


        System.out.println(clazz == clazzNew);
    }
}
