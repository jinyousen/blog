import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-05
 * @Description
 */


@SpringBootApplication
@ComponentScan("org.yasser.controller")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
