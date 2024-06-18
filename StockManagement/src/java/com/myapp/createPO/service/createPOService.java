/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.createPO.service;

import com.myapp.createPO.bean.PurchaseOrderBean;
import com.myapp.mapping.Brand;
import com.myapp.mapping.Category;
import com.myapp.mapping.Dealer;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.ItemOrder;
import com.myapp.mapping.OrderDealer;
import com.myapp.mapping.PurchasingOrder;
import com.myapp.mapping.Status;
import com.myapp.mapping.WarehouseItem;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class createPOService implements createPOServiceInf {

    @Override
    public List<PurchaseOrderBean> loadWhItem(PurchaseOrderBean inputBean, int max, int first) throws Exception {

        List<PurchaseOrderBean> dataList = new ArrayList<PurchaseOrderBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;
            Query querySearch2;

            if (inputBean.getFromdate() != null && inputBean.getTodate() != null) {

                String sqlCount = "select count(status) from PurchasingOrder po where po.status.statusId =:stat and (po.date >= :beginDate and po.date <= :endDate)";
                queryCount = session.createQuery(sqlCount);
                queryCount.setInteger("stat", 3);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                Date beginDate = dateFormatter.parse(inputBean.getFromdate());
                queryCount.setParameter("beginDate", beginDate);
                Date endDate = dateFormatter.parse(inputBean.getTodate());
                endDate.setDate(endDate.getDate() + 1);
                queryCount.setParameter("endDate", endDate);

            } else {

                String sqlCount = "select count(status) from PurchasingOrder po where po.status.statusId =:stat ";
                queryCount = session.createQuery(sqlCount);
                queryCount.setInteger("stat", 3);

            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getFromdate() != null && inputBean.getTodate() != null) {

                    String sqlSearch = "from PurchasingOrder po where po.status.statusId =:stat and (po.date >= :beginDate and po.date <= :endDate)";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setInteger("stat", 3);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = dateFormatter.parse(inputBean.getFromdate());
                    querySearch.setParameter("beginDate", beginDate);
                    Date endDate = dateFormatter.parse(inputBean.getTodate());
                    endDate.setDate(endDate.getDate() + 1);
                    querySearch.setParameter("endDate", endDate);

                } else {

                    String sqlSearch = "from PurchasingOrder po where po.status.statusId =:stat order  by po.date desc";
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setInteger("stat", 3);
                    
                }
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    PurchaseOrderBean databean = new PurchaseOrderBean();
                    PurchasingOrder objBean = (PurchasingOrder) it.next();

                    try {
                        databean.setOrder_id(objBean.getOrder());
                    } catch (NullPointerException npe) {
                        databean.setOrder_id(0);
                    }
                    try {
                        databean.setCreate_date(objBean.getDate());
                    } catch (Exception npe) {
                        databean.setCreate_date(null);
                    }
                    try {
                        databean.setPo_status(objBean.getStatus().getStatusId() + "");
                    } catch (Exception npe) {
                        databean.setCreate_date(null);
                    }

                    String sqlSearch2 = "from OrderDealer wu where wu.purchasingOrder.order =:orderID ";
                    querySearch2 = session.createQuery(sqlSearch2);
                    querySearch2.setInteger("orderID", objBean.getOrder());

                    List<OrderDealer> it2 = querySearch2.list();
                    for (int i = 0; i < it2.size(); i++) {

                        try {
                            databean.setDealer(it2.get(0).getDealer().getDeaerName());
                        } catch (Exception e) {
                            databean.setDealer("---");
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

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public boolean insertWhItem(PurchaseOrderBean inputBean) throws Exception {
        boolean whit = false;
        PurchasingOrder purchas = null;
        OrderDealer orderDealer = null;
        ItemOrder itemOrder = null;
        Session session = null;
        List<ItemOrder> itemList = new ArrayList<ItemOrder>();
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            purchas = new PurchasingOrder();
            orderDealer = new OrderDealer();

            for (PurchaseOrderBean purchaseOrderBean : inputBean.getDatacollection()) {
                itemOrder = new ItemOrder();
                WarehouseItem wItem = new WarehouseItem();
                wItem.setItemId(Integer.parseInt(purchaseOrderBean.getItem()));
                itemOrder.setWarehouseItem(wItem);
                itemOrder.setQnty(purchaseOrderBean.getQnty());

                itemList.add(itemOrder);

                Status stat = new Status();
                stat.setStatusId(3);
                purchas.setStatus(stat);
                purchas.setDate(new Date());

                Dealer deal = new Dealer();
                deal.setDealer(Integer.parseInt(inputBean.getDealer()));
                orderDealer.setDealer(deal);
                orderDealer.setDate(new Date());
                orderDealer.setPurchasingOrder(purchas);
            }

            session.save(purchas);
            session.save(orderDealer);
            session.getTransaction().commit();

            int orderId = purchas.getOrder();

            for (ItemOrder itemOrder1 : itemList) {
                session.beginTransaction();
                purchas.setOrder(orderId);
                itemOrder1.setPurchasingOrder(purchas);
                session.save(itemOrder1);
                session.getTransaction().commit();
            }

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
    public void getUpdateWhItem(PurchaseOrderBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteWhItem(PurchaseOrderBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateWhItem(PurchaseOrderBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetResult(PurchaseOrderBean inputBean) throws Exception {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getItems(PurchaseOrderBean inputBean) throws Exception {
        List<WarehouseItem> item;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from WarehouseItem w where w.brand.brand =:name and w.category.categoryId =:cate";
            Query query = session.createQuery(hql);
            query.setString("name", inputBean.getBrand());
            query.setString("cate", inputBean.getCategory());
            item = (List<WarehouseItem>) query.list();
            int size = item.size();
            for (int i = 0; i < size; i++) {
                inputBean.getItemMap().put(item.get(i).getItemId(), item.get(i).getItemName());
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
    public void getAgent(PurchaseOrderBean inputBean) throws Exception {
        List<Dealer> dealer;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from Dealer";
            Query query = session.createQuery(hql);
            dealer = (List<Dealer>) query.list();
            int size = dealer.size();
            for (int i = 0; i < size; i++) {
                inputBean.getAgentMap().put(dealer.get(i).getDealer(), dealer.get(i).getDeaerName());
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
    public void getBrands(PurchaseOrderBean inputBean) throws Exception {
        List<Brand> category;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from Brand";
            Query query = session.createQuery(hql);
            category = (List<Brand>) query.list();
            int size = category.size();
            for (int i = 0; i < size; i++) {
                inputBean.getBrandMap().put(category.get(i).getBrandId(), category.get(i).getBrand());
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
    public void getCategory(PurchaseOrderBean inputBean) throws Exception {
        List<Category> category;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            String hql = "from Category";
            Query query = session.createQuery(hql);
            category = (List<Category>) query.list();
            int size = category.size();
            for (int i = 0; i < size; i++) {
                inputBean.getCategoryMap().put(category.get(i).getCategoryId(), category.get(i).getCategory());
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
