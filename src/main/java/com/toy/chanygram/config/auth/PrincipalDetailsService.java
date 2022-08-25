package com.toy.chanygram.config.auth;

import com.toy.chanygram.domain.User;
import com.toy.chanygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. :" + username)
        );

        return new PrincipalDetails(principal);
    }
}
