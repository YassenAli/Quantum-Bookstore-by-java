package org.store.model;

import java.lang.IllegalArgumentException;

/**
 * Physical paper book: finite stock + shippable + purchasable.
 */
public class PaperBook extends Book implements IStockable, IShippable, IPurchasable {
    private int stock;

    public PaperBook(String isbn, String title, int year, double price, String author, int initialStock) {
        super(isbn, title, year, price, author);
        this.stock = initialStock;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void reduceStock(int qty) {
        if (qty <= 0 || qty > stock) {
            throw new IllegalArgumentException("Invalid quantity: " + qty);
        }
        stock -= qty;
    }

    @Override
    public void shipTo(String address) {
        // stub: integrate with ShippingService
        System.out.printf("Shipping %d copy(ies) of '%s' to %s%n",
                1, getTitle(), address);
    }

    @Override
    public void purchase(int qty) {
        reduceStock(qty);
    }
}
