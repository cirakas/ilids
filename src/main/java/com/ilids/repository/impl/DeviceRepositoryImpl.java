package com.ilids.repository.impl;

import com.ilids.IRepository.DeviceRepository;
import org.springframework.stereotype.Component;

import com.ilids.domain.Devices;

/**
 *
 * @author cirakas
 */
@Component
public class DeviceRepositoryImpl extends GenericRepositoryImpl<Devices> implements DeviceRepository{

    DeviceRepositoryImpl() {
        super(Devices.class);
    }

}

