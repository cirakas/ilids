/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.repository.impl;

import com.ilids.IRepository.AddressMapsRepository;
import com.ilids.domain.AddressMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cirakas
 */
@Component
public class AddressMapsRepositoryImpl extends GenericRepositoryImpl<AddressMaps> implements AddressMapsRepository{

    public AddressMapsRepositoryImpl() {
        super(AddressMaps.class);
    }
    
    
}
