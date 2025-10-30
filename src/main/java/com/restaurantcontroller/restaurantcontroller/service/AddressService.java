package com.restaurantcontroller.restaurantcontroller.service;

import com.restaurantcontroller.restaurantcontroller.dto.AddressDTO;
import com.restaurantcontroller.restaurantcontroller.mapper.AddressMapper;
import com.restaurantcontroller.restaurantcontroller.model.Address;
import com.restaurantcontroller.restaurantcontroller.model.User;
import com.restaurantcontroller.restaurantcontroller.repository.AddressRepository;
import com.restaurantcontroller.restaurantcontroller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    /**
     * Busca um endereço pelo ID do usuário
     * @param userId ID do usuário
     * @return Optional contendo o AddressDTO se encontrado
     */
    public Optional<AddressDTO> getAddressByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + userId + " não encontrado");
        }

        Address address = user.get().getAddressUser();
        if (address == null) {
            return Optional.empty();
        }

        return Optional.of(addressMapper.toAddressDTO(address));
    }

    /**
     * Busca um endereço pelo ID
     * @param id ID do endereço
     * @return Optional contendo o AddressDTO se encontrado
     */
    public Optional<AddressDTO> getAddressById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser válido");
        }

        Optional<Address> address = addressRepository.findById(id);
        return address.map(addressMapper::toAddressDTO);
    }

    /**
     * Atualiza um endereço pelo ID do usuário
     * @param userId ID do usuário
     * @param addressDTO Dados do endereço para atualização
     * @return AddressDTO atualizado
     * @throws IllegalArgumentException se o usuário ou endereço não for encontrado
     */
    public AddressDTO updateAddressByUserId(Long userId, AddressDTO addressDTO) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }

        if (addressDTO == null) {
            throw new IllegalArgumentException("Dados do endereço não podem ser nulos");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + userId + " não encontrado");
        }

        Address address = user.get().getAddressUser();
        if (address == null) {
            throw new IllegalArgumentException("Usuário não possui endereço cadastrado");
        }
        
        // Atualiza os campos do endereço usando o mapper
        addressMapper.updateAddress(address, addressDTO);

        Address savedAddress = addressRepository.save(address);
        return addressMapper.toAddressDTO(savedAddress);
    }

    /**
     * Atualiza um endereço existente
     * @param id ID do endereço a ser atualizado
     * @param addressDTO Dados do endereço para atualização
     * @return AddressDTO atualizado
     * @throws IllegalArgumentException se o endereço não for encontrado
     */
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser válido");
        }

        if (addressDTO == null) {
            throw new IllegalArgumentException("Dados do endereço não podem ser nulos");
        }

        Optional<Address> existingAddress = addressRepository.findById(id);
        if (existingAddress.isEmpty()) {
            throw new IllegalArgumentException("Endereço com ID " + id + " não encontrado");
        }

        Address address = existingAddress.get();
        
        // Atualiza os campos do endereço usando o mapper
        addressMapper.updateAddress(address, addressDTO);

        Address savedAddress = addressRepository.save(address);
        return addressMapper.toAddressDTO(savedAddress);
    }
}
