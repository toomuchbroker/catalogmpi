import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import dao.UserDao;
import model.User;
import java.util.List;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication(scanBasePackages = {"ro.catalogmpi", "dao"})
@EntityScan("model") 
public class Main {
    public static void main(String[] args) {
        // Store the application context returned by SpringApplication.run()
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        
        // Get the UserDao bean from the context
        UserDao userDao = ctx.getBean(UserDao.class);
        
        // Call a method, for example, print all users:
        List<User> users = userDao.getAll();
        users.forEach(user -> System.out.println(user.getName()));
    }
}
