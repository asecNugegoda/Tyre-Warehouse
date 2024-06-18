/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.warehouse.bean.RacksBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface RackServiceInf {
    
    public List<RacksBean> loadRacks(RacksBean inputBean, int max, int first) throws Exception;

    public boolean insertRacks(RacksBean inputBean) throws Exception;

    public void getUpdateRacks(RacksBean inputBean) throws Exception;

    public boolean deleteRacks(RacksBean inputBean) throws Exception;

    public boolean updateRacks(RacksBean inputBean) throws Exception;
    
}
