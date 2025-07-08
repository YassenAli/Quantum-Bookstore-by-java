package org.store.model;

/**
 * Showcase/demo book: not for sale
 */
public class DemoBook extends Book {
    public DemoBook(String isbn, String title, int year, double price, String author) {
        super(isbn, title, year, price, author);
    }

    @Override
    public int getYear() {
        return super.getYear();
    }
}
