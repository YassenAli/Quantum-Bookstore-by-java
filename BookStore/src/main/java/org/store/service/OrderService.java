package org.store.service;

import org.store.delivery.MailService;
import org.store.delivery.ShippingService;
import org.store.model.IBook;
import org.store.model.IStockable;
import org.store.repository.IBookRepository;

public class OrderService implements IOrderService {
    private IBookRepository repo;
    private ShippingService shipping;
    private MailService mail;

    public OrderService(IBookRepository repo, ShippingService shipping, MailService mail) {
        this.repo      = repo;
        this.shipping  = shipping;
        this.mail      = mail;
    }

    @Override
    public double buy(String isbn, int qty, String email, String address) {
        IBook book = repo.findByIsbn(isbn);

        // reduce inventory
        repo.reduceStock(isbn, qty);

        // dispatch
        if (book instanceof IStockable) {
            shipping.ship(book, address);
        } else {
            mail.send(book, email);
        }

        // return paid amount
        return book.getPrice() * qty;
    }
}
