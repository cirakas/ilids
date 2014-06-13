package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.SystemSettings;

@Component
public class SystemSettingsRepository extends AbstractGenericDao<SystemSettings> {

    public SystemSettingsRepository() {
        super(SystemSettings.class);
    }

}

