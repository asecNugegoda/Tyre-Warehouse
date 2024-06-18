/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.service;

import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Customer;
import com.myapp.mapping.Vehicle;
import com.myapp.userManagement.bean.vehicleBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class RegisterVehicleService implements RegisterVehicleServiceInf {

    @Override
    public boolean addVehicle(vehicleBean inputBean) throws Exception {
        boolean whit = false;
        Vehicle vehicle = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            vehicle = new Vehicle();

            vehicle.setBrand(inputBean.getVehicleBrand());
            vehicle.setVehicleNo(inputBean.getVehicleNumber());
            
            Customer cus = new Customer();
            cus.setCustomerId(Integer.parseInt(inputBean.getUsrId()));
            vehicle.setCustomer(cus);

            session.save(vehicle);
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
    public List<vehicleBean> loadVehicle(vehicleBean inputBean, int max, int first) throws Exception {
        Vehicle item = new Vehicle();
        List<vehicleBean> dataList = new ArrayList<vehicleBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;
            if (inputBean.getUsrId()== null) {
                String sqlCount = "select count(vehicleId) from Vehicle wu";
                queryCount = session.createQuery(sqlCount);
            } else {
                String sqlCount = "select count(vehicleId) from Vehicle wu where wu.customer.customerId =:itName ";
                queryCount = session.createQuery(sqlCount);
                queryCount.setString("itName", inputBean.getUsrId());
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getUsrId()== null) {
                    String sqlSearch = "from Vehicle wu";
                    querySearch = session.createQuery(sqlSearch);
                } else {
                    String sqlSearch = "from Vehicle wu where wu.customer.customerId =:itName ";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setString("itName", inputBean.getUsrId());
                }

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    vehicleBean databean = new vehicleBean();
                    Vehicle objBean = (Vehicle) it.next();

                    try {
                        databean.setVehicleBrand(objBean.getBrand());
                    } catch (NullPointerException npe) {
                        databean.setVehicleBrand("--");
                    }
                    try {
                        databean.setVehicleNumber(objBean.getVehicleNo());
                    } catch (Exception npe) {
                        databean.setVehicleNumber("--");
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
    public boolean deleteVehicle(vehicleBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void findVehicle(vehicleBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateVehicle(vehicleBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
