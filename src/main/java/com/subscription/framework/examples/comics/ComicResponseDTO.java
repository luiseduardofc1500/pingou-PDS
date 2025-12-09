package com.subscription.framework.examples.comics;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de resposta para HQ.
 */
@Getter
@Setter
public class ComicResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String sku;
    private String category;
    private Boolean active;
    
    // Campos específicos de HQ
    private String publisher;
    private Integer issueNumber;
    private String series;
    private String fullTitle;
    private ComicGenre genre;
    private String genreDisplayName;
    private ComicFormat format;
    private String formatDisplayName;
    private String writer;
    private String artist;
    private LocalDate publicationDate;
    private Integer pageCount;
    private String language;
    private String isbn;
    private String ageRating;
    private Boolean isSpecialEdition;
    
    /**
     * Converte uma entidade Comic para DTO.
     */
    public static ComicResponseDTO fromEntity(Comic comic) {
        ComicResponseDTO dto = new ComicResponseDTO();
        dto.setId(comic.getId());
        dto.setName(comic.getName());
        dto.setDescription(comic.getDescription());
        dto.setPrice(comic.getPrice());
        dto.setImageUrl(comic.getImageUrl());
        dto.setSku(comic.getSku());
        dto.setCategory(comic.getCategory());
        dto.setActive(comic.getActive());
        dto.setPublisher(comic.getPublisher());
        dto.setIssueNumber(comic.getIssueNumber());
        dto.setSeries(comic.getSeries());
        dto.setFullTitle(comic.getFullTitle());
        dto.setGenre(comic.getGenre());
        dto.setGenreDisplayName(comic.getGenre() != null ? comic.getGenre().getDisplayName() : null);
        dto.setFormat(comic.getFormat());
        dto.setFormatDisplayName(comic.getFormat() != null ? comic.getFormat().getDisplayName() : null);
        dto.setWriter(comic.getWriter());
        dto.setArtist(comic.getArtist());
        dto.setPublicationDate(comic.getPublicationDate());
        dto.setPageCount(comic.getPageCount());
        dto.setLanguage(comic.getLanguage());
        dto.setIsbn(comic.getIsbn());
        dto.setAgeRating(comic.getAgeRating());
        dto.setIsSpecialEdition(comic.getIsSpecialEdition());
        return dto;
    }
}
