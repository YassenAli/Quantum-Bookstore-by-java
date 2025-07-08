package org.store.delivery;

import org.store.model.IBook;

public class MailService {
    /** send an e‑book link to the email */
    public void send(IBook book, String email) {
        System.out.printf("Sending e‑book ISBN=%s titled '%s' to %s%n",
                book.getIsbn(), book.getTitle(), email);
    }
}
