package com.example.userservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.security.model.UserAccountInfo;
import com.example.security.model.UserPrincipal;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount userAccount = userAccountRepo.findByUsername(username).orElseThrow(
                () -> new NotFoundException(
                        String.format("loadUserByUsername error: Not found User Account with username: %s", username)
                )
        );

        return UserPrincipal.build(UserAccountInfo.builder()
                        .userId(userAccount.getId())
                        .username(userAccount.getUsername())
                        .password(userAccount.getPassword())
                        .firstName(userAccount.getFName())
                        .lastName(userAccount.getLName())
                        .roles(Collections.singletonList(userAccount.getRole().name()))
                        .phoneNum(userAccount.getPhoneNum())
                .build());
    }
}
