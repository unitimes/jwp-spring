package next.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class PerformanceMornitorAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceMornitorAspect.class);

	@Pointcut("execution(public * next.web..*.*(..))")
	public void serviceMethods() {
	};

	@Around("serviceMethods()")
	public void checkPerformance(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("in...");
		long startTime = System.currentTimeMillis();
		pjp.proceed();
		logger.info(pjp.getSignature().getName() + ": "
				+ (System.currentTimeMillis() - startTime) + "ms");
	}
}
