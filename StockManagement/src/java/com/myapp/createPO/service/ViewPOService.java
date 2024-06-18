/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.POItemBean;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.ItemOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class ViewPOService implements ViewPOServiceInf{

    @Override
    public List<POItemBean> loadPurchasingOrder(POItemBean inputBean, int max, int first) throws Exception {
        
        List<POItemBean> dataList = new ArrayList<POItemBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;

            String sqlCount = "select count(id) from ItemOrder po where po.purchasingOrder.order =:stat ";
            queryCount = session.createQuery(sqlCount);
            queryCount.setInteger("stat", Integer.parseInt(inputBean.getPo_id()));

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                String sqlSearch = "from ItemOrder po where po.purchasingOrder.order =:stat";
                querySearch = session.createQuery(sqlSearch);
                querySearch.setInteger("stat", Integer.parseInt(inputBean.getPo_id()));

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    POItemBean databean = new POItemBean();
                    ItemOrder objBean = (ItemOrder) it.next();

                    try {
                        databean.setBrand(objBean.getWarehouseItem().getBrand().getBrand());
                    } catch (NullPointerException npe) {
                        databean.setBrand("---");
                    }
                    try {
                        databean.setCategory(objBean.getWarehouseItem().getCategory().getCategory());
                    } catch (Exception npe) {
                        databean.setCategory("---");
                    }
                    try {
                        databean.setWhItmeName(objBean.getWarehouseItem().getItemName());
                    } catch (Exception npe) {
                        databean.setWhItmeName("---");
                    }
                    try {
                        databean.setQnty(objBean.getQnty().toString());
                    } catch (Exception npe) {
                        databean.setWhItmeName("---");
                    }
                    try {
                        databean.setPo_id(objBean.getId().toString());
                    } catch (Exception npe) {
                        databean.setWhItmeName("---");
                    }
                                        
                    databean.setFullCount(count);

                    dataList.add(databean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.clear();
                session.getTransaction().commit();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return dataList;
    }

    @Override
    public boolean deleteWhItem(POItemBean inputBean) throws Exception {
        
        boolean isFeDeleted = false;
        Session session = null;
        Query query = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            String sql = "DELETE ItemOrder po where po.id =:poid";
            query = session.createQuery(sql);
            query.setInteger("poid", Integer.parseInt(inputBean.getPo_id()));
            int result = query.executeUpdate();
            
            if (1 == result) {
                isFeDeleted = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                    session = null;
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return isFeDeleted;
    }
    
}
