package com.restaurantcontroller.restaurantcontroller.mapper;

import com.restaurantcontroller.restaurantcontroller.dto.NewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.ResponseNewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UpdateUserDTO;
import com.restaurantcontroller.restaurantcontroller.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável por converter objetos User para DTOs de resposta
 */
@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * Converte um objeto User para ResponseNewUserDTO
     * @param user Objeto User a ser convertido
     * @return ResponseNewUserDTO com os dados do usuário
     */
    public ResponseNewUserDTO toResponseNewUserDTO(User user) {
        if (user == null) {
            return null;
        }

        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();
        
        // Mapear dados básicos do usuário
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setUserIdentification(user.getUserIdentification());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setProfile(user.getProfile());
        responseDTO.setLastUpdate(user.getLastUpdate());
        
        // Mapear username das credenciais
        if (user.getUserCredentials() != null) {
            responseDTO.setUsername(user.getUserCredentials().getUsername());
        }
        
        // Mapear endereço se existir
        if (user.getAddressUser() != null) {
            responseDTO.setAddress(addressMapper.toAddressDTO(user.getAddressUser()));
        }
        
        return responseDTO;
    }

    /**
     * Atualiza um objeto User existente com dados de um UpdateUserDTO
     * @param existingUser Objeto User existente a ser atualizado
     * @param updateUserDTO Objeto UpdateUserDTO com os novos dados
     */
    public void updateUser(User existingUser, UpdateUserDTO updateUserDTO) {
        if (updateUserDTO == null || existingUser == null) {
            return;
        }
        
        existingUser.setName(updateUserDTO.getName());
        existingUser.setUserIdentification(updateUserDTO.getUserIdentification());
        existingUser.setEmail(updateUserDTO.getEmail());
        existingUser.setProfile(updateUserDTO.getProfile());
        existingUser.setLastUpdate(java.time.ZonedDateTime.now());
    }

    /**
     * Cria um novo objeto User a partir de um NewUserDTO
     * @param newUserDTO Objeto NewUserDTO com os dados do novo usuário
     * @return User criado com os dados básicos (sem endereço e credenciais)
     */
    public User createUser(NewUserDTO newUserDTO) {
        if (newUserDTO == null) {
            return null;
        }
        
        User user = new User();
        user.setName(newUserDTO.getName());
        user.setUserIdentification(newUserDTO.getUserIdentification());
        user.setEmail(newUserDTO.getEmail());
        user.setProfile(newUserDTO.getProfile());
        user.setLastUpdate(java.time.ZonedDateTime.now());
        
        return user;
    }

}
