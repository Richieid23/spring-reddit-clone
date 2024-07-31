package id.web.fitrarizki.spring_reddit_clone.controller;

import id.web.fitrarizki.spring_reddit_clone.dto.AuthenticationResponse;
import id.web.fitrarizki.spring_reddit_clone.dto.LoginRequest;
import id.web.fitrarizki.spring_reddit_clone.dto.RegisterRequest;
import id.web.fitrarizki.spring_reddit_clone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successfully", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable("token") String token) {
        authService.accountVerification(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}