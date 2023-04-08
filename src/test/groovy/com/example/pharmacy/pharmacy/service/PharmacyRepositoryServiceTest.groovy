package com.example.pharmacy.pharmacy.service

import com.example.pharmacy.AbstractIntegrationContainerBaseTest
import com.example.pharmacy.pharmacy.entity.Pharmacy
import com.example.pharmacy.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "Transaction 을 걸어야 dirty check 가 된다"() {
        given:
        String originalAddress = "서울 강동구 암사동"
        String modifiedAddress = "서울 강동구 천호동 중흥 s 클래스"
        String name = "강동약국"
        def pharmacy = Pharmacy.builder()
                    .pharmacyAddress(originalAddress)
                    .pharmacyName(name)
                    .build()

        when:
        def newPharmacy = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(newPharmacy.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).pharmacyAddress == modifiedAddress

    }

    def "Transaction 을 안걸면 dirty check 가 안된다"() {
        given:
        String originalAddress = "서울 강동구 암사동"
        String modifiedAddress = "서울 강동구 천호동 중흥 s 클래스"
        String name = "강동약국"
        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(originalAddress)
                .pharmacyName(name)
                .build()

        when:
        def newPharmacy = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransaction(newPharmacy.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).pharmacyAddress == originalAddress
    }

    def "self invocation"() {
        given:
        String address = "서울 강동구 암사동"
        String name = "강동약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))

        then:
        // spock 에서 에러 처리
        def e = thrown(RuntimeException.class)
        e.message == "error"
        def result = pharmacyRepository.findAll()
        result.size() == 1 // 트랜잭션이 적용되지 않는다( 롤백 적용 안됨 )
    }

    def "transactional readOnly test"() {
        given:
        String address = "서울 강동구 암사동"
        String modifiedAddress = "서울 강남구 역삼동"
        String name = "강동약국"
        double latitude = 36.11
        double longitude = 128.11

        def input = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def pharmacy = pharmacyRepository.save(input)
        pharmacyRepositoryService.readOnlyTest(pharmacy.id)

        then:
        def result = pharmacyRepository.findAll()
        result.get(0).pharmacyAddress == address
    }
}
