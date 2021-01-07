package cn.qzjblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by qzj on 2020/12/10 11:14
 * 拦截器
 **/
@ControllerAdvice
public class ControllerExceptionHandler {

    //先获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //所有的Exception都拦截
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        //在控制台输出异常信息
        logger.error("Request URL : {} , Exception : {}", request.getRequestURL(),e);

        //如果
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        //再返回到页面上
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
