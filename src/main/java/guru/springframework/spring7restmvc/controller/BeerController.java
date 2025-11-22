package guru.springframework.spring7restmvc.controller;

import guru.springframework.spring7restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Created by jt, Spring Framework Guru.
 */
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

}
