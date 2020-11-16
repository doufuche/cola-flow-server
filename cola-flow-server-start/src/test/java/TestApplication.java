

import com.github.cola.flow.server.start.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class TestApplication {

    public static void main(String[] args) {
        //这里填的是TestApplication
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
