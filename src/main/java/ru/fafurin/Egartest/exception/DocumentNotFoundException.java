package ru.fafurin.Egartest.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super(String.format("Document with id: %d not found", id));
    }
}
