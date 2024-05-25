package ru.fafurin.Egartest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fafurin.Egartest.dto.DocumentDto;
import ru.fafurin.Egartest.exception.DocumentNotFoundException;
import ru.fafurin.Egartest.mapper.DocumentMapper;
import ru.fafurin.Egartest.model.Document;
import ru.fafurin.Egartest.service.DocumentServiceContract;

import java.util.List;

/**
 * Обработка запросов на отображение, сохранение и изменение документов
 */
@Controller
@Log4j2
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceContract documentService;

    /**
     * Отображение страницы со всеми документами с пагинацией
     * Отображение информации о документе по идентификатору
     * @param model - объект, содержащий атрибуты для рендеринга страницы
     * @param page - номер страницы пагинации
     * @param id - идентификатор документа
     * @return шаблон главной страницы
     */
    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "0") Long id) {
        Page<Document> documents = documentService.findAll(page);
        model.addAttribute("documents", documents);
        model.addAttribute("currentPage", page);
        if (id != 0) {
            try {
                Document documentView = documentService.findById(id);
                model.addAttribute("documentView", documentView);
            } catch (DocumentNotFoundException e) {
                log.error(e.getMessage(), e);
                return "redirect:/";
            }
        }
        return "index";
    }

    /**
     * Отображение страницы с найденными документами по строке поиска
     * @param model - объект, содержащий атрибуты для рендеринга страницы
     * @param search - строка поиска
     * @return шаблон страницы с результатами поиска
     */
    @GetMapping("/search")
    public String getSearch(Model model,
                            @Param("search") String search) {
        List<Document> documents = documentService.findBySearchLine(search);
        model.addAttribute("documents", documents);
        model.addAttribute("search", search);
        return "search";
    }

    /**
     * Отображение страницы с формой для создания нового документа
     * @param model - объект, содержащий атрибуты для рендеринга страницы
     * @return шаблон с формой для создания нового документа
     */
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        DocumentDto documentDto = new DocumentDto();
        model.addAttribute("documentDto", documentDto);
        return "create";
    }

    /**
     * Обработка формы для создания нового документа
     * @param documentDto - DTO документа с новыми данными
     * @param result - объект, содержащий ошибки валидации
     * @return шаблон создания документа, если допущены ошибки валидации,
     *         или шаблон главной страницы в случае успешного создания документа
     */
    @PostMapping("/create")
    public String createDocument(@Valid @ModelAttribute DocumentDto documentDto,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        documentService.save(documentDto);
        return "redirect:/";
    }

    /**
     * Отображение страницы с формой для изменения существующего документа
     * @param model - объект, содержащий атрибуты для рендеринга страницы
     * @param id - идентификатор документа
     * @return шаблон изменения документа,
     *         или шаблон главной страницы в случае если документ не найден
     */
    @GetMapping("/edit")
    public String showEditPage(Model model,
                               @RequestParam Long id) {
        try {
            Document document = documentService.findById(id);
            model.addAttribute("document", document);
            DocumentDto documentDto = DocumentMapper.getDocumentDto(document);
            model.addAttribute("documentDto", documentDto);
        } catch (DocumentNotFoundException e) {
            log.error(e.getMessage(), e);
            return "redirect:/";
        }
        return "edit";
    }

    /**
     * Обработка формы для изменения существующего документа
     * @param model - объект, содержащий атрибуты для рендеринга страницы
     * @param id - идентификатор документа
     * @param documentDto - DTO документа с новыми данными
     * @param result - объект, содержащий ошибки валидации
     * @return шаблон изменения документа, если допущены ошибки валидации,
     *         или шаблон главной страницы в случае успешного создания документа,
     *         или если документ не найден
     */
    @PostMapping("/edit")
    public String updateDocument(Model model,
                                 @RequestParam Long id,
                                 @Valid @ModelAttribute DocumentDto documentDto,
                                 BindingResult result) {
        try {
            Document document = documentService.findById(id);
            model.addAttribute("document", document);
            if (result.hasErrors()) {
                return "edit";
            }
            documentService.update(document, documentDto);
        } catch (DocumentNotFoundException e) {
            log.error(e.getMessage(), e);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
