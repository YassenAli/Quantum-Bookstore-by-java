package org.store.delivery;

import org.store.model.IBook;

public class ShippingService {
    /** send a physical book to the address */
    public void ship(IBook book, String address) {
        System.out.printf("Shipping ISBN=%s titled '%s' to %s%n",
                book.getIsbn(), book.getTitle(), address);
    }
}
