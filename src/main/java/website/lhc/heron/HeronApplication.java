package website.lhc.heron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAsync
@MapperScan(basePackages = {"website.lhc.heron.mapper"})
@SpringBootApplication
public class HeronApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeronApplication.class, args);
    }

}
