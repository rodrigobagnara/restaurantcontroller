package com.restaurantcontroller.restaurantcontroller.validation;

import static org.junit.jupiter.api.Assertions.*;

import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniqueEmailValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private UniqueEmailValidator validator;

    @BeforeEach
    void setUp() {
        validator.initialize(null);
    }

    @Test
    void isValid_shouldReturnTrue_whenEmailIsNull() {
        boolean result = validator.isValid(null, context);
        assertTrue(result);
        verifyNoInteractions(userRepository);
    }

    @Test
    void isValid_shouldReturnTrue_whenEmailIsEmpty() {
        boolean result = validator.isValid("", context);
        assertTrue(result);
        verifyNoInteractions(userRepository);
    }

    @Test
    void isValid_shouldReturnTrue_whenEmailIsBlank() {
        boolean result = validator.isValid("   ", context);
        assertTrue(result);
        verifyNoInteractions(userRepository);
    }

    @Test
    void isValid_shouldReturnTrue_whenEmailDoesNotExist() {
        String email = "newuser@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = validator.isValid(email, context);

        assertTrue(result);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void isValid_shouldReturnFalse_whenEmailExists() {
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = validator.isValid(email, context);

        assertFalse(result);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void isValid_shouldReturnTrue_whenRepositoryThrowsException() {
        String email = "error@example.com";
        when(userRepository.existsByEmail(email)).thenThrow(new RuntimeException("Database error"));

        boolean result = validator.isValid(email, context);

        assertTrue(result);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void isValid_shouldTrimEmailBeforeValidation() {
        String email = "  test@example.com  ";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = validator.isValid(email, context);

        assertTrue(result);
        verify(userRepository).existsByEmail(email);
    }
}
