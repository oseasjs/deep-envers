package br.com.deep.envers.intercept;

import br.com.deep.envers.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class AspectInterceptor {

    @Autowired
    private PersonRepository repository;

    //AOP expression for which methods shall be intercepted
    @Around("execution(* com.example.pocauditoriadados.repository.*.*(..)))")
    public Object interceptAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object argument = joinPoint.getArgs()[0];

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object after = joinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        log.info("Execution time of "
                + className + "."
                + methodName
                + " :: "
                + stopWatch.getTotalTimeMillis() + " ms");

        return after;
    }

}
