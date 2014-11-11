/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.IService;

import com.ilids.domain.AddressMaps;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface AddressMapsService {

    public AddressMaps addAddressMaps(AddressMaps addressMaps) throws Exception;

    public List<AddressMaps> getAllAddressMaps();

    public AddressMaps remove(Long addressMapsId) throws Exception;

    /**
     *
     * @param id
     * @return
     */
    public AddressMaps findById(Long id);

    public AddressMaps updateAddressMaps(AddressMaps addressMaps) throws Exception;


    
}
