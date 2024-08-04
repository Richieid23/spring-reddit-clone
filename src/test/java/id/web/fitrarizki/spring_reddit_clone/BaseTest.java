package id.web.fitrarizki.spring_reddit_clone;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class BaseTest {
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",() -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username",() -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password",() -> mySQLContainer.getPassword());
    }

    static {
        mySQLContainer.start();
    }
}
