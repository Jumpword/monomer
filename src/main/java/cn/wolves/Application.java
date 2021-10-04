package cn.wolves;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Jumping
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        //获取端口号
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("Project started at http://127.0.0.1:" + serverPort);
        log.info("Api Doc started at http://127.0.0.1:" + serverPort+"/doc.html#/home");

    }

}
