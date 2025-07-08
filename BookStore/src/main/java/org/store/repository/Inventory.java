package org.store.repository;

import org.store.model.IBook;
import java.util.*;
import java.util.stream.Collectors;

public class Inventory implements IBookRepository {
    private Map<String, IBook> bookMap = new HashMap<>();
    private Map<String, Integer> stockMap = new HashMap<>();

    @Override
    public void addBook(IBook book, int qty) {
        bookMap.put(book.getIsbn(), book);
        stockMap.put(book.getIsbn(), stockMap.getOrDefault(book.getIsbn(), 0) + qty);
    }

    @Override
    public List<IBook> removeOutdated(int cutoffYear) {
        List<IBook> removed = bookMap.values().stream()
                .filter(b -> b.getYear() < cutoffYear)
                .collect(Collectors.toList());
        removed.forEach(b -> {
            bookMap.remove(b.getIsbn());
            stockMap.remove(b.getIsbn());
        });
        return removed;
    }

    @Override
    public IBook findByIsbn(String isbn) {
        IBook book = bookMap.get(isbn);
        if (book == null) {
            throw new NoSuchElementException("ISBN not found: " + isbn);
        }
        return book;
    }

    @Override
    public int getStock(String isbn) {
        return stockMap.getOrDefault(isbn, 0);
    }

    @Override
    public void reduceStock(String isbn, int qty) {
        int current = getStock(isbn);
        if (qty <= 0 || qty > current) {
            throw new IllegalArgumentException("Insufficient stock for ISBN " + isbn + ": requested=" +qty + ", available=" + current);
        }
        stockMap.put(isbn, current - qty);
    }
}
