package ru.fafurin.Egartest.mapper;

import ru.fafurin.Egartest.dto.DocumentDto;
import ru.fafurin.Egartest.model.Document;

public class DocumentMapper {

    /**
     * Получить документ из DTO
     * @param document - существующий или новый документ
     * @param documentDto - DTO документа с новыми данными
     * @return документ
     */
    public static Document getDocument(Document document, DocumentDto documentDto) {
        document.setTitle(documentDto.getTitle());
        document.setContent(documentDto.getContent());
        document.setNumber(documentDto.getNumber());
        document.setType(documentDto.getType());
        return document;
    }

    /**
     * Получить DTO из документа
     * @param document - существующий документ
     * @return DTO
     */
    public static DocumentDto getDocumentDto(Document document) {
        return DocumentDto.builder()
                .title(document.getTitle())
                .type(document.getType())
                .content(document.getContent())
                .number(document.getNumber())
                .build();
    }
}
