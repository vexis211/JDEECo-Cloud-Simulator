package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.Statistics;

@Aspect
public class StatisticsAspect {

	@Pointcut("within(@cz.cuni.mff.d3s.deeco.annotations.Component *)")
	private void classAnnotatedWithComponent() {
	}

	// cannot be static - proxies could not be used
	@Pointcut("execution(@cz.cuni.mff.d3s.deeco.annotations.Process public * *(..))")
	private void methodAnnotatedWithProcess() {
	}

	// execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)
	// throws-pattern?)

	@Before("classAnnotatedWithComponent() && methodAnnotatedWithProcess()")
	private void writeStatistic(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Statistics.Write(methodName);

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			String argName = String.format("%sArg%s", methodName, i);
			
			if (arg instanceof Boolean) {
				Statistics.Write(argName, (Boolean) arg);
			} else if (arg instanceof Byte) {
				Statistics.Write(argName, (Byte) arg);
			} else if (arg instanceof Short) {
				Statistics.Write(argName, (Short) arg);
			} else if (arg instanceof Integer) {
				Statistics.Write(argName, (Integer) arg);
			} else if (arg instanceof Float) {
				Statistics.Write(argName, (Float) arg);
			} else if (arg instanceof Double) {
				Statistics.Write(argName, (Double) arg);
			}
		}
	}
}
