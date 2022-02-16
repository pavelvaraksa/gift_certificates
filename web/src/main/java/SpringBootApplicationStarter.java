import com.epam.esm.config.BeanConfig;
import com.epam.esm.config.PersistentContextConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({BeanConfig.class, PersistentContextConfig.class})
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class SpringBootApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
    }
}
