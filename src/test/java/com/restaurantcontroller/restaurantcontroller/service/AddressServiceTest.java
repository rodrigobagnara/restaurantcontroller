package com.restaurantcontroller.restaurantcontroller.service;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.mapper.AddressMapper;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.repository.AddressRepository;
import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    @Test
    void getAddressByUserId_shouldThrowException_whenUserIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressByUserId(null));
        assertEquals("ID do usuário deve ser válido", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void getAddressByUserId_shouldThrowException_whenUserIdIsZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressByUserId(0L));
        assertEquals("ID do usuário deve ser válido", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void getAddressByUserId_shouldThrowException_whenUserIdIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressByUserId(-1L));
        assertEquals("ID do usuário deve ser válido", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void getAddressByUserId_shouldThrowException_whenUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressByUserId(userId));
        assertEquals("Usuário com ID 1 não encontrado", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void getAddressByUserId_shouldReturnEmpty_whenUserHasNoAddress() {
        Long userId = 1L;
        User user = new User();
        user.setAddressUser(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<AddressDTO> result = addressService.getAddressByUserId(userId);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(userId);
        verifyNoInteractions(addressMapper);
    }

    @Test
    void getAddressByUserId_shouldReturnAddressDTO_whenUserHasAddress() {
        Long userId = 1L;
        Address address = new Address();
        User user = new User();
        user.setAddressUser(address);
        AddressDTO addressDTO = new AddressDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressMapper.toAddressDTO(address)).thenReturn(addressDTO);

        Optional<AddressDTO> result = addressService.getAddressByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(addressDTO, result.get());
        verify(userRepository).findById(userId);
        verify(addressMapper).toAddressDTO(address);
    }

    @Test
    void getAddressById_shouldThrowException_whenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressById(null));
        assertEquals("ID do endereço deve ser válido", exception.getMessage());
        verifyNoInteractions(addressRepository);
    }

    @Test
    void getAddressById_shouldThrowException_whenIdIsZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressById(0L));
        assertEquals("ID do endereço deve ser válido", exception.getMessage());
        verifyNoInteractions(addressRepository);
    }

    @Test
    void getAddressById_shouldThrowException_whenIdIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.getAddressById(-1L));
        assertEquals("ID do endereço deve ser válido", exception.getMessage());
        verifyNoInteractions(addressRepository);
    }

    @Test
    void getAddressById_shouldReturnEmpty_whenAddressNotFound() {
        Long id = 1L;
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        Optional<AddressDTO> result = addressService.getAddressById(id);

        assertTrue(result.isEmpty());
        verify(addressRepository).findById(id);
        verifyNoInteractions(addressMapper);
    }

    @Test
    void getAddressById_shouldReturnAddressDTO_whenAddressFound() {
        Long id = 1L;
        Address address = new Address();
        AddressDTO addressDTO = new AddressDTO();

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        when(addressMapper.toAddressDTO(address)).thenReturn(addressDTO);

        Optional<AddressDTO> result = addressService.getAddressById(id);

        assertTrue(result.isPresent());
        assertEquals(addressDTO, result.get());
        verify(addressRepository).findById(id);
        verify(addressMapper).toAddressDTO(address);
    }

    @Test
    void updateAddressByUserId_shouldThrowException_whenUserIdIsNull() {
        AddressDTO addressDTO = new AddressDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddressByUserId(null, addressDTO));
        assertEquals("ID do usuário deve ser válido", exception.getMessage());
    }

    @Test
    void updateAddressByUserId_shouldThrowException_whenUserIdIsInvalid() {
        AddressDTO addressDTO = new AddressDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddressByUserId(0L, addressDTO));
        assertEquals("ID do usuário deve ser válido", exception.getMessage());
    }

    @Test
    void updateAddressByUserId_shouldThrowException_whenAddressDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddressByUserId(1L, null));
        assertEquals("Dados do endereço não podem ser nulos", exception.getMessage());
    }

    @Test
    void updateAddressByUserId_shouldThrowException_whenUserNotFound() {
        Long userId = 1L;
        AddressDTO addressDTO = new AddressDTO();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddressByUserId(userId, addressDTO));
        assertEquals("Usuário com ID 1 não encontrado", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateAddressByUserId_shouldThrowException_whenUserHasNoAddress() {
        Long userId = 1L;
        User user = new User();
        user.setAddressUser(null);
        AddressDTO addressDTO = new AddressDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddressByUserId(userId, addressDTO));
        assertEquals("Usuário não possui endereço cadastrado", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateAddressByUserId_shouldUpdateAndReturnAddressDTO() {
        Long userId = 1L;
        Address address = new Address();
        User user = new User();
        user.setAddressUser(address);
        AddressDTO addressDTO = new AddressDTO();
        AddressDTO updatedAddressDTO = new AddressDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toAddressDTO(address)).thenReturn(updatedAddressDTO);

        AddressDTO result = addressService.updateAddressByUserId(userId, addressDTO);

        assertEquals(updatedAddressDTO, result);
        verify(userRepository).findById(userId);
        verify(addressMapper).updateAddress(address, addressDTO);
        verify(addressRepository).save(address);
        verify(addressMapper).toAddressDTO(address);
    }

    @Test
    void updateAddress_shouldThrowException_whenIdIsNull() {
        AddressDTO addressDTO = new AddressDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(null, addressDTO));
        assertEquals("ID do endereço deve ser válido", exception.getMessage());
    }

    @Test
    void updateAddress_shouldThrowException_whenIdIsInvalid() {
        AddressDTO addressDTO = new AddressDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(-1L, addressDTO));
        assertEquals("ID do endereço deve ser válido", exception.getMessage());
    }

    @Test
    void updateAddress_shouldThrowException_whenAddressDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(1L, null));
        assertEquals("Dados do endereço não podem ser nulos", exception.getMessage());
    }

    @Test
    void updateAddress_shouldThrowException_whenAddressNotFound() {
        Long id = 1L;
        AddressDTO addressDTO = new AddressDTO();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.updateAddress(id, addressDTO));
        assertEquals("Endereço com ID 1 não encontrado", exception.getMessage());
        verify(addressRepository).findById(id);
    }

    @Test
    void updateAddress_shouldUpdateAndReturnAddressDTO() {
        Long id = 1L;
        Address address = new Address();
        AddressDTO addressDTO = new AddressDTO();
        AddressDTO updatedAddressDTO = new AddressDTO();

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toAddressDTO(address)).thenReturn(updatedAddressDTO);

        AddressDTO result = addressService.updateAddress(id, addressDTO);

        assertEquals(updatedAddressDTO, result);
        verify(addressRepository).findById(id);
        verify(addressMapper).updateAddress(address, addressDTO);
        verify(addressRepository).save(address);
        verify(addressMapper).toAddressDTO(address);
    }
}
