package com.example.demo;

import java.io.InputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        try {
            Class clazz = new HelloClassLoader().findClass("Hello");
            Method method = clazz.getDeclaredMethod("hello");
            Object obj = clazz.newInstance();
            method.invoke(obj);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try {
            bytes = readClassFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] readClassFile() throws Exception {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("Hello.xlass");
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        byte[] buf2 = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            buf2[i] = (byte) (255 - buf[i]);
        }
        fis.close();
        return buf2;
    }

}
