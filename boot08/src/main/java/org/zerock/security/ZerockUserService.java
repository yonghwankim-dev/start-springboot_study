package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.persistence.MemberRepository;

import java.util.Arrays;

@Service
@Log
public class ZerockUserService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ZerockSecurityUser zerockSecurityUser = memberRepository.findById(username).filter(m -> m != null).map(m -> new ZerockSecurityUser(m)).get();
        return zerockSecurityUser;
    }
}
