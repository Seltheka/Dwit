package org.dwit.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultClassLoader extends ClassLoader {
	
	private static final int BUFFER_SIZE = 8192;
	
	private int test = 0;
	
	private String className;
		
	protected synchronized Class loadClass(String className, File classFile, boolean resolve) throws ClassNotFoundException {
		
		System.out.println(className);
		
		this.className = className;
		
		//Class cls = findLoadedClass(null);
		
		Class cls = findLoadedClass(className);
		
		if (cls!=null)
			return cls;
				
		//System.out.println(test++);
		
		//System.out.println(classFile);
		
		/*classFile = new String("plugins/hosts/SamplePlugin.class");
		
		System.out.println(classFile);*/
		
		byte[] classBytes = null;
		
		try{
			InputStream in = new FileInputStream(classFile);
			byte[] buffer = new byte[BUFFER_SIZE];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int n = -1;
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1){
				out.write(buffer, 0, n);
			}
			classBytes = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (classBytes == null) {
			
			throw new ClassNotFoundException("Cannot load class: "+className);
			
		}
		
		try{
			return defineClass(className, classBytes, 0, classBytes.length);
			//System.out.println(cls.getName() + " " + className);
			/*if (resolve)
				resolveClass(cls);*/
		} catch (SecurityException e) {
			System.err.println("Error");
			//cls = super.loadClass(className, resolve);
		}
		
		// http://www.xinotes.org/notes/note/444/
		
		return cls;
		
	}

}
