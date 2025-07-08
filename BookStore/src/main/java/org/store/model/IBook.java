package org.store.model;

/**
 * Core book interface.
 */
public interface IBook {
    String getIsbn();
    String getTitle();
    int getYear();
    double getPrice();
    String getAuthor();
}
