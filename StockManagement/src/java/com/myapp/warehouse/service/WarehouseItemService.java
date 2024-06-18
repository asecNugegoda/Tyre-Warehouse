/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.mapping.Brand;
import com.myapp.mapping.Category;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Status;
import com.myapp.mapping.WarehouseItem;
import com.myapp.util.StatusValues;
import com.myapp.warehouse.bean.BrandBean;
import com.myapp.warehouse.bean.WarehouseItemBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class WarehouseItemService implements WarehouseItemServiceInf {

    @Override
    public List<WarehouseItemBean> loadWhItem(WarehouseItemBean inputBean, int max, int first) throws Exception {
        WarehouseItem item = new WarehouseItem();
        List<WarehouseItemBean> dataList = new ArrayList<WarehouseItemBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;
            if (inputBean.getWhItmeName() == null) {
                String sqlCount = "select count(itemId) from WarehouseItem wu";
                queryCount = session.createQuery(sqlCount);
            } else {
                String sqlCount = "select count(itemId) from WarehouseItem wu where wu.itemName LIKE :itName ";
                queryCount = session.createQuery(sqlCount);
                queryCount.setString("itName", "%" + inputBean.getWhItmeName() + "%");
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getWhItmeName() == null) {
                    String sqlSearch = "from WarehouseItem wu";
                    querySearch = session.createQuery(sqlSearch);
                } else {
                    String sqlSearch = "from WarehouseItem wu where wu.itemName LIKE :itName";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setString("itName", "%" + inputBean.getWhItmeName() + "%");
                }

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    WarehouseItemBean databean = new WarehouseItemBean();
                    WarehouseItem objBean = (WarehouseItem) it.next();

                    try {
                        databean.setBrand(objBean.getBrand().getBrand());
                    } catch (NullPointerException npe) {
                        databean.setBrand("--");
                    }
                    try {
                        databean.setCategory(objBean.getCategory().getCategory());
                    } catch (Exception npe) {
                        databean.setCategory("--");
                    }
                    try {
                        databean.setWhItmeName(objBean.getItemName());
                    } catch (NullPointerException npe) {
                        databean.setWhItmeName("--");
                    }
                    try {
                        databean.setStatus(objBean.getStatus().getStatusId()+"");
                    } catch (NullPointerException npe) {
                        databean.setStatus("--");
                    }
                    try {
                        databean.setWhItemId(objBean.getItemId()+"");
                    } catch (NullPointerException npe) {
                        databean.setStatus("--");
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
    public boolean insertWhItem(WarehouseItemBean inputBean) throws Exception {
        boolean whit = false;
        WarehouseItem whItem = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            whItem = new WarehouseItem();

            Brand brand = new Brand();
            brand.setBrandId(Integer.parseInt(inputBean.getBrand()));
            whItem.setBrand(brand);

            Category catgry = new Category();
            catgry.setCategoryId(Integer.parseInt(inputBean.getCategory()));
            whItem.setCategory(catgry);

            Status stat = new Status();
            stat.setStatusId(StatusValues.ACTIVE);
            whItem.setStatus(stat);

            whItem.setItemName(inputBean.getWhItmeName());

            session.save(whItem);
            session.getTransaction().commit();
            whit = true;

        } catch (Exception e) {
            e.printStackTrace();
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
        return whit;
    }

    @Override
    public void getUpdateWhItem(WarehouseItemBean inputBean) throws Exception {
        List<WarehouseItem> finduserlist = null;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from WarehouseItem wu where wu.itemId LIKE :itName";
            query = session.createQuery(sql);
            query.setString("itName", inputBean.getWhItemId());
            finduserlist = query.list();

            if (0 < finduserlist.size()) {
                inputBean.setUpWhItemId(finduserlist.get(0).getItemId().toString()+"");
                inputBean.setUpWhItmeName(finduserlist.get(0).getItemName());
                inputBean.setUpBrand(finduserlist.get(0).getBrand().getBrandId()+"");
                inputBean.setUpCategory(finduserlist.get(0).getCategory().getCategoryId()+"");
                inputBean.setUpStatus(finduserlist.get(0).getStatus().getStatusId()+"");
            }
        } catch (Exception e) {
            if (session != null) {
                session.close();
                session = null;
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    @Override
    public boolean deleteWhItem(WarehouseItemBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateWhItem(WarehouseItemBean inputBean) throws Exception {
        boolean isUpdated = false;
        Session session = null;
        Query query = null;
        List<WarehouseItem> whItem = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "from WarehouseItem wu where wu.itemId =:tid";
            query = session.createQuery(sql);
            query.setString("tid", inputBean.getUpWhItemId());
            whItem = query.list();
            if (whItem.size() > 0) {
                
                whItem.get(0).setItemName(inputBean.getUpWhItmeName());

                Status satatus = new Status();
                satatus.setStatusId(Integer.parseInt(inputBean.getUpStatus()));
                whItem.get(0).setStatus(satatus);

                Brand brand = new Brand();
                brand.setBrandId(Integer.parseInt(inputBean.getUpBrand()));
                whItem.get(0).setBrand(brand);

                Category category = new Category();
                category.setCategoryId(Integer.parseInt(inputBean.getUpCategory()));
                whItem.get(0).setCategory(category);

                session.save(whItem.get(0));
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
    public String GetResult(WarehouseItemBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getBrands(WarehouseItemBean inputBean) throws Exception {
        List<Brand> category;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from Brand";
            Query query = session.createQuery(hql);
            category = (List<Brand>) query.list();
            int size = category.size();
            for (int i = 0; i < size; i++) {
                inputBean.getBrandList().put(category.get(i).getBrandId(), category.get(i).getBrand());
            }

        } catch (Exception ex) {
            if (session != null) {
                session.close();
                session = null;
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }

    }

    @Override
    public void getCategory(WarehouseItemBean inputBean) throws Exception {
        List<Category> category;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from Category";
            Query query = session.createQuery(hql);
            category = (List<Category>) query.list();
            int size = category.size();
            for (int i = 0; i < size; i++) {
                inputBean.getCategoryList().put(category.get(i).getCategoryId(), category.get(i).getCategory());
            }

        } catch (Exception ex) {
            if (session != null) {
                session.close();
                session = null;
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }

    }

}
