package org.store.service;

public interface IOrderService {
    double buy(String isbn, int qty, String email, String address);
}
