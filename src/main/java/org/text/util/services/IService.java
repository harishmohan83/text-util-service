package org.text.util.services;

/**
 * Interface for the service layer
 */
public interface IService<I, O> {
    public O service(I domain);
}
