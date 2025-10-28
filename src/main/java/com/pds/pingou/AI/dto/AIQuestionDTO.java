package com.pds.pingou.AI.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AIQuestionDTO {
    private String question;
    private String contextType = "pingou";
}

