package com.ilids.dao;

import org.springframework.stereotype.Component;
import com.ilids.domain.DeviceZone;

@Component
public class DeviceZoneRepository extends AbstractGenericDao<DeviceZone> {

    public DeviceZoneRepository() {
        super(DeviceZone.class);
    }

}
