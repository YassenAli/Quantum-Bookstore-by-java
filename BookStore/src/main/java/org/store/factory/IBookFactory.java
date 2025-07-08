package org.store.factory;

import org.store.model.IBook;

public interface IBookFactory {
    IBook create(String type, String isbn, String title, int year, double price, String author, Object extra);
}
