package com.pds.pingou.AI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseDTO {

    private String response;
    private boolean success;
    private String error;
    public AIResponseDTO(String response) {
        this.response = response;
        this.success = true;
    }

    public static AIResponseDTO error(String errorMessage) {
        AIResponseDTO dto = new AIResponseDTO();
        dto.setSuccess(false);
        dto.setError(errorMessage);
        return dto;
    }
}

