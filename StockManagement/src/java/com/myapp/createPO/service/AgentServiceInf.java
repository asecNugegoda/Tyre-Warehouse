/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.AgentBean;
import java.util.List;

/**
 *
 * @author thilinath
 */
public interface AgentServiceInf {

    public List<AgentBean> loadAgentProfile(AgentBean inputBean, int max, int first) throws Exception;

    public boolean insertAgentProfile(AgentBean inputBean) throws Exception;

    public void getUpdateAgent(AgentBean inputBean) throws Exception;

    public boolean deleteAgent(AgentBean inputBean) throws Exception;

    public boolean updateAgent(AgentBean inputBean) throws Exception;

    public String GetResult(AgentBean inputBean) throws Exception;

}
