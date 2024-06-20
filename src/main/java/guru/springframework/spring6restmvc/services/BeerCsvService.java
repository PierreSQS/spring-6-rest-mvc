package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Modified by Pierrot on 20-06-2024.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(Reader reader);
}
