package org.example.utility;

import com.opencsv.CSVWriter;
import org.example.model.Quote;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveQuotesToCSV {

    public static void saveToCSV(List<Quote> quotes, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"Text", "Author", "Tags"});

            for (Quote quote : quotes) {
                String tags = String.join(", ", quote.getTags());
                writer.writeNext(new String[]{quote.getText(), quote.getAuthor(), tags});
            }

            System.out.println("Data saved to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
