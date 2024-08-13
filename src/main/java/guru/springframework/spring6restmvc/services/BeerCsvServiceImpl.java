package guru.springframework.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;

/**
 * Modified by Pierrot, on 2024-08-13.
 */
@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(Reader csvReader) {

        return new CsvToBeanBuilder<BeerCSVRecord>(csvReader)
                .withType(BeerCSVRecord.class)
                .build().parse();
    }
}
