package com.subscription.examples.soccer.service;

import com.subscription.examples.soccer.domain.CustomerJerseyHistory;
import com.subscription.examples.soccer.domain.SoccerCustomerProfile;
import com.subscription.examples.soccer.domain.SoccerJersey;
import com.subscription.examples.soccer.dto.SoccerJerseyRequestDTO;
import com.subscription.examples.soccer.dto.SoccerJerseyResponseDTO;
import com.subscription.examples.soccer.repository.CustomerJerseyHistoryRepository;
import com.subscription.examples.soccer.repository.SoccerCustomerProfileRepository;
import com.subscription.examples.soccer.repository.SoccerJerseyRepository;
import com.subscription.framework.core.service.AbstractProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoccerJerseyService extends AbstractProductService<SoccerJersey, SoccerJerseyResponseDTO, SoccerJerseyRequestDTO> {

    private final SoccerJerseyRepository jerseyRepository;
    private final SoccerCustomerProfileRepository profileRepository;
    private final CustomerJerseyHistoryRepository historyRepository;

    public SoccerJerseyService(SoccerJerseyRepository jerseyRepository,
                               SoccerCustomerProfileRepository profileRepository,
                               CustomerJerseyHistoryRepository historyRepository) {
        super(jerseyRepository);
        this.jerseyRepository = jerseyRepository;
        this.profileRepository = profileRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    protected SoccerJerseyResponseDTO toDTO(SoccerJersey entity) {
        return SoccerJerseyResponseDTO.fromEntity(entity);
    }

    @Override
    protected SoccerJersey toEntity(SoccerJerseyRequestDTO request) {
        SoccerJersey jersey = new SoccerJersey();
        jersey.setName(request.getName());
        jersey.setDescription(request.getDescription());
        jersey.setPrice(request.getPrice());
        jersey.setSku(request.getSku());
        jersey.setTeamName(request.getTeamName());
        jersey.setCountry(request.getCountry());
        jersey.setLeague(request.getLeague());
        jersey.setSeason(request.getSeason());
        jersey.setPersonalized(Boolean.TRUE.equals(request.getPersonalized()));
        jersey.setPlayerName(request.getPlayerName());
        jersey.setPlayerNumber(request.getPlayerNumber());
        jersey.setSize(request.getSize());
        return jersey;
    }

    @Override
    protected void updateEntity(SoccerJersey entity, SoccerJerseyRequestDTO request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setSku(request.getSku());
        entity.setTeamName(request.getTeamName());
        entity.setCountry(request.getCountry());
        entity.setLeague(request.getLeague());
        entity.setSeason(request.getSeason());
        entity.setPersonalized(Boolean.TRUE.equals(request.getPersonalized()));
        entity.setPlayerName(request.getPlayerName());
        entity.setPlayerNumber(request.getPlayerNumber());
        entity.setSize(request.getSize());
    }

    /**
     * Recomenda uma nova camisa para o cliente, evitando times que ele já recebeu.
     */
    public SoccerJersey recommendJerseyForCustomer(Long customerId) {
        SoccerCustomerProfile profile = profileRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil de cliente não encontrado: " + customerId));

        String size = profile.getRecommendedSize();

        List<CustomerJerseyHistory> history = historyRepository.findByCustomerId(customerId);
        List<String> ownedTeams = history.stream()
                .map(CustomerJerseyHistory::getTeamName)
                .collect(Collectors.toList());

        List<SoccerJersey> candidates;
        if (ownedTeams.isEmpty()) {
            candidates = jerseyRepository.findBySizeAndActiveTrue(size);
        } else {
            candidates = jerseyRepository.findBySizeAndTeamNameNotInAndActiveTrue(size, ownedTeams);
            if (candidates.isEmpty()) {
                // fallback: permite repetir time se já esgotamos as opções
                candidates = jerseyRepository.findBySizeAndActiveTrue(size);
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException("Nenhuma camisa disponível para o tamanho " + size);
        }

        // Estratégia simples: escolhe a primeira disponível
        SoccerJersey chosen = candidates.getFirst();

        // Atualiza histórico
        CustomerJerseyHistory entry = historyRepository
                .findByCustomerIdAndTeamName(customerId, chosen.getTeamName())
                .orElseGet(() -> {
                    CustomerJerseyHistory h = new CustomerJerseyHistory();
                    h.setCustomerId(customerId);
                    h.setTeamName(chosen.getTeamName());
                    h.setJerseyCount(0);
                    return h;
                });
        entry.increment();
        historyRepository.save(entry);

        return chosen;
    }

    public List<SoccerJerseyResponseDTO> findAllBySize(String size) {
        return jerseyRepository.findBySizeAndActiveTrue(size).stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
    }
}
