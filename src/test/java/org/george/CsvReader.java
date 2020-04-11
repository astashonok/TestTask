package org.george;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.george.entities.SuperHero;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    @SneakyThrows
    public static ArrayList<SuperHero> getData(String csvFilename) {
        ArrayList<SuperHero> list = new ArrayList<>();
        File file = new File(CsvReader.class.getClassLoader().getResource(csvFilename).getFile());
        try (
                Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
        ) {
            CsvToBean<SuperHero> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(SuperHero.class)
                    .withIgnoreQuotations(false)
                    .withIgnoreLeadingWhiteSpace(false)
                    .build();

            List<SuperHero> listUsers = csvToBean.parse();
            list.addAll(listUsers);

        }
        return list;
    }
}
