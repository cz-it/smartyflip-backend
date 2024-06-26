package smartyflip.accounting.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartyflip.accounting.dto.UserLoginRequestDto;
import smartyflip.accounting.dto.UserRegistrationRequestDto;
import smartyflip.accounting.dto.UserResponseDto;
import smartyflip.accounting.service.AuthService;
import smartyflip.accounting.service.UserAccountService;
import smartyflip.utils.MessageResponseDto;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService userAccountService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequestDto data, HttpServletResponse response) {
        UserResponseDto userResponseDto = userAccountService.registerUser(data);
        response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
        response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto data, HttpServletResponse response) {
        UserResponseDto userResponseDto = userAccountService.loginUser(data);
        response.addCookie(authService.createAccessTokenCookie(userResponseDto.getUsername()));
        response.addCookie(authService.createRefreshToken(userResponseDto.getUsername()));
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(HttpServletResponse response) {
        userAccountService.logoutUser().forEach(response::addCookie);
        return ResponseEntity.ok(new MessageResponseDto("Successful logout"));
    }

    @GetMapping("/activation")
    public UserResponseDto activateUserEmail(@NotBlank @RequestParam String key) {
        return userAccountService.activateUserEmail(key);
    }
}
