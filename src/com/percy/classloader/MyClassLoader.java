package com.percy.classloader;

import java.io.*;

/**
 * @author percy
 * @create 2019-02-25  下午9:35
 * @descreption:
 **/
public class MyClassLoader extends ClassLoader {
    private String rootDir;

    public MyClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }
    @Override
    protected Class<?> findClass(String s) throws ClassNotFoundException {
        byte[] classData = getClassData(s);
        Class clazz = defineClass(s,classData,0,classData.length);
        return clazz;
    }
    private byte[] getClassData(String className) {
        // 读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            // 读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 类文件的完全路径
     * @param className
     * @return
     */
    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }

    public static void main(String[] args) {
        String Root = "/home/percy/IdeaProjects/ClassLoader&SychronizedDemo/out/production/ClassLoader&SychronizedDemo/";
        /**
         * 通过自定义加载器加载类
         */
        MyClassLoader myClassLoader= new MyClassLoader(Root);
        MyClassLoader myClassLoader_= new MyClassLoader(Root);
        try {
            Class<?> clazz=myClassLoader.findClass("com.percy.classloader.TestClass");
            clazz.newInstance();
            System.out.println(clazz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        /**
         * 通过loadClass加载
         */
        try {
            Class<?> clazz1=myClassLoader.loadClass("com.percy.classloader.TestClass");
            Class<?> clazz2=myClassLoader_.loadClass("com.percy.classloader.TestClass");
            System.out.println(clazz1.hashCode() + "=" + clazz2.hashCode());
//            Class<?> clazz3=myClassLoader.findClass("com.percy.classloader.TestClass");
//            Class<?> clazz4=myClassLoader_.findClass("com.percy.classloader.TestClass");
//            System.out.println(clazz3.hashCode() + "=" + clazz4.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
