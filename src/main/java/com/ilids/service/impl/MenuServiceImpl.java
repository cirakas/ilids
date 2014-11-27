/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service.impl;

import com.ilids.IRepository.MenuRepository;
import com.ilids.IService.MenuService;
import com.ilids.repository.impl.MenuRepositoryImpl;
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
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;   
    
    @Override
    public List<Menu> getAllMenu() {
        List<Menu> allMenu = menuRepository.getAll();
        menuRepository.close();
        return allMenu;
    }
}
