package smartyflip.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smartyflip.accounting.controller.AuthController;
import smartyflip.accounting.controller.UserController;
import smartyflip.accounting.dto.ErrorMessageResponseDto;
import smartyflip.accounting.dto.exceptions.*;
import smartyflip.card.controller.CardController;
import smartyflip.card.service.exceptions.CardNotFoundException;
import smartyflip.card.service.exceptions.InvalidSubscriptionTypeException;
import smartyflip.modules.controller.ModuleController;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.modules.service.exceptions.UnauthorizedAccessException;
import smartyflip.stacks.controller.StackController;
import smartyflip.stacks.service.exceptions.StackAlreadyExistException;
import smartyflip.stacks.service.exceptions.StackNotFoundException;

@RestControllerAdvice(basePackageClasses = {AuthController.class, UserController.class, CardController.class, ModuleController.class, StackController.class})
public class ExceptionApiHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<MessageResponseDto> userNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponseDto("User not found"));
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    ResponseEntity<ErrorMessageResponseDto> emailIsTaken() {
        ErrorMessageResponseDto response = new ErrorMessageResponseDto("Registration failed");
        response.addError("email", "A user with this email already exists");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    ResponseEntity<ErrorMessageResponseDto> usernameIsTaken() {
        ErrorMessageResponseDto response = new ErrorMessageResponseDto("Registration failed");
        response.addError("username", "A user with this username already exists");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<MessageResponseDto> userNotFoundException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponseDto(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsOnLoginException.class)
    ResponseEntity<MessageResponseDto> badCredOnLogin() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDto("Username or password is incorrect"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorMessageResponseDto> handleValidationException(MethodArgumentNotValidException exception) {

        ErrorMessageResponseDto response = new ErrorMessageResponseDto("Validation error");

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.addError(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(PasswordValidationException.class)
    ResponseEntity<MessageResponseDto> passwordValidationException(PasswordValidationException exception) {
        return handleException("Password validation error");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<MessageResponseDto> incorrectJsonFormat() {
        return handleException("Incorrect JSON format");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<MessageResponseDto> illegalArgument() {
        return handleException("Incorrect data format");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    ResponseEntity<MessageResponseDto> missedRequestHeader() {
        return handleException("Missed required request header");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    ResponseEntity<MessageResponseDto> roleNotFound() {
        return handleException("Role not found");
    }

    private ResponseEntity<MessageResponseDto> handleException(String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new MessageResponseDto(message));
    }

    private ResponseEntity<MessageResponseDto> handleException(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDto(message));
    }

    @ExceptionHandler(InvalidSubscriptionTypeException.class)
    ResponseEntity<MessageResponseDto> invalidSubscriptionType(InvalidSubscriptionTypeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDto(e.getMessage()));
    }

    @ExceptionHandler(StackNotFoundException.class)
    ResponseEntity<MessageResponseDto> stackNotFound() {
        return handleException("Stack not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    ResponseEntity<MessageResponseDto> unauthorizedAccess(UnauthorizedAccessException e) {
        return handleException(e.getMessage());
    }

    @ExceptionHandler(CardNotFoundException.class)
    ResponseEntity<MessageResponseDto> cardNotFound() {
        return handleException("Card not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    ResponseEntity<MessageResponseDto> moduleNotFound() {
        return handleException("Module not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StackAlreadyExistException.class)
    ResponseEntity<MessageResponseDto> stackAlreadyExists(StackAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDto(e.getMessage()));
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    ResponseEntity<MessageResponseDto> unauthorizedAccess() {
        return handleException("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}