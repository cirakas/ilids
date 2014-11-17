package com.ilids.repository.impl;

import com.ilids.IRepository.DeviceRepository;
import org.springframework.stereotype.Component;

import com.ilids.domain.Devices;
import java.util.List;

/**
 *
 * @author cirakas
 */
@Component
public class DeviceRepositoryImpl extends GenericRepositoryImpl<Devices> implements DeviceRepository{

    DeviceRepositoryImpl() {
        super(Devices.class);
    }

    @Override
    public List<Devices> getAllUsedDevices() {
       return (List<Devices>)entityManager.createQuery("select d from Devices d where d.used=1").getResultList();
    }

}

