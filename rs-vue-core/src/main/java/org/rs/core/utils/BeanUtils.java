package org.rs.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class BeanUtils extends org.springframework.beans.BeanUtils{

	static public void objectToMap( Object source, Map<String,Object> target ){
		
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		Class<?> actualEditable = source.getClass();
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor sourcePd : sourcePds) {
			
			Method writeMethod = sourcePd.getWriteMethod();
			if(writeMethod == null)
				continue;
			Method readMethod = sourcePd.getReadMethod();
			String name = sourcePd.getName();
			try {
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(source);
				target.put(name, value);
			}
			catch (Throwable ex) {
				throw new FatalBeanException(
						"Could not copy property '" + sourcePd.getName() + "' from source to map", ex);
			}
		}
	}
	
	static public void mapToObject( Map<String,Object> source, Object target ){
		
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null ) {
				
				String name = targetPd.getName();
				if( source.containsKey(name) ) {
					Object value = source.get(name);
					try {
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
					}
				}
			}
		}
		
	}
}
