package com.restaurantcontroller.restaurantcontroller.service;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.dto.NewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.ResponseNewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UpdateUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UserCredentialsDTO;
import com.restaurantcontroller.restaurantcontroller.exception.EmailAlreadyExistsException;
import com.restaurantcontroller.restaurantcontroller.exception.UserIdentificationAlreadyExistsException;
import com.restaurantcontroller.restaurantcontroller.mapper.AddressMapper;
import com.restaurantcontroller.restaurantcontroller.mapper.UserMapper;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.model.UserCredentials;
import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AddressMapper addressMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;

    /**
     * Cria um novo usuário no sistema
     * @param newUserDTO DTO com os dados do novo usuário
     * @return ResponseNewUserDTO com os dados do usuário criado
     * @throws EmailAlreadyExistsException se o email já estiver em uso
     * @throws UserIdentificationAlreadyExistsException se a identificação do usuário já estiver em uso
     * @throws IllegalArgumentException se os dados fornecidos forem inválidos
     * @throws RuntimeException se ocorrer um erro interno durante a criação do usuário
     */
    public ResponseNewUserDTO createUser(NewUserDTO newUserDTO) {
        try {
            // Validar dados de entrada
            if (newUserDTO == null) 
                throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
            
            if (newUserDTO.getEmail() == null || newUserDTO.getEmail().trim().isEmpty()) 
                throw new IllegalArgumentException("Email é obrigatório");
            
            
            if (newUserDTO.getUserIdentification() == null || newUserDTO.getUserIdentification().trim().isEmpty()) 
                throw new IllegalArgumentException("Identificação do usuário é obrigatória");
            
            if (newUserDTO.getName() == null || newUserDTO.getName().trim().isEmpty()) 
                throw new IllegalArgumentException("Nome é obrigatório");
            
            // Verificar se o email já existe
            if (userRepository.existsByEmail(newUserDTO.getEmail())) 
                throw new EmailAlreadyExistsException(newUserDTO.getEmail());
            
            // Verificar se a identificação do usuário já existe
            if (userRepository.existsByUserIdentification(newUserDTO.getUserIdentification())) 
                throw new UserIdentificationAlreadyExistsException(newUserDTO.getUserIdentification());
            
            // Criar o endereço usando o mapper
            Address address = new Address();
            AddressDTO addressDTO = newUserDTO.getAddress();

            if (addressDTO != null) 
                address = addressMapper.toAddress(addressDTO);
                
            address.setLastUpdate(ZonedDateTime.now());

            
            // Criar as credenciais do usuário
            UserCredentials userCredentials = new UserCredentials();
            UserCredentialsDTO credentialsDTO = newUserDTO.getCredentials();

            if (credentialsDTO != null) {
                if (credentialsDTO.getUsername() == null || credentialsDTO.getUsername().trim().isEmpty()) 
                    throw new IllegalArgumentException("Username é obrigatório");
                
                if (credentialsDTO.getPassword() == null || credentialsDTO.getPassword().trim().isEmpty()) 
                    throw new IllegalArgumentException("Senha é obrigatória");
                
                userCredentials.setUsername(credentialsDTO.getUsername());
                // Criptografar a senha
                userCredentials.setPassword(passwordEncoder.encode(credentialsDTO.getPassword()));
                
            } else {
                throw new IllegalArgumentException("Credenciais são obrigatórias");
            }

            userCredentials.setLastUpdate(ZonedDateTime.now());

            // Criar o usuário usando o mapper
            User user = userMapper.createUser(newUserDTO);
            user.setAddressUser(address);
            user.setUserCredentials(userCredentials);
            
            // Salvar o usuário no banco de dados
            User savedUser = userRepository.save(user);
            
            // Converter para DTO de resposta usando o mapper
            return userMapper.toResponseNewUserDTO(savedUser);
            
        } catch (EmailAlreadyExistsException | UserIdentificationAlreadyExistsException | IllegalArgumentException ex) {
            // Re-lançar exceções específicas que já são tratadas pelo GlobalExceptionHandler
            throw ex;
        } catch (Exception ex) {
            System.err.println("(UserService.createUser) Error: " + ex.getMessage());
            // Capturar qualquer outra exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao criar usuário: " + ex.getMessage(), ex);
        }
    }

    /**
     * Lista todos os usuários do sistema
     * @return Lista de ResponseNewUserDTO com todos os usuários
     * @throws RuntimeException se ocorrer um erro interno durante a recuperação dos usuários
     */
    public List<ResponseNewUserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(userMapper::toResponseNewUserDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            System.err.println("(UserService.getAllUsers) Error: " + ex.getMessage());
            // Capturar qualquer exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao recuperar usuários: " + ex.getMessage(), ex);
        }
    }

    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário a ser buscado
     * @return Optional<ResponseNewUserDTO> com os dados do usuário se encontrado
     * @throws RuntimeException se ocorrer um erro interno durante a busca
     */
    public Optional<ResponseNewUserDTO> getUserById(Long id) {
        try {
            if (id == null) 
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");
            
            // Buscar o usuário no banco de dados
            Optional<User> user = userRepository.findById(id);

            // Converter para DTO de resposta usando o mapper
            return user.map(userMapper::toResponseNewUserDTO);

        } catch (IllegalArgumentException ex) {
            throw ex; // Re-lançar exceções de validação
        } catch (Exception ex) {
            System.err.println("(UserService.getUserById) Error: " + ex.getMessage());
            // Capturar qualquer exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao buscar usuário: " + ex.getMessage(), ex);
        }
    }

    /**
     * Busca usuários pelo nome (busca parcial, case insensitive)
     * @param name Nome ou parte do nome a ser buscado
     * @return Lista de ResponseNewUserDTO com os usuários encontrados
     * @throws IllegalArgumentException se o nome fornecido for nulo ou vazio
     * @throws RuntimeException se ocorrer um erro interno durante a busca
     */
    public List<ResponseNewUserDTO> searchUsersByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
            }
            
            // Buscar usuários no banco de dados com busca parcial e case insensitive
            List<User> users = userRepository.findByNameContainingIgnoreCase(name.trim());
            
            // Converter para DTO de resposta usando o mapper
            return users.stream().map(userMapper::toResponseNewUserDTO).collect(Collectors.toList());
            
        } catch (IllegalArgumentException ex) {
            throw ex; // Re-lançar exceções de validação
        } catch (Exception ex) {
            System.err.println("(UserService.searchUsersByName) Error: " + ex.getMessage());
            // Capturar qualquer exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao buscar usuários por nome: " + ex.getMessage(), ex);
        }
    }

    /**
     * Atualiza um usuário existente no sistema (sem alterar credenciais)
     * @param id ID do usuário a ser atualizado
     * @param updateUserDTO DTO com os dados atualizados do usuário
     * @return ResponseNewUserDTO com os dados do usuário atualizado
     * @throws IllegalArgumentException se o ID fornecido for nulo ou se os dados forem inválidos
     * @throws EmailAlreadyExistsException se o email já estiver em uso por outro usuário
     * @throws UserIdentificationAlreadyExistsException se a identificação já estiver em uso por outro usuário
     * @throws RuntimeException se o usuário não for encontrado ou ocorrer um erro interno
     */
    public ResponseNewUserDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");
            }
            
            if (updateUserDTO == null) {
                throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
            }
            
            // Buscar o usuário existente
            User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            // Verificar se o email já existe em outro usuário
            if (!existingUser.getEmail().equals(updateUserDTO.getEmail()) && 
                userRepository.existsByEmail(updateUserDTO.getEmail())) {
                throw new EmailAlreadyExistsException(updateUserDTO.getEmail());
            }
            
            // Verificar se a identificação já existe em outro usuário
            if (!existingUser.getUserIdentification().equals(updateUserDTO.getUserIdentification()) && 
                userRepository.existsByUserIdentification(updateUserDTO.getUserIdentification())) {
                throw new UserIdentificationAlreadyExistsException(updateUserDTO.getUserIdentification());
            }
            
            // Atualizar dados básicos do usuário usando o mapper
            userMapper.updateUser(existingUser, updateUserDTO);
            
            // Atualizar endereço se fornecido
            if (updateUserDTO.getAddress() != null) {
                Address address = existingUser.getAddressUser();
                if (address == null) {
                    address = new Address();
                    existingUser.setAddressUser(address);
                }
                
                AddressDTO addressDTO = updateUserDTO.getAddress();

                // Usar o mapper para atualizar o endereço existente
                addressMapper.updateAddress(address, addressDTO);
            }
            
            // Salvar as alterações no banco de dados
            User updatedUser = userRepository.save(existingUser);
            
            // Converter para DTO de resposta usando o mapper
            return userMapper.toResponseNewUserDTO(updatedUser);
            
        } catch (EmailAlreadyExistsException | UserIdentificationAlreadyExistsException | IllegalArgumentException ex) {
            // Re-lançar exceções específicas que já são tratadas pelo GlobalExceptionHandler
            throw ex;
        } catch (Exception ex) {
            System.err.println("(UserService.updateUser) Error: " + ex.getMessage());
            // Capturar qualquer outra exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao atualizar usuário: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deleta um usuário do sistema pelo ID
     * @param id ID do usuário a ser deletado
     * @return Optional<ResponseNewUserDTO> com os dados do usuário deletado, ou empty se não encontrado
     * @throws IllegalArgumentException se o ID fornecido for nulo
     * @throws RuntimeException se ocorrer um erro interno durante a exclusão
     */
    public Optional<ResponseNewUserDTO> deleteUser(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");
            }
            
            // Buscar o usuário antes de deletar
            Optional<User> user = userRepository.findById(id);
            
            if (user.isPresent()) {
                // Converter para DTO antes de deletar
                ResponseNewUserDTO userDTO = userMapper.toResponseNewUserDTO(user.get());
                
                // Deletar o usuário do banco de dados
                userRepository.deleteById(id);
                
                return Optional.of(userDTO);
            } else {
                return Optional.empty();
            }
            
        } catch (IllegalArgumentException ex) {
            throw ex; // Re-lançar exceções de validação
        } catch (Exception ex) {
            System.err.println("(UserService.deleteUser) Error: " + ex.getMessage());
            // Capturar qualquer exceção e lançar como RuntimeException
            throw new RuntimeException("Erro interno ao deletar usuário: " + ex.getMessage(), ex);
        }
    }

}
