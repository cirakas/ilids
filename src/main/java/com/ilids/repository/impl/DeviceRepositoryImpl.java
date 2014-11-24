package com.ilids.repository.impl;

import com.ilids.IRepository.DeviceRepository;
import org.springframework.stereotype.Component;

import com.ilids.domain.Devices;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author cirakas
 */
@Component
public class DeviceRepositoryImpl extends GenericRepositoryImpl<Devices> implements DeviceRepository{
    
    private static final Logger logger = LoggerFactory.getLogger(DataRepositoryImpl.class);

    DeviceRepositoryImpl() {
        super(Devices.class);
    }

    @Override
    public List<Devices> getAllUsedDevices() {
       return (List<Devices>)entityManager.createQuery("select d from Devices d where d.used=1").getResultList();
    }

    @Override
    public Long getFirstDevice() {
       Object firstDevice = 0;
       try{
           String DeiceIdQuery = "SELECT slave_id FROM devices where used = 1 LIMIT 1" ;
           firstDevice = entityManager.createNativeQuery(DeiceIdQuery).getSingleResult();
           Long.valueOf(firstDevice.toString());
       }catch(Exception e) {
            logger.error("There is an Exception in getFirstDevice method " + e.getMessage());
        }
        return Long.valueOf(firstDevice.toString());
    }
}

