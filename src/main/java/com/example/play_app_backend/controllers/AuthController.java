package com.example.play_app_backend.controllers;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.example.play_app_backend.TokenResponse;

@RestController
public class AuthController {

    private final RestTemplate restTemplate;
    //constructor
    public AuthController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    //method triggered when user redirects from 3-rd party spotify!
    @GetMapping("/callback")
    public String handleCallBack(@RequestParam("code") String authCode){
        System.out.println("authCode: " + authCode);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("code", authCode);
        body.add("redirect_uri", "http://localhost:8090/callback");
        body.add("client_id", "a45128e98e524e9db967116b0cccfddb");
        body.add("client_secret", "ef13b0362de94b8b845e00ca8b9faec4");
        //combine headers and body into one single http post request instance: this will be sent to Spotify to get access token!
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, requestEntity, TokenResponse.class);

        TokenResponse tokenResponse = response.getBody();

        System.out.println("tokenResponse: " + tokenResponse);

        return "Authorization Successful!";
    }
}
