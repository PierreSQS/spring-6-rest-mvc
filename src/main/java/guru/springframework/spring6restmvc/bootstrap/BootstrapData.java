package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BootstrapData {

    private final BeerRepository beerRepo;

    private final CustomerRepository customerRepo;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            loadCustomerData();
            loadBeerData();
        };
    }

    private void loadBeerData() {

        if (beerRepo.count() == 0 ) {
            Beer beer1 = Beer.builder()
                    .beerName("33 Export")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("A-0010-Z")
                    .price(BigDecimal.valueOf(7,35))
                    .quantityOnHand(30)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Beaufort")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("A-0020-Z")
                    .price(BigDecimal.valueOf(4,55))
                    .quantityOnHand(30)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Kadji")
                    .beerStyle(BeerStyle.PORTER)
                    .upc("A-0030-Z")
                    .price(BigDecimal.valueOf(3,50))
                    .quantityOnHand(30)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepo.saveAll(List.of(beer1,beer2,beer3));
            log.info("####### loaded {} Beers!!! #######", beerRepo.count());
        } else {
            log.info("####### DB already loaded with {} Beers #######", beerRepo.count());
        }
    }

    private void loadCustomerData() {

        if (customerRepo.count() == 0 ) {

            Customer customer1 = Customer.builder()
                    .name("Customer1")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Customer2")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Customer3")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepo.saveAll(List.of(customer1,customer2,customer3));

            log.info("####### loaded {} Customer!!! #######", customerRepo.count());
        } else {
            log.info("####### DB already loaded with {} Customer #######", customerRepo.count());
        }
    }

}
