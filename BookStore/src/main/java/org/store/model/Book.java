package org.store.model;

/**
 * Base abstract class holding common book data.
 */
public abstract class Book implements IBook {
    private String isbn;
    private String title;
    private int year;
    private double price;
    private String author;

    protected Book(String isbn, String title, int year, double price, String author) {
        this.isbn   = isbn;
        this.title  = title;
        this.year   = year;
        this.price  = price;
        this.author = author;
    }

    @Override public String getIsbn()   {
        return isbn;
    }
    @Override public String getTitle()  {
        return title;
    }
    @Override public int    getYear()   {
        return year;
    }
    @Override public double getPrice()  {
        return price;
    }
    @Override public String getAuthor() {
        return author;
    }
}
