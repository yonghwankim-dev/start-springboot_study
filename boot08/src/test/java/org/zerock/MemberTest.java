package org.zerock;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;
import org.zerock.security.ZerockUserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
@ActiveProfiles("prod")
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ZerockUserService zerockUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest() throws Exception {
        //given
        //when
        for (int i = 1; i <= 100; i++) {
            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw(passwordEncoder.encode("pw" + i));
            member.setUname("사용자" + i);

            MemberRole memberRole = new MemberRole();

            if (i <= 80) {
                memberRole.setRoleName("BASIC");
            } else if (i <= 90) {
                memberRole.setRoleName("MANAGER");
            } else {
                memberRole.setRoleName("ADMIN");
            }

            member.setRoles(Arrays.asList(memberRole));
            memberRepository.save(member);
            //then
        }
    }


    @Test
    @Transactional(readOnly = true)
    public void readTest() throws Exception{
        //given

        //when
        Optional<Member> result = memberRepository.findById("user85");
        result.ifPresent(m->System.out.println("member" + m));
        //then
    }

    @Test
    @Transactional(readOnly = true)
    public void passwordEncoderMatchesTest() throws Exception{
        //given
        UserDetails user99 = zerockUserService.loadUserByUsername("user99");
        //when
        boolean pw99 = passwordEncoder.matches("pw99", user99.getPassword());
        //then
        assertThat(pw99).isTrue();
    }

}
