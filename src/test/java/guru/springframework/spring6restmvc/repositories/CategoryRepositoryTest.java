package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    CategoryRepository categoryRepo;

    Beer beer;

    @BeforeEach
    void setUp() {
        beer = beerRepo.findAll().getFirst();
    }

    @Test
    void testAddCategory() {
        Category beerCategory = Category.builder()
                .description("Pilsener")
                .build();

        Category savedBeerCat = categoryRepo.save(beerCategory);

        System.out.println("the Beer Name: "+beer.getBeerName());
    }
}