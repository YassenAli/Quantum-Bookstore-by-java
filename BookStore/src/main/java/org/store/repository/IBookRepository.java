package org.store.repository;

import org.store.model.IBook;
import java.util.List;

public interface IBookRepository {
    void addBook(IBook book, int qty);
    List<IBook> removeOutdated(int cutoffYear);
    IBook findByIsbn(String isbn);
    int getStock(String isbn);
    void reduceStock(String isbn, int qty) throws IllegalArgumentException;
}
