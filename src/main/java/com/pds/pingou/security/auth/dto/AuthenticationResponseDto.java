package com.pds.pingou.security.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponseDto(
        @JsonProperty("access_token")
        String access_token,
        @JsonProperty("refresh_token")
        String refresh_token
) {
}
