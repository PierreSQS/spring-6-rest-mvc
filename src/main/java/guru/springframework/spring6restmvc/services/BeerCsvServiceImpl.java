package guru.springframework.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;

/**
 * Modified by Pierrot on 2025-11-05.
 */
@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(Reader reader) {

        return new CsvToBeanBuilder<BeerCSVRecord>(reader)
                .withType(BeerCSVRecord.class)
                .build().parse();
    }
}
