package com.restaurantcontroller.restaurantcontroller.mapper;
import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.dto.NewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.ResponseNewUserDTO;
import com.restaurantcontroller.restaurantcontroller.dto.UpdateUserDTO;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import com.restaurantcontroller.restaurantcontroller.model.EProfile;
import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.model.UserCredentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void toResponseNewUserDTO_shouldReturnNull_whenUserIsNull() {
        ResponseNewUserDTO result = userMapper.toResponseNewUserDTO(null);
        assertNull(result);
    }

    @Test
    void toResponseNewUserDTO_shouldConvertUserToDTO() {
        User user = new User();
        user.setName("John Doe");
        user.setUserIdentification("123456789");
        user.setEmail("john@example.com");
        user.setProfile(EProfile.admin);
        user.setLastUpdate(ZonedDateTime.now());

        ResponseNewUserDTO result = userMapper.toResponseNewUserDTO(user);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("123456789", result.getUserIdentification());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(EProfile.admin, result.getProfile());
        assertNotNull(result.getLastUpdate());
    }

    @Test
    void toResponseNewUserDTO_shouldMapUsername_whenCredentialsExist() {
        User user = new User();
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("johndoe");
        user.setUserCredentials(credentials);

        ResponseNewUserDTO result = userMapper.toResponseNewUserDTO(user);

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
    }

    @Test
    void toResponseNewUserDTO_shouldMapAddress_whenAddressExists() {
        User user = new User();
        Address address = new Address();
        AddressDTO addressDTO = new AddressDTO();
        user.setAddressUser(address);

        when(addressMapper.toAddressDTO(address)).thenReturn(addressDTO);

        ResponseNewUserDTO result = userMapper.toResponseNewUserDTO(user);

        assertNotNull(result);
        assertNotNull(result.getAddress());
        verify(addressMapper).toAddressDTO(address);
    }

    @Test
    void updateUser_shouldDoNothing_whenDTOIsNull() {
        User user = new User();
        user.setName("Original Name");
        userMapper.updateUser(user, null);
        assertEquals("Original Name", user.getName());
    }

    @Test
    void updateUser_shouldDoNothing_whenUserIsNull() {
        UpdateUserDTO dto = new UpdateUserDTO();
        userMapper.updateUser(null, dto);
        // No exception should be thrown
    }

    @Test
    void updateUser_shouldUpdateExistingUser() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("old@example.com");

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setName("New Name");
        dto.setUserIdentification("987654321");
        dto.setEmail("new@example.com");
        dto.setProfile(EProfile.client);

        ZonedDateTime beforeUpdate = ZonedDateTime.now();
        userMapper.updateUser(user, dto);

        assertEquals("New Name", user.getName());
        assertEquals("987654321", user.getUserIdentification());
        assertEquals("new@example.com", user.getEmail());
        assertEquals(EProfile.client, user.getProfile());
        assertNotNull(user.getLastUpdate());
        assertTrue(user.getLastUpdate().isAfter(beforeUpdate) || user.getLastUpdate().isEqual(beforeUpdate));
    }

    @Test
    void createUser_shouldReturnNull_whenDTOIsNull() {
        User result = userMapper.createUser(null);
        assertNull(result);
    }

    @Test
    void createUser_shouldCreateNewUser() {
        NewUserDTO dto = new NewUserDTO();
        dto.setName("Jane Smith");
        dto.setUserIdentification("111222333");
        dto.setEmail("jane@example.com");
        dto.setProfile(EProfile.owner);

        ZonedDateTime beforeCreation = ZonedDateTime.now();
        User result = userMapper.createUser(dto);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals("111222333", result.getUserIdentification());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals(EProfile.owner, result.getProfile());
        assertNotNull(result.getLastUpdate());
        assertTrue(result.getLastUpdate().isAfter(beforeCreation) || result.getLastUpdate().isEqual(beforeCreation));
    }
}
