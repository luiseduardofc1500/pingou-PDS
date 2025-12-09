package com.subscription.framework.examples.comics;

import com.subscription.framework.core.service.AbstractProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço específico para HQs.
 * 
 * Demonstra como usar o framework para criar um serviço
 * completo de gerenciamento de histórias em quadrinhos.
 */
@Service
public class ComicService extends AbstractProductService<Comic, ComicResponseDTO, ComicRequestDTO> {
    
    private final ComicRepository comicRepository;
    
    public ComicService(ComicRepository comicRepository) {
        super(comicRepository);
        this.comicRepository = comicRepository;
    }
    
    // ========== Métodos específicos de HQ ==========
    
    public List<ComicResponseDTO> findByPublisher(String publisher) {
        return comicRepository.findByPublisherAndActiveTrue(publisher).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findBySeries(String series) {
        return comicRepository.findBySeriesContainingIgnoreCaseAndActiveTrue(series).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findByGenre(ComicGenre genre) {
        return comicRepository.findByGenreAndActiveTrue(genre).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findByFormat(ComicFormat format) {
        return comicRepository.findByFormatAndActiveTrue(format).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findByWriter(String writer) {
        return comicRepository.findByWriterContainingIgnoreCaseAndActiveTrue(writer).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findByArtist(String artist) {
        return comicRepository.findByArtistContainingIgnoreCaseAndActiveTrue(artist).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<ComicResponseDTO> findSpecialEditions() {
        return comicRepository.findByIsSpecialEditionTrueAndActiveTrue().stream()
                .map(this::toDTO)
                .toList();
    }
    
    // ========== Implementação dos métodos abstratos ==========
    
    @Override
    protected ComicResponseDTO toDTO(Comic entity) {
        return ComicResponseDTO.fromEntity(entity);
    }
    
    @Override
    protected Comic toEntity(ComicRequestDTO request) {
        Comic comic = new Comic();
        comic.setName(request.getName());
        comic.setDescription(request.getDescription());
        comic.setPrice(request.getPrice());
        comic.setImageUrl(request.getImageUrl());
        comic.setSku(request.getSku());
        comic.setCategory("COMICS");
        comic.setPublisher(request.getPublisher());
        comic.setIssueNumber(request.getIssueNumber());
        comic.setSeries(request.getSeries());
        comic.setGenre(request.getGenre());
        comic.setFormat(request.getFormat());
        comic.setWriter(request.getWriter());
        comic.setArtist(request.getArtist());
        comic.setPublicationDate(request.getPublicationDate());
        comic.setPageCount(request.getPageCount());
        comic.setLanguage(request.getLanguage());
        comic.setIsbn(request.getIsbn());
        comic.setAgeRating(request.getAgeRating());
        comic.setIsSpecialEdition(request.getIsSpecialEdition());
        return comic;
    }
    
    @Override
    protected void updateEntity(Comic entity, ComicRequestDTO request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setImageUrl(request.getImageUrl());
        entity.setSku(request.getSku());
        entity.setPublisher(request.getPublisher());
        entity.setIssueNumber(request.getIssueNumber());
        entity.setSeries(request.getSeries());
        entity.setGenre(request.getGenre());
        entity.setFormat(request.getFormat());
        entity.setWriter(request.getWriter());
        entity.setArtist(request.getArtist());
        entity.setPublicationDate(request.getPublicationDate());
        entity.setPageCount(request.getPageCount());
        entity.setLanguage(request.getLanguage());
        entity.setIsbn(request.getIsbn());
        entity.setAgeRating(request.getAgeRating());
        entity.setIsSpecialEdition(request.getIsSpecialEdition());
    }
    
    @Override
    protected void validateRequest(ComicRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Comic name is required");
        }
        if (request.getPublisher() == null || request.getPublisher().isBlank()) {
            throw new IllegalArgumentException("Publisher is required");
        }
        if (request.getSeries() == null || request.getSeries().isBlank()) {
            throw new IllegalArgumentException("Series is required");
        }
        if (request.getGenre() == null) {
            throw new IllegalArgumentException("Genre is required");
        }
        if (request.getFormat() == null) {
            throw new IllegalArgumentException("Format is required");
        }
    }
}
