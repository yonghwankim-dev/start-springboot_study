package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.domain.Member;
import org.zerock.persistence.MemberRepository;

@Log
@Controller
public class MemberController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    @Transactional
    @PostMapping("/member/join")
    public String joinPost(@ModelAttribute("member") Member member){
        log.info("MEMBER: " + member);
        String encryptPw = passwordEncoder.encode(member.getUpw());
        log.info("en: " + encryptPw);
        member.setUpw(encryptPw);
        memberRepository.save(member);
        return "/member/joinResult";
    }
}
