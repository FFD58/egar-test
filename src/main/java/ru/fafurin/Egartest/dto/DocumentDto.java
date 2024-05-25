package ru.fafurin.Egartest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO документа с валидацией
 *
 * @title - название (обязательное поле)
 * @number - номер (не может быть меньше 1)
 * @content - содержимое (от 20 до 2000 символов)
 * @type - тип (обязательное поле)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    @NotEmpty(message = "The title is required")
    private String title;

    @Min(1)
    private Integer number;

    @Size(min = 20, message = "The content should be at least 20 characters")
    @Size(max = 2000, message = "The content cannot exceed 2000 characters")
    private String content;

    @NotEmpty(message = "The type is required")
    private String type;
}
