package ru.fafurin.Egartest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fafurin.Egartest.model.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Получить список документов, отсортированных по дате обновления с пагинацией
     * @param pageable - интерфейс Spring Data для реализации пагинации
     * @return список документов, отсортированных по дате обновления с пагинацией
     */
    @Query("SELECT d FROM Document d ORDER BY updatedAt DESC")
    Page<Document> findAll(Pageable pageable);

    /**
     * Получить список документов по строке поиска
     * @param searchLine - строка поиска
     * @return список документов
     */
    @Query("SELECT d FROM Document d WHERE CONCAT(d.title, d.type, d.number) LIKE %?1%")
    List<Document> findBySearchLine(String searchLine);
}
