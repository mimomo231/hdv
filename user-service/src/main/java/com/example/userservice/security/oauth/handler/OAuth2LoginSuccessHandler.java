package com.example.userservice.security.oauth.handler;

import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userdatamodel.entity.enumtype.AuthProviderEnum;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.security.oauth.model.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserAccountRepository userAccountRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (Objects.nonNull(authentication)) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

            String username = oauthUser.getEmail();
            Optional<UserAccount> userAccount = userAccountRepo.findByUsername(username);
            String clientName = oauthUser.getClientName();
            UserAccount account;
            if (!userAccount.isPresent()) {
                //register a new user

                String randomPassword = generateCommonLangPassword();
                account = userAccountRepo.save(UserAccount.builder()
                        .authProvider(AuthProviderEnum.findByCode(clientName))
                        .username(username)
                        .password(passwordEncoder.encode(randomPassword))
                        .fName(oauthUser.getName())
                        .role(AccountRoleEnum.ROLE_USER)
                        .build());
            } else {
                //update existing user

                account = userAccountRepo.save(UserAccount.builder()
                        .id(userAccount.get().getId())
                        .authProvider(AuthProviderEnum.findByCode(clientName))
                        .username(username)
                        .password(userAccount.get().getPassword())
                        .fName(oauthUser.getName())
                        .role(AccountRoleEnum.ROLE_USER)
                        .build());

            }

            response.sendRedirect(request.getContextPath() + "/api/user/oauth2/success/" + account.getUsername() + "/" + account.getId() + "/"+ oauthUser.getName());
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private String generateCommonLangPassword() {

        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);

        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
