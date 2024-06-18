/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.mapping.Brand;
import com.myapp.mapping.Category;
import com.myapp.mapping.HibernateUtil;
import com.myapp.warehouse.bean.BrandBean;
import com.myapp.warehouse.bean.CategoryBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class BrandService implements BrandServiceInf{

    @Override
    public List<BrandBean> loadBrand(BrandBean inputBean, int max, int first) throws Exception {
        
        List<BrandBean> dataList = new ArrayList<BrandBean>();
        Session session = null;
        try {
            
            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sqlCount = "select count(brandId) from Brand ";
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Brand ";
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    BrandBean databean = new BrandBean();
                    Brand objBean = (Brand) it.next();

                    try {
                        databean.setBrandId(objBean.getBrandId().toString());
                    } catch (NullPointerException npe) {
                        databean.setBrandId("0");
                    }
                    try {
                        databean.setBrandName(objBean.getBrand());
                    } catch (NullPointerException npe) {
                        databean.setBrandName("--");
                    }
                    
                    databean.setFullCount(count);

                    dataList.add(databean);
                }

            }
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
                session.clear();
                session.getTransaction().commit();
                session.close();
                session = null;
            }
        }
        return dataList;
    }

    @Override
    public boolean insertBrand(BrandBean inputBean) throws Exception {
        boolean catAdd = false;
        Brand brand = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            brand = new Brand();

            brand.setBrand(inputBean.getBrandName());
            
            session.beginTransaction();
            session.save(brand);
            session.getTransaction().commit();

            catAdd = true;

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
        return catAdd;
    }

    @Override
    public boolean deleteBrand(BrandBean inputBean) throws Exception {
        boolean isFeDeleted = false;
        Session session = null;
        Query query = null;

        try {

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            String sql = "DELETE Brand bb where bb.brandId=:profile";
            query = session.createQuery(sql);
            query.setInteger("profile", Integer.parseInt(inputBean.getBrandId()));

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

    @Override
    public String GetResult(BrandBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
