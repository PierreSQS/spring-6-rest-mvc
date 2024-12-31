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

/**
 * Created by Pierrot, 2024-12-30.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return beerRepository.findById(id).map(beerMapper::beerToBeerDto);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beerDTO) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);

        beerOptional.ifPresent(beer -> {
            beerDTO.setId(beerId);
            beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));
        });

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beerDTO) {

    }
}
