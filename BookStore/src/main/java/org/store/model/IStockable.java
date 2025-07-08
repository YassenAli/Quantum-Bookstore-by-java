package org.store.model;

import java.lang.IllegalArgumentException;

/**
 * For items with a finite stock.
 */
public interface IStockable {
    int getStock();
    void reduceStock(int qty) throws IllegalArgumentException;
}
