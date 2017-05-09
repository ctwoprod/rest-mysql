package org.rf.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

/**
 * Util class for for copying values of object to another object
 * 
 * @author Roberto
 */
public class ExtendedSpringBeanUtil extends BeanUtils {
	/**
	 * copy properties from other object with same attributes name
	 *
	 * @param source
	 * @param target
	 * @param copiedProperties
	 * @throws BeansException
	 */
	public static void copySpecificProperties(Object source, Object target,
			String[] copiedProperties) throws BeansException {

		Class<?> actualEditable = target.getClass();

		if (copiedProperties == null) {
			copyProperties(source, target);
			return;
		}

		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> copiedList = (copiedProperties != null) ? Arrays
				.asList(copiedProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null
					&& copiedList.contains(targetPd.getName())
					&& copiedProperties != null

			) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass()
								.getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}

	/**
	 * copy properties from object to other object with same or different
	 * attributes name count attributes must be same
	 *
	 * @param source
	 * @param target
	 * @param sourceCopiedProperties
	 * @param targetCopiedProperties
	 * @throws BeansException
	 */
	@SuppressWarnings("rawtypes")
	public static void copySpecificProperties(Object source, Object target,
			String[] sourceCopiedProperties, String[] targetCopiedProperties)
			throws BeansException {

		if (sourceCopiedProperties == null) {
			copyProperties(source, target);
			return;
		}

		if (sourceCopiedProperties.length != targetCopiedProperties.length) {
			throw new FatalBeanException(
					"Count source copy properties must be equal with target copy properties");
		}

		List<String> sourceCopiedList = (sourceCopiedProperties != null) ? Arrays
				.asList(sourceCopiedProperties) : null;
		List<String> targetCopiedList = (targetCopiedProperties != null) ? Arrays
				.asList(targetCopiedProperties) : null;

		int n = 0;

		for (String sourceCopied : sourceCopiedList) {
			PropertyDescriptor sourcePd = getPropertyDescriptor(
					source.getClass(), sourceCopied);
			PropertyDescriptor targetPd = getPropertyDescriptor(
					target.getClass(), targetCopiedList.get(n));
			if ((sourcePd != null && targetPd != null)) {

				if (sourcePd != null && sourcePd.getReadMethod() != null
						&& targetPd != null && targetPd.getReadMethod() != null) {
					if (sourcePd.getPropertyType().equals(
							targetPd.getPropertyType())) {
						try {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							copyValue(target, targetPd, value);

						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy properties from source to target",
									ex);
						}
					} else {
						try {

							Object value = null;

							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}

							Object object = readMethod.invoke(source);

							if (object instanceof BaseModelEnum) {
								value = ((BaseModelEnum) object)
										.getInternalValue();
							}
							copyValue(target, targetPd, value);

						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy properties from source to target",
									ex);
						}
					}
				}
			}

			n++;

		}
	}

	private static void copyValue(Object target, PropertyDescriptor targetPd,
			Object value) throws IllegalAccessException,
			InvocationTargetException {
		// update target value only if source value is not null
		if (null != value) {

			Method writeMethod = targetPd.getWriteMethod();

			if (!Modifier.isPublic(writeMethod.getDeclaringClass()
					.getModifiers())) {
				writeMethod.setAccessible(true);
			}
			writeMethod.invoke(target, value);
		}
	}

}
