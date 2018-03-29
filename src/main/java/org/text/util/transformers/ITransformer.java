package org.text.util.transformers;

/**
 * Interface for the request and response transformation layer
 */
public interface ITransformer<I, O> {
    public O transform(I input);
}
