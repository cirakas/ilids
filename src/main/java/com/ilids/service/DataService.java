package com.ilids.service;

import com.ilids.dao.DataRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



import com.ilids.domain.Data;
import com.ilids.domain.User;
import java.util.Date;
import javax.annotation.Resource;
import org.codehaus.jackson.map.util.JSONWrappedObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;


@Component
@Transactional
public class DataService {

  
    @Autowired
    private DataRepository dataRepository;

    public JSONArray getAllData() {
        List<Data> dataList=dataRepository.getAll();
        System.out.println("data====================="+dataList.size());
        JSONArray arr = new JSONArray();
        JSONObject tmp;
          for(int i = 0; i < dataList.size(); i++) {
              System.out.println("Inside loop"+i);
             if(8==dataList.get(i).getAddressMap().getOffSet()){
                 System.out.println("Date time is-----"+dataList.get(i).getTime());
             tmp = new JSONObject();
             tmp.put("voltage",dataList.get(i).getData()); //some public getters inside GraphUser?
             tmp.put("date",dataList.get(i).getTime());
             tmp.put("current",dataList.get(i).getData());
             tmp.put("power",dataList.get(i).getData());
             arr.put(tmp);
             }
          }
        
        System.out.println("Phase 1 voltage-----"+arr.toString());
        
        
        return arr;
    }
        
 

    public Data findById(Long id) {
        return dataRepository.findById(id);
    }

    public Data remove(Long id) {
        Data data = dataRepository.findById(id);
        if (data == null) {
            throw new IllegalArgumentException();
        }
      //  device.getUser().getDevices().remove(device); //pre remove
        dataRepository.delete(data);
        return data;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public boolean addDeviceToUser(String devicename, String username) {
//        //Devices device = createDevice(devicename);
//        User user = userService.findByCustomField("username", username);
//        if (user == null) {
//            throw new IllegalArgumentException();
//        }
//      //  user.addDevice(device);
//        userService.persist(user);
//        return true;
//    }
    
     public boolean addData(Data data){
       
       dataRepository.persist(data);
        return true;
    }
    

//    private Devices createDevice(String name) {
//        return new Devices(name);
//    }

}
