package com.example.pharmacy.pharmacy.service;

import com.example.pharmacy.pharmacy.entity.Pharmacy;
import com.example.pharmacy.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {

    private final PharmacyRepository pharmacyRepository;

    // self invocation test
    // 원인
    // 같은 클래스 내에서 같은 클래스의 @Transactional이 붙은 메서드를 호출하면 발생
    // proxy 객체를 사용하기 때문에 같은 클래스 내에서 호출하면 proxy 객체를 사용하지 않고 호출된다.
    // 해결방법들
    // 1. 상위 메서드에 @Transactional을 붙인다.
    // 2. 의존성 분리를 한다. -> foo 함수를 별도의 서비스 클래스의 함수로 분리한다.
    // @Transactional
    public void bar(List<Pharmacy> pharmacies) {
        log.info("bar CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacies);
    }

    @Transactional
    public void foo(List<Pharmacy> pharmacies) {
        log.info("foo CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        pharmacies.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error"); // 예외 발생
        });
    }

    // read only test
    @Transactional(readOnly = true)
    public void readOnlyTest(Long id) {
        pharmacyRepository.findById(id).ifPresent(pharmacy ->
                pharmacy.changePharmacyAddress("서울 특별시 강남구 역삼동"));
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyRepositoryService updateAddress] not found error. id: {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }

    public void updateAddressWithoutTransaction(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyRepositoryService updateAddressWithoutTransaction] not found error. id: {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }
}
