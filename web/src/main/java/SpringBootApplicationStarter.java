import com.epam.esm.config.BeanConfig;
import com.epam.esm.config.JwtConfig;
import com.epam.esm.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import({BeanConfig.class, WebSecurityConfig.class, JwtConfig.class})
@EnableJpaRepositories("com.epam.esm")
@EntityScan("com.epam.esm")
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class SpringBootApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
    }
}
