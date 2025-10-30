package com.restaurantcontroller.restaurantcontroller.mapper;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável por converter objetos Address para DTOs
 */
@Component
public class AddressMapper {

    /**
     * Converte um objeto Address para AddressDTO
     * @param address Objeto Address a ser convertido
     * @return AddressDTO com os dados do endereço
     */
    public AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setComplement(address.getComplement());
        addressDTO.setNeighborhood(address.getNeighborhood());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCep(address.getCep());
        addressDTO.setCountry(address.getCountry());
        
        return addressDTO;
    }

    /**
     * Converte um objeto AddressDTO para Address
     * @param addressDTO Objeto AddressDTO a ser convertido
     * @return Address com os dados do endereço
     */
    public Address toAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCep(addressDTO.getCep());
        address.setCountry(addressDTO.getCountry());
        
        return address;
    }

    /**
     * Atualiza um objeto Address existente com dados de um AddressDTO
     * @param existingAddress Objeto Address existente a ser atualizado
     * @param addressDTO Objeto AddressDTO com os novos dados
     */
    public void updateAddress(Address existingAddress, AddressDTO addressDTO) {
        if (addressDTO == null || existingAddress == null) {
            return;
        }
        
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setNumber(addressDTO.getNumber());
        existingAddress.setComplement(addressDTO.getComplement());
        existingAddress.setNeighborhood(addressDTO.getNeighborhood());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setCep(addressDTO.getCep());
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setLastUpdate(java.time.ZonedDateTime.now());
    }
}
