package com.ilids.service.impl;

import com.ilids.IRepository.SystemSettingsRepository;
import com.ilids.IService.SystemSettingsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ilids.domain.SystemSettings;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class SystemSettingsServiceImpl  implements SystemSettingsService{

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    @Override
    public List<SystemSettings> getAllSystemSettings() {
        List<SystemSettings> list = systemSettingsRepository.getAll();
        return list;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public SystemSettings findById(Long id)throws Exception {
        SystemSettings systemSettings = systemSettingsRepository.findById(id);
        return systemSettings;
    }

    @Override
    public SystemSettings remove(Long id) throws Exception{
        SystemSettings systemSettings = systemSettingsRepository.findById(id);
        if (systemSettings == null) {
            throw new IllegalArgumentException();
        }
        //  device.getUser().getDevices().remove(device); //pre remove
        systemSettingsRepository.delete(systemSettings);
        return systemSettings;
    }

    @Override
    public boolean addSystemSettings(SystemSettings systemSettings)throws Exception {
        systemSettingsRepository.persist(systemSettings);
        return true;
    }

    @Override
    public boolean updateSystemSettings(SystemSettings systemSettings)throws Exception {
        systemSettingsRepository.merge(systemSettings);
        return true;
    }
}
