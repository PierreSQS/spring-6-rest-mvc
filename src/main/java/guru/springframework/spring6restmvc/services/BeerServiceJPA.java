package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.controller.NotFoundException;
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
 * Created by Pierrot, 18.02.2023.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beerDTO) {
        // find the Beer to Update
        Optional<Beer> foundBeerOpt = beerRepository.findById(beerId);

        // if present then update
        if (foundBeerOpt.isPresent()) {
            Beer beerToUpdate = foundBeerOpt.get();
            Beer newBeer = beerMapper.beerDtoToBeer(beerDTO);
            beerToUpdate.setBeerName(newBeer.getBeerName());
            beerToUpdate.setBeerStyle(newBeer.getBeerStyle());
            beerToUpdate.setPrice(newBeer.getPrice());
            beerToUpdate.setQuantityOnHand(newBeer.getQuantityOnHand());
            beerRepository.save(beerToUpdate);
        } else {
            throw new NotFoundException("Beer not found!!");
        }

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
