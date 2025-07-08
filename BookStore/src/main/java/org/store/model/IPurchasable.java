package org.store.model;

import java.lang.IllegalArgumentException;

/**
 * Marks a product as purchasable.
 */
public interface IPurchasable {
    void purchase(int qty) throws IllegalArgumentException;
}
