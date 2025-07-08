package org.store.model;

/**
 * For products that can be shipped.
 */
public interface IShippable {
    void shipTo(String address);
}
