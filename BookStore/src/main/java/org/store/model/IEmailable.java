package org.store.model;

/**
 * For products that can be sent via email.
 */
public interface IEmailable {
    void emailTo(String email);
}
