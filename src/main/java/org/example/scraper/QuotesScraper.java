package org.example.scraper;

import org.example.model.Quote;
import org.example.utility.SaveQuotesToCSV;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuotesScraper {

    private static final String BASE_URL = "https://quotes.toscrape.com/page/";

    public static List<Quote> scrapeAllPages() {
        List<Quote> allQuotes = new ArrayList<>();
        int currentPage = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            String pageUrl = BASE_URL + currentPage;
            try {
                Document doc = Jsoup.connect(pageUrl).get();
                Elements quotesElements = doc.select(".quote");

                if (quotesElements.isEmpty()) {
                    hasMorePages = false;
                } else {
                    for (Element quoteElement : quotesElements) {
                        String text = quoteElement.select(".text").text();
                        String author = quoteElement.select(".author").text();
                        List<String> tags = quoteElement.select(".tags a.tag").eachText();

                        allQuotes.add(new Quote(text, author, tags));
                    }
                    currentPage++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                hasMorePages = false;
            }
        }

        return allQuotes;
    }

    public static void main(String[] args) {
        List<Quote> quotes = scrapeAllPages();
        quotes.forEach(System.out::println);

        SaveQuotesToCSV.saveToCSV(quotes, "quotes.csv");
    }
}
