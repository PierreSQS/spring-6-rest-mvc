package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepo;

    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepo.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID uuid) {
        Optional<Beer> beerOptional = beerRepo.findById(uuid);
        return beerOptional.map(beerMapper::beerToBeerDto);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
