package com.example.pharmacy.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName;

    // 위도 경도는 실수값
    // 경도
    @JsonProperty("x")
    private double longitude;

    // 위도
    @JsonProperty("y")
    private double latitude;
}
