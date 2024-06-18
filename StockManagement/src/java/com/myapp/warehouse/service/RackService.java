/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.warehouse.service;

import com.myapp.mapping.Cell;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Racks;
import com.myapp.mapping.Warehouse;
import com.myapp.warehouse.bean.RacksBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author thilinath
 */
public class RackService implements RackServiceInf {

    @Override
    public List<RacksBean> loadRacks(RacksBean inputBean, int max, int first) throws Exception {

        List<RacksBean> dataList = new ArrayList<RacksBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query queryCount2;

            Query querySearch;
            Query querySearch2;
            if (inputBean.getWarehouseId() == null) {
                String sqlCount = "select count(racksId) from Racks wu";
                queryCount = session.createQuery(sqlCount);
            } else {

                String sqlCount = "select count(racksId) from Racks wu where wu.warehouse.warehouseId =:wareID ";
                queryCount = session.createQuery(sqlCount);
                queryCount.setString("wareID", inputBean.getWarehouseId());
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getWarehouseId() == null) {
                    String sqlSearch = "from Racks wu";
                    querySearch = session.createQuery(sqlSearch);
                } else {
                    String sqlSearch = "from Racks wu where wu.warehouse.warehouseId =:wareID";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setString("wareID", inputBean.getWarehouseId());
                }
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    RacksBean databean = new RacksBean();
                    Racks objBean = (Racks) it.next();

                    try {
                        databean.setRack(objBean.getRack());
                    } catch (NullPointerException npe) {
                        databean.setRack("--");
                    }
                    try {
                        databean.setRackId(objBean.getRacksId().toString());
                    } catch (NullPointerException npe) {
                        databean.setRack("--");
                    }

                    String Rackid = objBean.getRacksId().toString();
                    Warehouse wrh = new Warehouse();
                    wrh.setWarehouseId(Integer.parseInt(Rackid));

                    String sqlSearch2 = "from Cell wu where wu.racks.racksId =:rackID";
                    querySearch2 = session.createQuery(sqlSearch2);
                    querySearch2.setInteger("rackID", objBean.getRacksId());
                    querySearch2.setMaxResults(max);
                    querySearch2.setFirstResult(first);

                    List<Cell> it2 = querySearch2.list();
                    for (int i = 0; i < it2.size(); i++) {

                        Cell obj = new Cell();

                        try {
                            databean.setHrCount(it2.get(0).getXCode());
                        } catch (Exception e) {
                            databean.setHrCount("0");
                        }
                        try {
                            databean.setVrCount(it2.get(0).getYCode());
                        } catch (Exception e) {
                            databean.setHrCount("0");
                        }
                        try {
                            databean.setMaxStorage(it2.get(0).getMaxStorage() + "");
                        } catch (Exception e) {
                            databean.setHrCount("0");
                        }

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
    public boolean insertRacks(RacksBean inputBean) throws Exception {
        boolean whit = false;
        Racks racks = null;
        Cell cell = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            racks = new Racks();
            cell = new Cell();

            Warehouse wrhouse = new Warehouse();
            wrhouse.setWarehouseId(Integer.parseInt(inputBean.getWarehouseId()));

            racks.setRack(inputBean.getRack());
            racks.setWarehouse(wrhouse);
            session.save(racks);

            cell.setRacks(racks);
            cell.setXCode(inputBean.getHrCount());
            cell.setYCode(inputBean.getVrCount());
            cell.setMaxStorage(Double.parseDouble(inputBean.getMaxStorage()));
            session.save(cell);

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
    public void getUpdateRacks(RacksBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteRacks(RacksBean inputBean) throws Exception {

        boolean isFeDeleted = false;
        Session session = null;
        Query query = null;
        Query query2 = null;
        Query querySearch = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            String sql = "DELETE Cell wu where wu.racks.racksId =:racksid";
            query = session.createQuery(sql);
            query.setInteger("racksid", Integer.parseInt(inputBean.getRackId()));
            int result = query.executeUpdate();
            
            String sql2 = "DELETE Racks wu where wu.racksId =:itName";
            query2 = session.createQuery(sql2);
            query2.setInteger("itName", Integer.parseInt(inputBean.getRackId()));
            int result2 = query2.executeUpdate();
            
            if (1 == result && 1 == result2) {
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
    public boolean updateRacks(RacksBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
