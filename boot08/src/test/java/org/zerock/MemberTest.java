package org.zerock;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import java.util.Arrays;

@SpringBootTest
@Transactional(readOnly = true)
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    public void insertTest() throws Exception {
        //given
        //when
        for (int i = 1; i <= 100; i++) {
            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" + i);
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
}
