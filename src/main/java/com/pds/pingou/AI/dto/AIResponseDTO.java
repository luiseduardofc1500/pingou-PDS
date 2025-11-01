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

    private String answer;
    private boolean success;
    private String errorMessage;
    
    public AIResponseDTO(String answer) {
        this.answer = answer;
        this.success = true;
    }

    public static AIResponseDTO error(String errorMessage) {
        AIResponseDTO dto = new AIResponseDTO();
        dto.setSuccess(false);
        dto.setErrorMessage(errorMessage);
        return dto;
    }
}

