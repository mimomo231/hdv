package com.example.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.api.exception.ForbiddenException;
import com.example.api.exception.NotFoundException;
import com.example.security.common.JwtTokenCommon;
import com.example.security.model.UserAccountInfo;
import com.example.security.model.UserPrincipal;
import com.example.security.payload.UserToken;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userdatamodel.entity.enumtype.AuthProviderEnum;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenCommon jwtTokenCommon;

    @Value("${ecobook.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecobook.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public ResponseEntity<?> hello(String username, Long userId) {

        return ResponseEntity.ok("Hello " + username + " with id = " + userId);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        UserAccount existUsername = userAccountRepo.findByUsername(request.getUsername()).orElseThrow(
                () -> new NotFoundException(
                        String.format("login error: Not found User Account with username: %s", request.getUsername())
                )
        );

        // Authenticate via authentication manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (Objects.nonNull(existUsername) && Objects.nonNull(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

            return ResponseEntity.ok(genTokenInfo(userDetails));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.name());
    }

    @Override
    public RegisterResponse addUser(RegisterRequest request) {

        RegisterResponse response = new RegisterResponse();

        List<UserAccount> userAccounts = userAccountRepo.findAllByUsernameOrPhoneNum(
                Collections.singletonList(request.getUsername()), Collections.singletonList(request.getPhoneNum())
        );

        if(userAccounts.isEmpty()) {
            log.info("Saving new user {} to the database", request.getLName());
            userAccountRepo.save(UserAccount.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .fName(request.getFName())
                    .lName(request.getLName())
                    .address(request.getAddress())
                    .role(AccountRoleEnum.ROLE_USER)
                    .phoneNum(request.getPhoneNum())
                    .authProvider(AuthProviderEnum.LOCAL)
                    .build());
        }
        else {
            log.info("User Account already exist");
            userAccounts.forEach(
                    u -> {
                        if (u.getUsername().equals(request.getUsername()) && u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistUsername(1);
                            response.setExistPhoneNumber(1);
                        } else if (u.getUsername().equals(request.getUsername())) {
                            response.setExistUsername(1);
                            response.setExistPhoneNumber(0);
                        } else if (u.getPhoneNum().equals(request.getPhoneNum())) {
                            response.setExistUsername(0);
                            response.setExistPhoneNumber(1);
                        }
                    }
            );
        }

        return response;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        UserAccount userAccount = userAccountRepo.findByPhoneNum(request.getPhoneNum()).orElseThrow(
                () -> new NotFoundException(
                        String.format("resetPassword error: Not found User Account with phone_number: %s", request.getPhoneNum())
                )
        );

        if(Objects.nonNull(userAccount)) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            userAccountRepo.save(userAccount);
        } else {
            log.info("Phone number exists");
        }
    }

    @Override
    public UserToken genOauthToken(String username, Long userId, String name) {

        UserPrincipal principal = UserPrincipal.build(
                UserAccountInfo.builder()
                        .userId(userId)
                        .username(username)
                        .roles(Collections.singletonList(AccountRoleEnum.ROLE_USER.name()))
                        .build()
        );
        UserToken token = genTokenInfo(principal);
        token.setFirstName(name);

        return token;
    }

    private UserToken genTokenInfo(UserPrincipal principal) {

        String accessToken = jwtTokenCommon.generateJwtToken(principal);
        String refreshToken = jwtTokenCommon.generateJwtRefreshToken(principal);

        Set<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return UserToken.builder()
                .accountId(principal.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .listRole(roles)
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .build();
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                UserAccount user = userAccountRepo.findByUsername(username).orElseThrow(
                        () -> new NotFoundException(
                                String.format("refreshToken error: Not found User Account with username: %s", username)
                        )
                );
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", Collections.singletonList(user.getRole()))
                        .sign(algorithm);

                return ResponseEntity.ok(
                        UserToken.builder()
                                .accountId(user.getId())
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .listRole(Collections.singleton(user.getRole().name()))
                                .firstName(user.getFName())
                                .lastName(user.getLName())
                                .build()
                );

            }catch (Exception exception) {
                throw new ForbiddenException("error" + exception.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.name());
    }
}