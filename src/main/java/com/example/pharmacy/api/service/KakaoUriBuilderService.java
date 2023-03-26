package com.example.pharmacy.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        // cmd + opt + v -> 앞 변수 추출
        // 특수문자, 공백, 한글 등 인코딩 필요 -> encode()
        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }
}
