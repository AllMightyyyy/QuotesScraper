package org.example.model;

import java.util.List;

public class Quote {
    private String text;
    private String author;
    private List<String> tags;

    public Quote(String text, String author, List<String> tags) {
        this.text = text;
        this.author = author;
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                '}';
    }
}
