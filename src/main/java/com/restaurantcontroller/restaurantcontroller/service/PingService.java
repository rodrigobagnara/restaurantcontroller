package com.restaurantcontroller.restaurantcontroller.service;

import com.restaurantcontroller.restaurantcontroller.dto.PingDTO;
import org.springframework.stereotype.Service;

@Service
public class PingService {

    public PingDTO getPing() {
        return new PingDTO();
    }

}
