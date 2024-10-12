package org.example.utility;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.Quote;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CsvAnalyzer {

    public static List<Quote> loadQuotesFromCSV(String filePath) {
        List<Quote> quotes = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                String text = values[0];
                String author = values[1];
                List<String> tags = Arrays.asList(values[2].split(", "));

                quotes.add(new Quote(text, author, tags));
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return quotes;
    }

    public static Map<String, Long> mostQuotedAuthors(List<Quote> quotes) {
        return quotes.stream()
                .collect(Collectors.groupingBy(Quote::getAuthor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, Long> mostCommonTags(List<Quote> quotes) {
        return quotes.stream()
                .flatMap(quote -> quote.getTags().stream())
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void main(String[] args) {
        String csvFilePath = "quotes.csv";
        List<Quote> quotes = loadQuotesFromCSV(csvFilePath);

        System.out.println("Most Quoted Authors:");
        Map<String, Long> topAuthors = mostQuotedAuthors(quotes);
        topAuthors.forEach((author, count) -> System.out.println(author + ": " + count));

        System.out.println("\nMost Common Tags:");
        Map<String, Long> topTags = mostCommonTags(quotes);
        topTags.forEach((tag, count) -> System.out.println(tag + ": " + count));
    }
}
