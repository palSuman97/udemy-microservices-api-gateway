package com.codeandlearn.APIGateway.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeandlearn.APIGateway.model.OktaAuthenticationResponse;

@RestController
@RequestMapping("/authenticate")
public class OktaAuthenticationController {

	@GetMapping("/login")
	public ResponseEntity<OktaAuthenticationResponse> login(@AuthenticationPrincipal OidcUser oidcUser, Model model,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
		
				OktaAuthenticationResponse oktaAuthenticationResponse = OktaAuthenticationResponse.builder()
								.userId(oidcUser.getEmail())
								.accessToken(client.getAccessToken().getTokenValue())
								.refreshToken(client.getRefreshToken().getTokenValue())
								.expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
								.authorityList(oidcUser.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
								.build();
			return new ResponseEntity<OktaAuthenticationResponse>(oktaAuthenticationResponse, HttpStatus.OK);	
	}
	
}
