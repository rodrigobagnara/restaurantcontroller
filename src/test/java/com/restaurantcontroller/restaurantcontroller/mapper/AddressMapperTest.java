package com.restaurantcontroller.restaurantcontroller.mapper;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapper();
    }

    @Test
    void toAddressDTO_shouldReturnNull_whenAddressIsNull() {
        AddressDTO result = addressMapper.toAddressDTO(null);
        assertNull(result);
    }

    @Test
    void toAddressDTO_shouldConvertAddressToDTO() {
        Address address = new Address();
        address.setStreet("Main Street");
        address.setNumber(123);
        address.setComplement("Apt 4B");
        address.setNeighborhood("Downtown");
        address.setCity("New York");
        address.setState("NY");
        address.setCep("10001");
        address.setCountry("USA");

        AddressDTO result = addressMapper.toAddressDTO(address);

        assertNotNull(result);
        assertEquals("Main Street", result.getStreet());
        assertEquals(123, result.getNumber());
        assertEquals("Apt 4B", result.getComplement());
        assertEquals("Downtown", result.getNeighborhood());
        assertEquals("New York", result.getCity());
        assertEquals("NY", result.getState());
        assertEquals("10001", result.getCep());
        assertEquals("USA", result.getCountry());
    }

    @Test
    void toAddress_shouldReturnNull_whenDTOIsNull() {
        Address result = addressMapper.toAddress(null);
        assertNull(result);
    }

    @Test
    void toAddress_shouldConvertDTOToAddress() {
        AddressDTO dto = new AddressDTO();
        dto.setStreet("Second Avenue");
        dto.setNumber(456);
        dto.setComplement("Suite 10");
        dto.setNeighborhood("Midtown");
        dto.setCity("Los Angeles");
        dto.setState("CA");
        dto.setCep("90001");
        dto.setCountry("USA");

        Address result = addressMapper.toAddress(dto);

        assertNotNull(result);
        assertEquals("Second Avenue", result.getStreet());
        assertEquals(456, result.getNumber());
        assertEquals("Suite 10", result.getComplement());
        assertEquals("Midtown", result.getNeighborhood());
        assertEquals("Los Angeles", result.getCity());
        assertEquals("CA", result.getState());
        assertEquals("90001", result.getCep());
        assertEquals("USA", result.getCountry());
    }

    @Test
    void updateAddress_shouldDoNothing_whenDTOIsNull() {
        Address address = new Address();
        addressMapper.updateAddress(address, null);
        assertNull(address.getStreet());
    }

    @Test
    void updateAddress_shouldDoNothing_whenAddressIsNull() {
        AddressDTO dto = new AddressDTO();
        addressMapper.updateAddress(null, dto);
        // No exception should be thrown
    }

    @Test
    void updateAddress_shouldUpdateExistingAddress() {
        Address address = new Address();
        address.setStreet("Old Street");
        address.setCity("Old City");

        AddressDTO dto = new AddressDTO();
        dto.setStreet("New Street");
        dto.setNumber(789);
        dto.setComplement("Floor 2");
        dto.setNeighborhood("Uptown");
        dto.setCity("New City");
        dto.setState("TX");
        dto.setCep("75001");
        dto.setCountry("USA");

        ZonedDateTime beforeUpdate = ZonedDateTime.now();
        addressMapper.updateAddress(address, dto);

        assertEquals("New Street", address.getStreet());
        assertEquals(789, address.getNumber());
        assertEquals("Floor 2", address.getComplement());
        assertEquals("Uptown", address.getNeighborhood());
        assertEquals("New City", address.getCity());
        assertEquals("TX", address.getState());
        assertEquals("75001", address.getCep());
        assertEquals("USA", address.getCountry());
        assertNotNull(address.getLastUpdate());
        assertTrue(address.getLastUpdate().isAfter(beforeUpdate) || address.getLastUpdate().isEqual(beforeUpdate));
    }
}
