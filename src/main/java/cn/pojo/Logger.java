package cn.pojo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component("logger")
@Aspect
public class Logger {
    //@Before("execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))")
    public void printLog() {System.out.println("Logger类中的printLog方法开始记录日志了。。。 前置通知");}
    //@AfterReturning("execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))")
    public void printLog2() {System.out.println("Logger类中的printLog2方法开始记录日志了。。。 后置通知");}
    //@AfterThrowing("execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))")
    public void printLog3() {System.out.println("Logger类中的printLog3方法开始记录日志了。。。 异常通知");}
    //@After("execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))")
    public void printLog4() {System.out.println("Logger类中的printLog3方法开始记录日志了。。。 最终通知");}
    @Around("execution(public void cn.service.impl.IAccountServiceimpl.saveAccount(..))")
    public Object around(ProceedingJoinPoint pjp){
        Object obj = null;
        try {
            //System.out.println("前置通知 环");
            printLog();
            Object[] args = pjp.getArgs();
            System.out.println(args[0]);
            System.out.println(pjp.getSignature().getName());
            obj = pjp.proceed();
            //System.out.println("后置通知 环");
            printLog2();

        } catch (Throwable e) {
            //System.out.println("异常通知 环");
            printLog3();
            e.printStackTrace();
        }finally{
            //System.out.println("最终通知 环");
            printLog4();
        }
        return obj;
    }





}
