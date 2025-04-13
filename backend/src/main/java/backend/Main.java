package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 👈 this now scans everything inside "backend"
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
