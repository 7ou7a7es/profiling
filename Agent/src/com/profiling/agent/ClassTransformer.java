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
			stackClazz = ClassPool.getDefault().getCtClass(StackTraceElement[].class.getName());
			stackNodeClazz = ClassPool.getDefault().getCtClass(StackTraceNode.class.getName());
			CtClass noClazzDef = ClassPool.getDefault().getCtClass(NoClassDefFoundError.class.getName());
			exceptionArrayClazz = new CtClass[1];
			exceptionArrayClazz[0] = noClazzDef;
		} catch (NotFoundException ex) {
			// FIXME change error log.
			System.err.println(ex);
		}
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) {

		try {

			ByteArrayInputStream byteArray = new java.io.ByteArrayInputStream(classfileBuffer);
			ClassPool pool = ClassPool.getDefault();
			CtClass clazz = pool.makeClass(byteArray);
			
			// Skip interface and agent class.
			if (clazz != null && !clazz.isInterface() && !clazz.isFrozen() && (clazz.getPackageName()==null || !(clazz.getPackageName().startsWith("com.profiling") || clazz.getPackageName().startsWith("javassist")))){

				// Init root stack node.
				CtField nodeField = new CtField( stackNodeClazz, "ROOT_NODE",clazz);
				nodeField.setModifiers( Modifier.STATIC );
				clazz.addField(nodeField);

				// Modify all class methods.
				for (CtBehavior method : clazz.getDeclaredBehaviors()) {
					
					// Skip abstract and native methods.
					if (isTransformable(method) && stackClazz != null) {
						
						// Add exception NoClassDefFoundError for nodes treatments.
						method.setExceptionTypes(exceptionArrayClazz);
						
						// Init local variables.
						method.addLocalVariable("time", CtClass.longType);
						method.addLocalVariable("stack", stackClazz);
//						method.addLocalVariable("subStack", stackClazz);
						
						// Init the method execution time.
						method.insertBefore("time = System.currentTimeMillis();");
						
						// Retrieve the stack.
						method.insertAfter("stack =Thread.currentThread().getStackTrace();");
						
						
//						method.insertAfter("System.out.println(\"*********************************************\");");
//						method.insertAfter("System.out.println(\"Method name : +"+method.getName()+"\");");
//						method.insertAfter("System.out.println(\"Method long name : +"+method.getLongName()+"\");");
//						method.insertAfter("System.out.println(\"Method info : +"+method.getMethodInfo().+"\");");
//						method.insertAfter("System.out.println(\"Method info 2 : +"+method.getMethodInfo2()+"\");");
//						method.insertAfter("System.out.println(\"*********************************************\");");
						
						// Add stack to root nodes.
						//FIXME change log.
						method.insertAfter("try{ROOT_NODE.ROOT.addInformations(stack, System.currentTimeMillis()-time);}catch(java.lang.NoClassDefFoundError e){System.err.println(\" Exception in "+method.getLongName()+" : \"+e);}");

					}
				}

				// Return modified code.
				return clazz.toBytecode();
			}
		} catch (IOException ex) {
			// FIXME change error log.
			System.err.println(ex);
		} catch (CannotCompileException ex) {
			// FIXME change error log.
			System.err.println(ex);
		} catch (NotFoundException ex) {
			// FIXME change error log.
			System.err.println(ex);
		}
		return null;
	}

	private boolean isTransformable(CtBehavior method) {

		// Check if method is abstract.
		if (Modifier.isAbstract(method.getModifiers())) {
			return false;
		}

		// Check if method is native.
		if (Modifier.isNative(method.getModifiers())) {
			return false;
		}

		// Otherwise method is transformable.
		return true;
	}
}
