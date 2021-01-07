package cn.qzjblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Create by qzj on 2020/12/10 22:30
 **/
//起到进行切面操作
@Aspect
//可以被扫描到
@Component
public class LogAspect {
    //拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义一个切面,execution内容定义拦截类.
    //web包及其子包下所有的类中的所有方法全部拦截
    @Pointcut("execution(* cn.qzjblog.web..*.*(..))")
    public void log() {
    }
    //定义一个内部类，来装载请求参数【url、ip、method、args】
    private class RequestLog {

        private String url;
        private String ip;
        private String method;
        private Object[] args;

        public RequestLog() {
        }

        public RequestLog(String url, String ip, String method, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.method = method;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", method='" + method + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

    //获取请求参数，并打印日志
    @Before(value = "log()")
    public void doBefore(JoinPoint jp) {
        //先获取到request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //通过httpServletRequest来获取url
        String url = request.getRequestURL().toString();
        //获取id
        String addr = request.getRemoteAddr();
        //获取类.方法
        String method = jp.getSignature().getDeclaringTypeName()
                + "." +jp.getSignature().getName();
        //获取参数
        Object[] args = jp.getArgs();
        RequestLog rl = new RequestLog(url, addr, method,args);
        logger.info("Request : {}",rl);
    }
    //获取返回值并打印日志
    @AfterReturning(pointcut = "log()",returning = "res")
    public void doAfterReturning(Object res){
        logger.info("Return : {}" , res);
    }



}
