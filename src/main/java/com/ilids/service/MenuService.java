/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service;

import com.ilids.dao.MenuRepository;
import com.ilids.domain.Menu;
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
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;   
    
    public List<Menu> getAllMenu() {
        return menuRepository.getAll();
    }
}
