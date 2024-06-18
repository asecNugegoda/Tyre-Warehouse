/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.mapping.Category;
import com.myapp.mapping.HibernateUtil;
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
public class CategoryService implements CategoryServiceInf{


    @Override
    public boolean insertCategory(CategoryBean inputBean) throws Exception {
        boolean catAdd = false;
        Category category = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            category = new Category();

            category.setCategory(inputBean.getCategory());
            
            session.beginTransaction();
            session.save(category);
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
    public List<CategoryBean> loadCategory(CategoryBean inputBean, int max, int first) throws Exception {
        
        List<CategoryBean> dataList = new ArrayList<CategoryBean>();
        Session session = null;
        try {
            
            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sqlCount = "select count(categoryId) from Category ";
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Category ";
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    CategoryBean databean = new CategoryBean();
                    Category objBean = (Category) it.next();

                    try {
                        databean.setCategoryId(objBean.getCategoryId().toString());
                    } catch (NullPointerException npe) {
                        databean.setCategoryId("0");
                    }
                    try {
                        databean.setCategory(objBean.getCategory());
                    } catch (NullPointerException npe) {
                        databean.setCategory("--");
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
    public boolean deleteCategory(CategoryBean inputBean) throws Exception {
        boolean isFeDeleted = false;
        Session session = null;
        Query query = null;

        try {

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            String sql = "DELETE Category bb where bb.categoryId=:profile";
            query = session.createQuery(sql);
            query.setInteger("profile", Integer.parseInt(inputBean.getCategoryId()));

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
    public String GetResult(CategoryBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
