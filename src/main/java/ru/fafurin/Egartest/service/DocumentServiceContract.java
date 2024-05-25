package ru.fafurin.Egartest.service;

import org.springframework.data.domain.Page;
import ru.fafurin.Egartest.dto.DocumentDto;
import ru.fafurin.Egartest.model.Document;

import java.util.List;

public interface DocumentServiceContract {

    Page<Document> findAll(int page);

    List<Document> findBySearchLine(String searchLine);

    Document findById(Long id);

    void save(DocumentDto documentDto);

    void update(Document document, DocumentDto documentDto);

}
