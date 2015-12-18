package com.hy.tools.uitl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <pre>
 * Jar包操作辅助类
 * </pre>
 * 
 * @author 黄云
 * 2015年11月17日
 */
public class MyClassLoader extends URLClassLoader {

	public MyClassLoader(URL[] urls) {
		super(urls);
	}

	/** 
     * 获取jar中某个class 
     *  
     * @param jarPath 
     * @param classPath 
     * @return 
     */  
    @SuppressWarnings("resource")
	public static Object getClassObject(String jarPath, String classPath){
        try {
        	URL[] urls = new URL[]{};
        	MyClassLoader ju = new MyClassLoader(urls);
        	ju.addURL(new File(jarPath).toURI().toURL());
        	Package pk = ju.getPackage("com.linzi.app.appserver.response");
        	System.out.println(pk);
            Class<?> c = ju.loadClass(classPath);
            return c.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
