/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.service.impl;

import com.ilids.IRepository.AddressMapsRepository;
import com.ilids.IService.AddressMapsService;
import com.ilids.domain.AddressMaps;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class AddressMapsServiceImpl implements AddressMapsService{
    
    @Autowired
    AddressMapsRepository addressMapsRepository;

    @Override
    public AddressMaps addAddressMaps(AddressMaps addressMaps) throws Exception {
        addressMapsRepository.persist(addressMaps);
        addressMapsRepository.close();
        return addressMaps;
        
    }

    @Override
    public List<AddressMaps> getAllAddressMaps() {
        List<AddressMaps> allAddressMap = addressMapsRepository.getAll();
        addressMapsRepository.close();
        return allAddressMap;
    }

    @Override
    public AddressMaps remove(Long addressMapsId) {
        AddressMaps addressMaps = addressMapsRepository.findById(addressMapsId);
        addressMapsRepository.delete(addressMaps);
        addressMapsRepository.close();
        return addressMaps;
    }

    @Override
    public AddressMaps findById(Long id) {
        AddressMaps amId = addressMapsRepository.findById(id);
        addressMapsRepository.close();
        return amId;
        
    }

    @Override
    public AddressMaps updateAddressMaps(AddressMaps addressMaps) {
         addressMapsRepository.merge(addressMaps);
         addressMapsRepository.close();
         return addressMaps;
    }

    
}
