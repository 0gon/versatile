package org.gon;

import org.gon.domain.member.MemberRepository;
import org.gon.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@SpringBootTest
@Transactional
class GonApplicationTests {

    @TestConfiguration
    static class TestClockConfig {

        @Primary
        @Bean
        public Clock fixedClock() {
            return Clock.fixed(java.time.Instant.parse("2024-01-01T00:00:00Z"), java.time.ZoneOffset.UTC);
        }
    }

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        Member save = memberRepository.save(new Member("test", "test"));
        Member save1 = memberRepository.save(new Member("test2", "test2"));
        System.out.println(TestMain.prettyBinary(save.getId()));
        System.out.println(TestMain.prettyBinary(save1.getId()));
    }

}
