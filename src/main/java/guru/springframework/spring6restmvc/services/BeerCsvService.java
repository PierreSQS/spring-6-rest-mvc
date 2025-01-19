package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Modified by Pierrot, 19.01.2025.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(Reader reader);
}
