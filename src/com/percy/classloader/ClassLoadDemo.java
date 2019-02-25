package com.percy.classloader;

/**
 * @author percy
 * @create 2019-02-25  下午9:34
 * @descreption:
 **/
public class ClassLoadDemo {
    static {
        System.out.println("SuperClass init!");
    }
    public  static  int value=123;
    public ClassLoadDemo() {
        System.out.println("classloaderdemo is load");

    }

    public static void main(String[] args) {
        ClassLoadDemo classLoadDemo = new ClassLoadDemo();
        ClassLoader cl= classLoadDemo.getClass().getClassLoader();
        while (cl!=null){
            System.out.println(cl);
            cl = cl.getParent();
        }
        System.out.println(cl);
        /**
         * BootStrap ClassLoader 加载的文件
         */
        System.out.println(System.getProperty("sun.boot.class.path"));
        /**
         * EtxClassLoader 加载的文件
         */
        System.out.println(System.getProperty("java.ext.dirs"));
        /**
         * String的加载器是BootStrap的ClassLoader  所以输出是null
         * 同理int也是一样
         */
        System.out.println(String.class.getClassLoader());
        System.out.println(int.class.getClassLoader());

    }
}
