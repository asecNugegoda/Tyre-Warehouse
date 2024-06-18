/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Warehouse;
import java.util.List;
import com.myapp.warehouse.bean.WarehouseBean;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class WarehouseService implements WarehouseServiceInf {

    @Override
    public List<WarehouseBean> loadWarehouse(WarehouseBean inputBean, int max, int first) {

        List<WarehouseBean> dataList = new ArrayList<WarehouseBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;
            if (inputBean.getSearchString()== null) {
                String sqlCount = "select count(warehouseId) from Warehouse wu";
                queryCount = session.createQuery(sqlCount);
            } else {
                String sqlCount = "select count(warehouseId) from Warehouse wu where wu.location LIKE :itName ";
                queryCount = session.createQuery(sqlCount);
                queryCount.setString("itName", "%" + inputBean.getSearchString() + "%");
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getSearchString() == null) {
                    String sqlSearch = "from Warehouse wu";
                    querySearch = session.createQuery(sqlSearch);
                } else {
                    String sqlSearch = "from Warehouse wu where wu.location LIKE :itName";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setString("itName", "%" + inputBean.getSearchString() + "%");
                }

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    WarehouseBean databean = new WarehouseBean();
                    Warehouse objBean = (Warehouse) it.next();

                    try {
                        databean.setLocation(objBean.getLocation());
                    } catch (NullPointerException npe) {
                        databean.setLocation("--");
                    }
                    try {
                        databean.setLocationId(objBean.getWarehouseId() + "");
                    } catch (Exception npe) {
                        databean.setLocationId("--");
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
    public boolean insertWarehouse(WarehouseBean inputBean) {
        boolean whit = false;
        Warehouse warehouse = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            warehouse = new Warehouse();

            warehouse.setLocation(inputBean.getLocation());

            session.save(warehouse);
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
    public void getUpdateWarehouse(WarehouseBean inputBean) {
        List<Warehouse> finduserlist = null;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Warehouse wu where wu.warehouseId =:itName";
            query = session.createQuery(sql);
            query.setString("itName", inputBean.getUpLocationId());
            finduserlist = query.list();

            if (0 < finduserlist.size()) {
                inputBean.setUpLocationId(finduserlist.get(0).getWarehouseId().toString());
                inputBean.setUpLocation(finduserlist.get(0).getLocation());
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
    public boolean deleteWarehouse(WarehouseBean inputBean) {
        
        boolean isFeDeleted = false;
        Session session = null;
        Query query = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "DELETE Warehouse wu where wu.warehouseId =:itName";
            query = session.createQuery(sql);
            query.setInteger("itName", Integer.parseInt(inputBean.getLocationId()));
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
    public boolean updateWarehouse(WarehouseBean inputBean) {
        boolean isUpdated = false;
        Session session = null;
        Query query = null;
        List<Warehouse> whItem = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "from Warehouse wu where wu.warehouseId =:tid";
            query = session.createQuery(sql);
            query.setString("tid", inputBean.getUpLocationId());
            whItem = query.list();
            if (whItem.size() > 0) {
                
                whItem.get(0).setLocation(inputBean.getUpLocation());

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

}
