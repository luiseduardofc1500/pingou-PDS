package com.subscription.framework.examples.comics;

import com.subscription.framework.core.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório específico para HQs.
 */
@Repository
public interface ComicRepository extends ProductRepository<Comic> {
    
    /**
     * Busca HQs por editora.
     */
    List<Comic> findByPublisherAndActiveTrue(String publisher);
    
    /**
     * Busca HQs por série.
     */
    List<Comic> findBySeriesContainingIgnoreCaseAndActiveTrue(String series);
    
    /**
     * Busca HQs por gênero.
     */
    List<Comic> findByGenreAndActiveTrue(ComicGenre genre);
    
    /**
     * Busca HQs por formato.
     */
    List<Comic> findByFormatAndActiveTrue(ComicFormat format);
    
    /**
     * Busca HQs por escritor.
     */
    List<Comic> findByWriterContainingIgnoreCaseAndActiveTrue(String writer);
    
    /**
     * Busca HQs por artista.
     */
    List<Comic> findByArtistContainingIgnoreCaseAndActiveTrue(String artist);
    
    /**
     * Busca edições especiais.
     */
    List<Comic> findByIsSpecialEditionTrueAndActiveTrue();
}
