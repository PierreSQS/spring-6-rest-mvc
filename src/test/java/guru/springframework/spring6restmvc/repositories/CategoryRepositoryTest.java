package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    CategoryRepository categoryRepo;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepo.findAll().getFirst();
    }

    @Transactional
    @Test
    void testAddCategory() {
        Category beerCategory = Category.builder()
                .description("Pilsener")
                .build();

        Category savedBeerCat = categoryRepo.save(beerCategory);

        testBeer.addCategory(savedBeerCat);

        System.out.println("the Beer Name: "+ testBeer.getBeerName());
    }
}