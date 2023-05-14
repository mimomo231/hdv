package com.example.userservice.controller;

import com.example.api.controller.BaseController;
import com.example.security.payload.UserToken;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.RegisterRequest;
import com.example.userservice.payload.request.ResetPasswordRequest;
import com.example.userservice.payload.response.RegisterResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserAccountController extends BaseController {

    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello")
    ResponseEntity<?> helloWorld() {

        String username = getPrincipal();
        Long userId = getOriginalId();

        return ResponseEntity.ok(userService.hello(username, userId));
    }

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(
            @RequestBody @Valid LoginRequest request
    ) {
        return userService.login(request);
    }

    @GetMapping("/oauth2/success/{username}/{userId}/{name}")
    public ResponseEntity<?> oauthSuccess(
            @PathVariable("username") String username,
            @PathVariable("userId") Long userId,
            @PathVariable("name") String name
    ) {

        UserToken userToken = userService.genOauthToken(username, userId, name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserToken> entity = new HttpEntity<>(userToken, headers);
        String url = "http://localhost:8009/user/sign-in/oauth";
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @PostMapping("/register")
    ResponseEntity<RegisterResponse> addUser(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(userService.addUser(request));
    }

    @PostMapping("/reset-password")
    ResponseEntity<String> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Reset password completed");
    }
}
