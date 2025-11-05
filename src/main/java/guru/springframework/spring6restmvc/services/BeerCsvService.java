package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Modified by Pierrot on 2025-11-05.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(Reader reader);
}
