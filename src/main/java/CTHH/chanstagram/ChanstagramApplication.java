package CTHH.chanstagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ChanstagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChanstagramApplication.class, args);
    }

}
