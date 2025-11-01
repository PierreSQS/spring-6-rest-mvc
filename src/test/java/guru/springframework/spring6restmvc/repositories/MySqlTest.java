package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Pierrot on 01-11-2025.
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
class MySqlTest {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9")
            .withConfigurationOverride("mysql-conf");

    // Set MySQL properties in the Docker container
    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    BeerRepository beerRepo;

    @Test
    void testListBeers() {
        List<Beer> beerList = beerRepo.findAll();

        assertThat(beerList).hasSizeGreaterThan(0);
    }
}
