package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Devices;

@Component
public class DeviceRepository extends AbstractGenericDao<Devices> {

    public DeviceRepository() {
        super(Devices.class);
    }

}

