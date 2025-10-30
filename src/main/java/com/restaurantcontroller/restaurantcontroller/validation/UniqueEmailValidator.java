package com.restaurantcontroller.restaurantcontroller.validation;

import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // Inicialização se necessário
    }
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Deixar outras validações tratarem campos nulos/vazios
        }
        
        try {
            return !userRepository.existsByEmail(email);
        } catch (Exception e) {
            // Log do erro para debug
            System.err.println("Erro no UniqueEmailValidator: " + e.getMessage());
            e.printStackTrace();
            // Retorna true para não bloquear a validação em caso de erro
            return true;
        }
    }
}
