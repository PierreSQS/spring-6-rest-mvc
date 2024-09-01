package guru.springframework.spring6restmvc.mappers;


import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.entities.BeerOrderLine;
import guru.springframework.spring6restmvc.entities.BeerOrderShipment;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import guru.springframework.spring6restmvc.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvc.model.BeerOrderShipmentDTO;
import org.mapstruct.Mapper;

/**
 * Created by Pierrot, on 2024-08-31.
 */
@Mapper
public interface BeerOrderMapper {

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO dto);

    BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder beerOrder);

    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO dto);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipmentDTO dto);

    BeerOrderShipmentDTO beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipment beerOrderShipment);


}
