/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.AgentBean;
import com.myapp.mapping.Brand;
import com.myapp.mapping.Category;
import com.myapp.mapping.Dealer;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Status;
import com.myapp.mapping.WarehouseItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class AgentService implements AgentServiceInf {

    @Override
    public List<AgentBean> loadAgentProfile(AgentBean inputBean, int max, int first) throws Exception {
        List<AgentBean> dataList = new ArrayList<AgentBean>();

        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sqlCount = "";
            String sqlSearch = "";
            Query querySearch = null;
            Query queryCount = null;

            if (inputBean.getAgentName() != null) {
                sqlCount = "select count(dealer) from Dealer dd where dd.deaerName LIKE :fname ";
                queryCount = session.createQuery(sqlCount);

                sqlSearch = "from Dealer dd where dd.deaerName LIKE :fname ";
                querySearch = session.createQuery(sqlSearch);
                queryCount.setParameter("fname", "%" + inputBean.getAgentName() + "%");
                querySearch.setParameter("fname", "%" + inputBean.getAgentName() + "%");
            } else {
                sqlCount = "select count(dealer) from Dealer ";
                queryCount = session.createQuery(sqlCount);

                sqlSearch = "from Dealer ";
                querySearch = session.createQuery(sqlSearch);
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {
                    AgentBean databean = new AgentBean();
                    Dealer objBean = (Dealer) it.next();
                    try {
                        databean.setAgentId(objBean.getDealer() + "");
                    } catch (NullPointerException npe) {
                        databean.setAgentId("0");
                    }
                    try {
                        databean.setAgentName(objBean.getDeaerName());
                    } catch (NullPointerException npe) {
                        databean.setAgentName("");
                    }
                    try {
                        databean.setAdr1(objBean.getAdr1());
                    } catch (NullPointerException npe) {
                        databean.setAdr1("");
                    }
                    try {
                        databean.setAdr2(objBean.getAdr2());
                    } catch (NullPointerException npe) {
                        databean.setAdr2("");
                    }
                    try {
                        databean.setCity(objBean.getCity());
                    } catch (NullPointerException npe) {
                        databean.setCity("");
                    }
                    try {
                        databean.setContact(objBean.getContact());
                    } catch (NullPointerException npe) {
                        databean.setContact("");
                    }

                    databean.setFullCount(count);
                    dataList.add(databean);

                }
            }
        } catch (Exception e) {
            if (session != null) {
                session.close();
                session = null;
            }
            throw e;
        } finally {
            if (session != null) {
                session.clear();
                session.getTransaction().commit();
                session.close();
                session = null;
            }
        }

        return dataList;

    }

    @Override
    public boolean insertAgentProfile(AgentBean inputBean) throws Exception {

        boolean binAdd = false;
        Dealer agent = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            agent = new Dealer();

            agent.setDeaerName(inputBean.getAgentName());
            agent.setAdr1(inputBean.getAdr1());
            agent.setAdr2(inputBean.getAdr2());
            agent.setCity(inputBean.getCity());
            agent.setContact(inputBean.getContact());

            session.beginTransaction();
            session.save(agent);
            session.getTransaction().commit();

            binAdd = true;

        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            throw e;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
        return binAdd;
    }

    @Override
    public void getUpdateAgent(AgentBean inputBean) throws Exception {

        List<Dealer> finfuserlist = null;
        Session session = null;
        Query query = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Dealer wu where wu.dealer =:agentId";
            query = session.createQuery(sql);
            query.setString("agentId", inputBean.getAgentId());
            finfuserlist = query.list();

            if (0 < finfuserlist.size()) {
                inputBean.setUpAgentId(finfuserlist.get(0).getDealer() + "");
                inputBean.setUpAgentName(finfuserlist.get(0).getDeaerName());
                inputBean.setUpAdr1(finfuserlist.get(0).getAdr1());
                inputBean.setUpAdr2(finfuserlist.get(0).getAdr2());
                inputBean.setUpCity(finfuserlist.get(0).getCity());
                inputBean.setUpContact(finfuserlist.get(0).getContact());
            }
        } catch (Exception e) {
            if (session != null) {
                session.close();
                session = null;
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }

    }

    @Override
    public boolean deleteAgent(AgentBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateAgent(AgentBean inputBean) throws Exception {
        boolean isUpdated = false;
        Session session = null;
        Query query = null;
        List<Dealer> dealer = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "from Dealer wu where wu.dealer =:tid";
            query = session.createQuery(sql);
            query.setString("tid", inputBean.getUpAgentId());
            dealer = query.list();
            if (dealer.size() > 0) {
                
                dealer.get(0).setDeaerName(inputBean.getUpAgentName());
                dealer.get(0).setAdr1(inputBean.getUpAdr1());
                dealer.get(0).setAdr2(inputBean.getUpAdr2());
                dealer.get(0).setCity(inputBean.getUpCity());
                dealer.get(0).setContact(inputBean.getUpContact());

                session.save(dealer.get(0));
                session.getTransaction().commit();
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.close();//roll back
            } catch (Exception e) {
                throw e;
            }
        }
        return isUpdated;
    }

    @Override
    public String GetResult(AgentBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
