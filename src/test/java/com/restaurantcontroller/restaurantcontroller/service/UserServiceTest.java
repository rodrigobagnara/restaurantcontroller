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
import com.restaurantcontroller.restaurantcontroller.model.EProfile;
import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldThrowException_whenUserDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void createUser_shouldThrowException_whenEmailIsNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenEmailIsEmpty() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenUserIdentificationIsNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Identificação do usuário é obrigatória", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenNameIsNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenEmailAlreadyExists() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("existing@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(dto));
        verify(userRepository).existsByEmail(dto.getEmail());
    }

    @Test
    void createUser_shouldThrowException_whenUserIdentificationAlreadyExists() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(true);

        assertThrows(UserIdentificationAlreadyExistsException.class,
                () -> userService.createUser(dto));
    }

    @Test
    void createUser_shouldThrowException_whenCredentialsAreNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");
        dto.setCredentials(null);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Credenciais são obrigatórias", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenUsernameIsNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");
        UserCredentialsDTO credentialsDTO = new UserCredentialsDTO();
        credentialsDTO.setUsername(null);
        credentialsDTO.setPassword("password123");
        dto.setCredentials(credentialsDTO);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Username é obrigatório", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenPasswordIsNull() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");
        UserCredentialsDTO credentialsDTO = new UserCredentialsDTO();
        credentialsDTO.setUsername("johndoe");
        credentialsDTO.setPassword(null);
        dto.setCredentials(credentialsDTO);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));
        assertEquals("Senha é obrigatória", exception.getMessage());
    }

    @Test
    void createUser_shouldCreateUserSuccessfully() {
        NewUserDTO dto = new NewUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123456789");
        dto.setName("John Doe");
        dto.setProfile(EProfile.client);

        UserCredentialsDTO credentialsDTO = new UserCredentialsDTO();
        credentialsDTO.setUsername("johndoe");
        credentialsDTO.setPassword("password123");
        dto.setCredentials(credentialsDTO);

        AddressDTO addressDTO = new AddressDTO();
        dto.setAddress(addressDTO);

        User user = new User();
        User savedUser = new User();
        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();
        Address address = new Address();

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(false);
        when(addressMapper.toAddress(addressDTO)).thenReturn(address);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userMapper.createUser(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponseNewUserDTO(savedUser)).thenReturn(responseDTO);

        ResponseNewUserDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(userRepository).existsByEmail(dto.getEmail());
        verify(userRepository).existsByUserIdentification(dto.getUserIdentification());
        verify(addressMapper).toAddress(addressDTO);
        verify(passwordEncoder).encode("password123");
        verify(userMapper).createUser(dto);
        verify(userRepository).save(user);
        verify(userMapper).toResponseNewUserDTO(savedUser);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        ResponseNewUserDTO dto1 = new ResponseNewUserDTO();
        ResponseNewUserDTO dto2 = new ResponseNewUserDTO();

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseNewUserDTO(user1)).thenReturn(dto1);
        when(userMapper.toResponseNewUserDTO(user2)).thenReturn(dto2);

        List<ResponseNewUserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verify(userMapper, times(2)).toResponseNewUserDTO(any(User.class));
    }

    @Test
    void getUserById_shouldThrowException_whenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(null));
        assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void getUserById_shouldReturnEmpty_whenUserNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ResponseNewUserDTO> result = userService.getUserById(id);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserFound() {
        Long id = 1L;
        User user = new User();
        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toResponseNewUserDTO(user)).thenReturn(responseDTO);

        Optional<ResponseNewUserDTO> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(responseDTO, result.get());
        verify(userRepository).findById(id);
        verify(userMapper).toResponseNewUserDTO(user);
    }

    @Test
    void searchUsersByName_shouldThrowException_whenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.searchUsersByName(null));
        assertEquals("Nome não pode ser nulo ou vazio", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void searchUsersByName_shouldThrowException_whenNameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.searchUsersByName("   "));
        assertEquals("Nome não pode ser nulo ou vazio", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void searchUsersByName_shouldReturnUsers() {
        String name = "John";
        User user1 = new User();
        List<User> users = Arrays.asList(user1);
        ResponseNewUserDTO dto1 = new ResponseNewUserDTO();

        when(userRepository.findByNameContainingIgnoreCase(name)).thenReturn(users);
        when(userMapper.toResponseNewUserDTO(user1)).thenReturn(dto1);

        List<ResponseNewUserDTO> result = userService.searchUsersByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findByNameContainingIgnoreCase(name);
        verify(userMapper).toResponseNewUserDTO(user1);
    }

    @Test
    void updateUser_shouldThrowException_whenIdIsNull() {
        UpdateUserDTO dto = new UpdateUserDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(null, dto));
        assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
    }

    @Test
    void updateUser_shouldThrowException_whenDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(1L, null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void updateUser_shouldThrowException_whenUserNotFound() {
        Long id = 1L;
        UpdateUserDTO dto = new UpdateUserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUser(id, dto));
        assertEquals("Erro interno ao atualizar usuário: Usuário não encontrado", exception.getMessage());
        verify(userRepository).findById(id);
    }

    @Test
    void updateUser_shouldThrowException_whenEmailAlreadyExists() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.setEmail("old@example.com");

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setEmail("new@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.updateUser(id, dto));
    }

    @Test
    void updateUser_shouldThrowException_whenUserIdentificationAlreadyExists() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setUserIdentification("123");

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("456");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUserIdentification(dto.getUserIdentification())).thenReturn(true);

        assertThrows(UserIdentificationAlreadyExistsException.class,
                () -> userService.updateUser(id, dto));
    }

    @Test
    void updateUser_shouldUpdateSuccessfully() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setUserIdentification("123");
        existingUser.setAddressUser(null);

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123");

        User updatedUser = new User();
        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toResponseNewUserDTO(updatedUser)).thenReturn(responseDTO);

        ResponseNewUserDTO result = userService.updateUser(id, dto);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(userMapper).updateUser(existingUser, dto);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_shouldUpdateWithAddress() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setUserIdentification("123");
        Address existingAddress = new Address();
        existingUser.setAddressUser(existingAddress);

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setEmail("test@example.com");
        dto.setUserIdentification("123");
        AddressDTO addressDTO = new AddressDTO();
        dto.setAddress(addressDTO);

        User updatedUser = new User();
        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toResponseNewUserDTO(updatedUser)).thenReturn(responseDTO);

        ResponseNewUserDTO result = userService.updateUser(id, dto);

        assertNotNull(result);
        verify(addressMapper).updateAddress(existingAddress, addressDTO);
    }

    @Test
    void deleteUser_shouldThrowException_whenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(null));
        assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void deleteUser_shouldReturnEmpty_whenUserNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ResponseNewUserDTO> result = userService.deleteUser(id);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(id);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void deleteUser_shouldDeleteAndReturnUser() {
        Long id = 1L;
        User user = new User();
        ResponseNewUserDTO responseDTO = new ResponseNewUserDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toResponseNewUserDTO(user)).thenReturn(responseDTO);

        Optional<ResponseNewUserDTO> result = userService.deleteUser(id);

        assertTrue(result.isPresent());
        assertEquals(responseDTO, result.get());
        verify(userRepository).findById(id);
        verify(userRepository).deleteById(id);
        verify(userMapper).toResponseNewUserDTO(user);
    }
}
