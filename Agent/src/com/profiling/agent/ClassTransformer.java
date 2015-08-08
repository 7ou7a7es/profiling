package com.profiling.agent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

import com.profiling.model.StackTraceNode;

public class ClassTransformer implements ClassFileTransformer {

	private static CtClass stackClazz;
	private static CtClass stackNodeClazz;
	private static CtClass[] exceptionArrayClazz;

	static {
		try {
//			stackClazz = ClassPool.getDefault().getCtClass(StackTraceElement[].class.getName());
//			stackNodeClazz = ClassPool.getDefault().getCtClass(StackTraceNode.class.getName());
//			CtClass noClazzDef = ClassPool.getDefault().getCtClass(NoClassDefFoundError.class.getName());
			stackClazz = ClassPool.getDefault().getCtClass(StackTraceElement[].class.getName());
			stackNodeClazz = ClassPool.getDefault().getCtClass(StackTraceNode.class.getName());
			CtClass noClazzDef = ClassPool.getDefault().getCtClass(NoClassDefFoundError.class.getName());
			exceptionArrayClazz = new CtClass[1];
			exceptionArrayClazz[0] = noClazzDef;
		} catch (NotFoundException e) {
			stackClazz = null;
			stackNodeClazz = null;
			exceptionArrayClazz = null;
		}
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) {

		// if (loader != null) {
		System.out.println("**********************");
		System.out.println("*loader : " + loader);
		System.out.println("*className " + className);
		System.out.println("*classBeingRedefined " + classBeingRedefined);
		System.out.println("*protectionDomain " + protectionDomain);
		System.out.println("*classfileBuffer " + classfileBuffer);
		System.out.println("**********************");

		try {

			ByteArrayInputStream byteArray = new java.io.ByteArrayInputStream(classfileBuffer);
			ClassPool pool = ClassPool.getDefault();
			CtClass clazz = pool.makeClass(byteArray);
//			pool.insertClassPath("com/model/StackTraceNode");
			//
			
//			try {
//				Thread.currentThread().getContextClassLoader().loadClass("com.profiling.model.StackTraceNode");
//			} catch (ClassNotFoundException e) {
//				System.err.println("haaaaaaaaaaaaaaaaaaaa "+e);
//			}

			System.err.println("clazz : " + clazz);
			System.err.println("clazz package name " + clazz.getPackageName());
			if (clazz != null && !clazz.isInterface() && !clazz.isFrozen() && (clazz.getPackageName()==null || !(clazz.getPackageName().startsWith("com.profiling") || clazz.getPackageName().startsWith("javassist")))){

				System.err.println("clazz name : " + clazz.getName());
				
				CtField nodeField = new CtField( stackNodeClazz, "ROOT_NODE",clazz);
				nodeField.setModifiers( Modifier.STATIC );
				clazz.addField(nodeField);

				for (CtBehavior method : clazz.getDeclaredBehaviors()) {
					System.err.println("methode name : " + method.getLongName());
					if (isTransformable(method) && stackClazz != null) {
						System.err.println("~~ transform methode : " + method.getLongName());
						method.setExceptionTypes(exceptionArrayClazz);
						method.addLocalVariable("time", CtClass.longType);
						method.addLocalVariable("stack", stackClazz);
						method.addLocalVariable("subStack", stackClazz);
						
						
						method.insertBefore("System.err.println(\"Test\");");
						method.insertBefore("time = System.currentTimeMillis();");
						
						method.insertAfter("stack =Thread.currentThread().getStackTrace();");

						
						method.insertAfter("System.err.println(\"Class " + clazz.getName() + " method getLongName "
								+ method.getLongName() + " time : \"+Long.toString(System.currentTimeMillis()-time));");
						
						//FIXME
						method.insertAfter("try{ROOT_NODE.ROOT.addNodes(stack, System.currentTimeMillis()-time);}catch(java.lang.NoClassDefFoundError e){System.err.println(\" Exception in "+method.getLongName()+" : \"+e);}");

					}
					System.err.println("€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€");
				}

				System.err.println("cla22 : " + clazz);
				System.err.println("cla22 byte code : " + clazz.toBytecode());
				return clazz.toBytecode();
			}
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (CannotCompileException ex) {
			System.err.println(ex);
		} catch (NotFoundException ex) {
			System.err.println(ex);
		}
//		catch (NotFoundException ex) {
//			System.err.println(ex);
//		}
		// }
		return null;
	}

	private boolean isTransformable(CtBehavior method) {

		// Check if method is abstract.
		if (Modifier.isAbstract(method.getModifiers())) {
			System.err.println("~~ abstract methode : " + method.getLongName());
			return false;
		}

		// Check if method is native.
		if (Modifier.isNative(method.getModifiers())) {
			System.err.println("~~ native methode : " + method.getLongName());
			return false;
		}

		// Otherwise method is transformable.
		return true;
	}
}
