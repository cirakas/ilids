/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.IService.AddressMapsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author cirakas
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@ContextConfiguration("classpath*:/WEB-INF/spring/appServlet/servlet-context.xml")
public class AddressMapsControllerTest {
   
    private MockMvc mockMvc;
    
    @Mock
    AddressMapsService addressMapsService;
    
    @InjectMocks
    AddressMapsController addressMapController;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    //    Mockito.reset(addressMapsService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
    }
    
    @Test
    public void addAddressMaps(){
   }
}
