package protocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import controller.Controller;

public class ProtocolMethodExcutor {
	public static ProtocolReturn excute(Controller mcontroller, ProtocolMethod method){
		try {
			Class<?> controller = Class.forName("controller.Controller");
			//Class[] parameterTypes = new Class[0];
			Method m = controller.getMethod(method.method_name, (Class<?>[])null);
			// set method
			mcontroller.setMethod(method);
			Controller obj = mcontroller;
			Object ret = m.invoke(obj, (Object[])null);
			return (ProtocolReturn) ret;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
