package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Created by Pierrot, 22.05.2024.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(Reader reader);
}
