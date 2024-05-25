package ru.fafurin.Egartest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.fafurin.Egartest.dto.DocumentDto;
import ru.fafurin.Egartest.exception.DocumentNotFoundException;
import ru.fafurin.Egartest.mapper.DocumentMapper;
import ru.fafurin.Egartest.model.Document;
import ru.fafurin.Egartest.repository.DocumentRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DocumentService implements DocumentServiceContract {

    private static final int DOCUMENTS_BY_PAGE = 5;

    private final DocumentRepository documentRepository;

    /**
     * Получить список документов по строке поиска
     * @param searchLine - строка поиска
     * @return - список документов
     */
    @Override
    public List<Document> findBySearchLine(String searchLine) {
        return documentRepository.findBySearchLine(searchLine);
    }

    /**
     * Получить список документов с пагинацией
     * @param page - номер страницы пагинации
     * @return список документов с пагинацией
     */
    @Override
    public Page<Document> findAll(int page) {
        return documentRepository.findAll(PageRequest.of(page, DOCUMENTS_BY_PAGE));
    }

    /**
     * Сохранить новый документ с параметрами: название, номер, содержание, тип
     * даты создания и изменения документа создадутся автоматически
     * @param documentDto - DTO документа с новыми данными
     */
    @Override
    public void save(DocumentDto documentDto) {
        Document document = DocumentMapper.getDocument(new Document(), documentDto);
        documentRepository.save(document);
        log.info("Document with id: {} saved", document.getId());
    }

    /**
     * Получить документ по идентификатору
     * @param id - идентификатор документа
     * @return документ
     */
    @Override
    public Document findById(Long id) {
        return documentRepository
                .findById(id)
                .orElseThrow(() -> new DocumentNotFoundException(id));
    }

    /**
     * Изменить существующий документ
     * @param document - существующий документ
     * @param documentDto - DTO документа с новыми данными
     */
    @Override
    public void update(Document document, DocumentDto documentDto) {
        documentRepository.save(DocumentMapper.getDocument(document, documentDto));
        log.info("Document with id: {} updated", document.getId());
    }
}
