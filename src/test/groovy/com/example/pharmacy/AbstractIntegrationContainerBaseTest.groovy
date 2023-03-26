package com.example.pharmacy;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import spock.lang.Specification;

// 통합 테스트를 위한 추상 클래스
@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {

    static final GenericContainer MY_REDIS_CONTAINER

    static {
        // docker 내부 포트 6379 지정해주면 얘가 알아서 host 포트를 충돌하지 않는 포트로 매핑해줌
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379)

        // 실행을 시켜주고
        MY_REDIS_CONTAINER.start()

        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        // 도커 내부 6379 포트와 매핑된 호스트 포트와 spring boot에 연결하여 redis 사용 가능하도록 설정
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }
}
