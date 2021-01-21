package cn.qzjblog;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("cn.qzjblog.mapper")
@SpringBootApplication()
public class YnToolApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YnToolApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(YnToolApplication.class);
    }
}