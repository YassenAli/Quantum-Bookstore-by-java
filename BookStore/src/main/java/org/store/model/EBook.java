package org.store.model;

import java.lang.IllegalArgumentException;

/**
 * Digital e‑book: unlimited stock + emailable + purchasable.
 */
public class EBook extends Book implements IEmailable, IPurchasable {

    private String fileType;

    public EBook(String isbn, String title, int year, double price, String author, String fileType) {
        super(isbn, title, year, price, author);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public void emailTo(String email) {
        // stub: integrate with MailService
        System.out.printf("Emailing '%s' (%s) to %s%n", getTitle(), fileType, email);
    }

    @Override
    public void purchase(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        // no stock to reduce
    }
}
