package org.zerock;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import java.util.Arrays;
import java.util.Optional;

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

    @Test
    public void readTest() throws Exception{
        //given
        Member member = new Member();
        MemberRole memberRole = new MemberRole();
        member.setUid("user1");
        member.setUpw("pw1");
        member.setUname("사용자1");
        memberRole.setRoleName("MANAGER");
        member.setRoles(Arrays.asList(memberRole));
        memberRepository.save(member);
        //when
        Optional<Member> result = memberRepository.findById("user1");
        result.ifPresent(m->System.out.println("member" + m));
        //then
    }
}
