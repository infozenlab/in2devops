/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service.loggable;

import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.service.DefaultLoggerServiceImpl;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Spring AOP Aspect class to log events around method invocation.
 * 
 */
@Component
@Aspect
public class LoggingAspect {
	
	/** The before string. */
	private static String BEFORE_STRING = "[ entering < {0} > ]";

	/** The before with params string. */
	private static String BEFORE_WITH_PARAMS_STRING = "[ entering < {0} > with params {1} ]";

	/** The after throwing. */
	private static String AFTER_THROWING = "[ exception thrown < {0} > exception message {1} with params {2} ]";

	/** The after returning. */
	private static String AFTER_RETURNING = "[ leaving < {0} > returning {1} ]";

	/** The after returning void. */
	private static String AFTER_RETURNING_VOID = "[ leaving < {0} > ]";

	/** The logger. */
	@Inject
	private DefaultLoggerServiceImpl logger;

	/**
	 * ********************.
	 *
	 * @param joinPoint the join point
	 * @param annotation the annotation
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around(value = "@annotation(annotation)")
	public Object logAround(final ProceedingJoinPoint joinPoint, final Loggable annotation) throws Throwable {

		final Object returnVal;

		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String name = joinPoint.getSignature().getName();

		Object[] args = joinPoint.getArgs();

		if (args == null || args.length == 0) {
			logger.log(annotation.value(), clazz, null, BEFORE_STRING, name,
					constructArgumentsString(clazz, joinPoint.getArgs()));
		} else {
			logger.log(annotation.value(), clazz, null, BEFORE_WITH_PARAMS_STRING, name,
					constructArgumentsString(clazz, joinPoint.getArgs()));
		}
		returnVal = joinPoint.proceed();
		// Now Do The After Logging Part
		afterReturningLog(joinPoint, annotation, returnVal);

		return returnVal;
	}

	/**
	 * After throwing.
	 *
	 * @param joinPoint the join point
	 * @param throwable the throwable
	 */
	@AfterThrowing(value = "@annotation(Loggable)", throwing = "throwable", argNames = "joinPoint, throwable")
	public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {

		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String name = joinPoint.getSignature().getName();
		logger.log(LogLevel.ERROR, clazz, throwable, AFTER_THROWING, name, throwable.getMessage(),
				constructArgumentsString(joinPoint.getArgs()));
	}

	/**
	 * ************.
	 *
	 * @param joinPoint the join point
	 * @param loggable the loggable
	 * @param returnValue the return value
	 */
	private void afterReturningLog(final JoinPoint joinPoint, final Loggable loggable,
			final Object returnValue) {

		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String name = joinPoint.getSignature().getName();

		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Class<?> returnType = signature.getReturnType();
			if (returnType.getName().compareTo("void") == 0) {
				logger.log(loggable.value(), clazz, null, AFTER_RETURNING_VOID, name,
						constructArgumentsString(returnValue));
				return;
			}
		}
		logger.log(loggable.value(), clazz, null, AFTER_RETURNING, name,
				constructArgumentsString(returnValue));
	}

	/**
	 * ********.
	 *
	 * @param clazz the clazz
	 * @param arguments the arguments
	 * @return the string
	 */
	private String constructArgumentsString(Object... arguments) {

		StringBuilder buffer = new StringBuilder();
		if (arguments != null) {
    		for (Object object : arguments) {
    			buffer.append(object);
    			buffer.append(",");
    		}
		}

		if (buffer.length() > 0) {
			return buffer.substring(0, buffer.length() - 1);
		} else {
			return buffer.toString();
		}
	}
}
