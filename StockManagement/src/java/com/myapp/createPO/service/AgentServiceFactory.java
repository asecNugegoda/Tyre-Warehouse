/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

/**
 *
 * @author thilinath
 */
public class AgentServiceFactory {

    private final AgentServiceInf agentServiceInf;
    
    public AgentServiceFactory(){
        this.agentServiceInf = new AgentService();
    }

    public AgentServiceInf getAgentServiceInf() {
        return agentServiceInf;
    }   
    
}
