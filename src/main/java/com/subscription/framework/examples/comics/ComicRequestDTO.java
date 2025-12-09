package com.subscription.framework.examples.comics;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de requisição para HQ.
 */
@Getter
@Setter
public class ComicRequestDTO {
    
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String sku;
    
    // Campos específicos de HQ
    private String publisher;
    private Integer issueNumber;
    private String series;
    private ComicGenre genre;
    private ComicFormat format;
    private String writer;
    private String artist;
    private LocalDate publicationDate;
    private Integer pageCount;
    private String language = "Portuguese";
    private String isbn;
    private String ageRating;
    private Boolean isSpecialEdition = false;
}
