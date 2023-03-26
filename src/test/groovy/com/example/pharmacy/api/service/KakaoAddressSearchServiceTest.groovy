package com.example.pharmacy.api.service

import com.example.pharmacy.AbstractIntegrationContainerBaseTest
import com.example.pharmacy.api.dto.KakaoApiResponseDto
import org.springframework.beans.factory.annotation.Autowired

// env 값(kakao api key 등) edit configuration 에서 세팅해줘야함. 이유: 통합테스트 이므로 전체 스프링 컨네이너에서 빈을 스캔할때 모든 컴포넌트를 스캔하기 때문
class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null 을 리턴한다."() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid 하다면, requestAddressSearch 메소드는 정상적으로 document 를 반환한다."() {
        given:
        def address = "서울 강동구 올림픽로 104길 42"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

}
