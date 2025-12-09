package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.service.AbstractProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço específico para cachaças.
 * 
 * Estende o serviço abstrato de produtos e implementa os métodos
 * de mapeamento específicos para cachaça.
 */
@Service
public class CachacaService extends AbstractProductService<Cachaca, CachacaResponseDTO, CachacaRequestDTO> {
    
    private final CachacaRepository cachacaRepository;
    
    public CachacaService(CachacaRepository cachacaRepository) {
        super(cachacaRepository);
        this.cachacaRepository = cachacaRepository;
    }
    
    // ========== Métodos específicos de cachaça ==========
    
    public List<CachacaResponseDTO> findByRegion(String region) {
        return cachacaRepository.findByRegionAndActiveTrue(region).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<CachacaResponseDTO> findByType(CachacaType type) {
        return cachacaRepository.findByCachacaTypeAndActiveTrue(type).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<CachacaResponseDTO> findByAgingType(AgingType agingType) {
        return cachacaRepository.findByAgingTypeAndActiveTrue(agingType).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<CachacaResponseDTO> findByDistillery(String distillery) {
        return cachacaRepository.findByDistilleryContainingIgnoreCaseAndActiveTrue(distillery).stream()
                .map(this::toDTO)
                .toList();
    }
    
    public List<CachacaResponseDTO> findAgedAtLeast(Integer months) {
        return cachacaRepository.findByAgingMonthsGreaterThanEqualAndActiveTrue(months).stream()
                .map(this::toDTO)
                .toList();
    }
    
    // ========== Implementação dos métodos abstratos ==========
    
    @Override
    protected CachacaResponseDTO toDTO(Cachaca entity) {
        return CachacaResponseDTO.fromEntity(entity);
    }
    
    @Override
    protected Cachaca toEntity(CachacaRequestDTO request) {
        Cachaca cachaca = new Cachaca();
        cachaca.setName(request.getName());
        cachaca.setDescription(request.getDescription());
        cachaca.setPrice(request.getPrice());
        cachaca.setImageUrl(request.getImageUrl());
        cachaca.setSku(request.getSku());
        cachaca.setCategory("CACHACA");
        cachaca.setRegion(request.getRegion());
        cachaca.setAlcoholContent(request.getAlcoholContent());
        cachaca.setVolume(request.getVolume());
        cachaca.setCachacaType(request.getCachacaType());
        cachaca.setAgingType(request.getAgingType());
        cachaca.setAgingMonths(request.getAgingMonths());
        cachaca.setDistillery(request.getDistillery());
        cachaca.setProductionYear(request.getProductionYear());
        return cachaca;
    }
    
    @Override
    protected void updateEntity(Cachaca entity, CachacaRequestDTO request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setImageUrl(request.getImageUrl());
        entity.setSku(request.getSku());
        entity.setRegion(request.getRegion());
        entity.setAlcoholContent(request.getAlcoholContent());
        entity.setVolume(request.getVolume());
        entity.setCachacaType(request.getCachacaType());
        entity.setAgingType(request.getAgingType());
        entity.setAgingMonths(request.getAgingMonths());
        entity.setDistillery(request.getDistillery());
        entity.setProductionYear(request.getProductionYear());
    }
    
    @Override
    protected void validateRequest(CachacaRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Cachaca name is required");
        }
        if (request.getRegion() == null || request.getRegion().isBlank()) {
            throw new IllegalArgumentException("Region is required");
        }
        if (request.getAlcoholContent() == null) {
            throw new IllegalArgumentException("Alcohol content is required");
        }
        if (request.getVolume() == null || request.getVolume() <= 0) {
            throw new IllegalArgumentException("Volume must be greater than zero");
        }
        if (request.getCachacaType() == null) {
            throw new IllegalArgumentException("Cachaca type is required");
        }
    }
}
