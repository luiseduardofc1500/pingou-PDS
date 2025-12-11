package com.subscription.examples.soccer.service;

import com.subscription.examples.soccer.domain.SoccerCustomerProfile;
import com.subscription.examples.soccer.dto.CustomerProfileRequestDTO;
import com.subscription.examples.soccer.repository.SoccerCustomerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class SoccerCustomerProfileService {

    private final SoccerCustomerProfileRepository profileRepository;

    public SoccerCustomerProfileService(SoccerCustomerProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public SoccerCustomerProfile createOrUpdate(CustomerProfileRequestDTO request) {
        SoccerCustomerProfile profile = profileRepository.findById(request.getCustomerId())
                .orElseGet(SoccerCustomerProfile::new);

        profile.setCustomerId(request.getCustomerId());
        profile.setHeightCm(request.getHeightCm());
        profile.setWeightKg(request.getWeightKg());
        profile.setChestCm(request.getChestCm());
        profile.setWaistCm(request.getWaistCm());
        profile.setRecommendedSize(calculateSize(profile));

        return profileRepository.save(profile);
    }

    public SoccerCustomerProfile getProfile(Long customerId) {
        return profileRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil de cliente não encontrado: " + customerId));
    }

    private String calculateSize(SoccerCustomerProfile profile) {
        double height = profile.getHeightCm() != null ? profile.getHeightCm() : 170.0;
        double weight = profile.getWeightKg() != null ? profile.getWeightKg() : 70.0;

        double bmi = weight / Math.pow(height / 100.0, 2);

        if (bmi < 20) {
            return "S";
        } else if (bmi < 23) {
            return "M";
        } else if (bmi < 27) {
            return "L";
        } else if (bmi < 31) {
            return "XL";
        } else {
            return "XXL";
        }
    }
}
