package com.ilids.repository.impl;

import com.ilids.IRepository.SystemSettingsRepository;
import org.springframework.stereotype.Component;

import com.ilids.domain.SystemSettings;

/**
 *
 * @author cirakas
 */
@Component
public class SystemSettingsRepositoryImpl extends GenericRepositoryImpl<SystemSettings> implements SystemSettingsRepository{

    /**
     *
     */
    public SystemSettingsRepositoryImpl() {
        super(SystemSettings.class);
    }

}

