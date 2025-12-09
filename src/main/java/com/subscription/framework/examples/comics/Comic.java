package com.subscription.framework.examples.comics;

import com.subscription.framework.core.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Exemplo de implementação: Produto HQ (História em Quadrinhos).
 * 
 * Esta classe demonstra como estender o framework de assinaturas
 * para criar um produto específico do domínio de histórias em quadrinhos.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Entity
@Table(name = "comics")
@Getter
@Setter
public class Comic extends Product {

    /** Editora da HQ */
    @Column(nullable = false)
    private String publisher;
    
    /** Número da edição */
    @Column(name = "issue_number")
    private Integer issueNumber;
    
    /** Nome da série */
    @Column(nullable = false)
    private String series;
    
    /** Gênero da HQ */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComicGenre genre;
    
    /** Formato da publicação */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComicFormat format;
    
    /** Escritor(es) */
    @Column
    private String writer;
    
    /** Artista(s)/Ilustrador(es) */
    @Column
    private String artist;
    
    /** Data de publicação */
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    /** Número de páginas */
    @Column(name = "page_count")
    private Integer pageCount;
    
    /** Idioma */
    @Column
    private String language = "Portuguese";
    
    /** ISBN (se aplicável) */
    @Column
    private String isbn;
    
    /** Classificação indicativa */
    @Column(name = "age_rating")
    private String ageRating;
    
    /** É edição especial/limitada */
    @Column(name = "is_special_edition")
    private Boolean isSpecialEdition = false;
    
    public Comic() {
        super();
    }
    
    public Comic(String name, String description, BigDecimal price,
                 String publisher, String series, ComicGenre genre, ComicFormat format) {
        super(name, description, price);
        this.publisher = publisher;
        this.series = series;
        this.genre = genre;
        this.format = format;
        this.setCategory("COMICS");
    }
    
    /**
     * Retorna o título completo da HQ.
     */
    public String getFullTitle() {
        if (issueNumber != null) {
            return String.format("%s #%d - %s", series, issueNumber, getName());
        }
        return String.format("%s - %s", series, getName());
    }
    
    @Override
    public boolean isValidForPackage() {
        return super.isValidForPackage() 
                && publisher != null && !publisher.isBlank()
                && series != null && !series.isBlank();
    }
}
