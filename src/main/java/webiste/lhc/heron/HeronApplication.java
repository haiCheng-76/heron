package webiste.lhc.heron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = {"webiste.lhc.heron.mapper"})
@SpringBootApplication
public class HeronApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeronApplication.class, args);
    }

}
