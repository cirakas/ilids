package com.ilids.repository.impl;

import com.ilids.IRepository.DeviceZoneRepository;
import org.springframework.stereotype.Component;
import com.ilids.domain.DeviceZone;

/**
 *
 * @author cirakas
 */
@Component
public class DeviceZoneRepositoryImpl extends GenericRepositoryImpl<DeviceZone> implements DeviceZoneRepository{

    /**
     *
     */
    public DeviceZoneRepositoryImpl() {
        super(DeviceZone.class);
    }

}
