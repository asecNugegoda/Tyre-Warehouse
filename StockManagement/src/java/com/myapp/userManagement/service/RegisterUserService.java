/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.userManagement.service;

import com.myapp.login.bean.SessionUserBean;
import com.myapp.mapping.Customer;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Section;
import com.myapp.mapping.UserLogin;
import com.myapp.mapping.UserType;
import com.myapp.userManagement.bean.RegisterUserBean;
import com.myapp.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class RegisterUserService implements RegisterUserServiceInf {

    SessionUserBean sub = (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");

    public Map<Integer, String> getUserTypes() throws Exception {
        Map<Integer, String> usertypesmap = new HashMap<Integer, String>();

        List<UserType> tleUserTypes = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from UserType";
            Query query = session.createQuery(sql);
            tleUserTypes = query.list();

            for (int i = 0; i < tleUserTypes.size(); i++) {
                usertypesmap.put(tleUserTypes.get(i).getUserTypeId(), tleUserTypes.get(i).getUserType());
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
        return usertypesmap;
    }

//    public Map<Integer, String> getStatusTypes() throws Exception {
//
//        Map<Integer, String> statusTypesMap = new HashMap<Integer, String>();
//
//        List<EpicTleStatus> tleStatusTypes = null;
//        Session session = null;
//        try {
//            session = HibernateUtil.sessionFactory.openSession();
//            String sql = "from EpicTleStatus wu where wu.code between 1 and 2";
//            Query query = session.createQuery(sql);
////            query.setInteger("code", 1);
//            tleStatusTypes = query.list();
//
//            for (int i = 0; i < tleStatusTypes.size(); i++) {
//                statusTypesMap.put(tleStatusTypes.get(i).getCode(), tleStatusTypes.get(i).getDescription());
//            }
//        } catch (Exception e) {
//            if (session != null) {
//                session.close();
//                session = null;
//            }
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//                session = null;
//            }
//        }
//        return statusTypesMap;
//
//    }
    @Override
    public RegisterUserBean getPagePath(String page, RegisterUserBean inputBean) {

        Session session = null;
        String pagePath = "";

        try {

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Section setion = (Section) session.get(Section.class, Integer.parseInt(page));
            String mod = setion.getModule().getDescription();
            String sect = setion.getSection();

            inputBean.setModule(mod);
            inputBean.setSection(sect);

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

        return inputBean;
    }

    public boolean checkUserName(String userName) throws Exception {
        boolean isUsername = false;
        List<UserLogin> tleWebUser = null;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from UserLogin wu where wu.userName =:username";
            query = session.createQuery(sql);
            query.setString("username", userName);
            tleWebUser = query.list();
            if (0 < tleWebUser.size()) {
                int statusCode = 0;
                for (int i = 0; i < tleWebUser.size(); i++) {
                    statusCode = tleWebUser.get(i).getStatus();
                    if (statusCode == 1) {
                        isUsername = true;
                    }
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
                session.close();
                session = null;
            }
        }
        return isUsername;
    }

    public boolean addUser(RegisterUserBean inputBean) throws Exception {
        boolean isAddUser = false;
        Customer customer = null;
        UserLogin login = null;
        Session session = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            customer = new Customer();
            login = new UserLogin();

            customer.setFirstName(inputBean.getFirstName());
            customer.setLastName(inputBean.getLastName());
            customer.setContact(inputBean.getContact());
            customer.setAdr1(inputBean.getAdr1());
            customer.setAdr2(inputBean.getAdr2());
            customer.setCity(inputBean.getCity());
            UserType tlestatus = (UserType) session.get(UserType.class, Integer.parseInt(inputBean.getUserType()));
            customer.setUserType(tlestatus);
//            customer.setUserType();
            session.beginTransaction();
            session.save(customer);

            if (Integer.parseInt(inputBean.getUserType()) != 5) {
                login.setUserName(inputBean.getUserName());
                login.setPassword(Util.generateHash(inputBean.getPassword()));
                login.setStatus(1);
                login.setCustomer(customer);
                session.save(login);
            }

            session.getTransaction().commit();
            isAddUser = true;
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
        return isAddUser;
    }

    //should edit
    public List<RegisterUserBean> loadUsers(RegisterUserBean inputBean, int max, int first, String orderBy) throws Exception {

        List<RegisterUserBean> dataList = new ArrayList<RegisterUserBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by wu.firstName desc ";
            }

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
//            String sqlCount = "select count(username) from EpicTleUser wu where wu.epicTleStatus.code =:statuscode and wu.name LIKE :name " + orderBy;
            String sqlCount = "select count(customerId) from Customer wu where wu.firstName LIKE :fname or wu.lastName LIKE :lname " + orderBy;
            Query queryCount = session.createQuery(sqlCount);
//            queryCount.setInteger("statuscode", Status.ACTIVE);
            queryCount.setParameter("fname", "%" + inputBean.getSearchName() + "%");
            queryCount.setParameter("lname", "%" + inputBean.getSearchName() + "%");
            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

//               String sqlSearch = "from EpicTleUser wu where wu.epicTleStatus.code =:statuscode and wu.name LIKE :name " + orderBy;
                String sqlSearch = "from Customer wu where wu.firstName LIKE :fname or wu.lastName LIKE :lname " + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
//                querySearch.setInteger("statuscode", Status.ACTIVE);
                querySearch.setParameter("fname", "%" + inputBean.getSearchName() + "%");
                querySearch.setParameter("lname", "%" + inputBean.getSearchName() + "%");
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    RegisterUserBean databean = new RegisterUserBean();
                    Customer objBean = (Customer) it.next();

                    try {
                        databean.setUserTypeId(objBean.getCustomerId().toString());
                    } catch (NullPointerException npe) {
                        databean.setUserTypeId("0");
                    }
                    try {
                        databean.setFirstName(objBean.getFirstName());
                    } catch (NullPointerException npe) {
                        databean.setFirstName("--");
                    }
                    try {
                        databean.setLastName(objBean.getLastName());
                    } catch (NullPointerException npe) {
                        databean.setLastName("--");
                    }
                    try {
                        databean.setContact(objBean.getContact());
                    } catch (NullPointerException npe) {
                        databean.setContact("--");
                    }
                    try {
                        databean.setAdr1(objBean.getAdr1());
                    } catch (NullPointerException npe) {
                        databean.setAdr1("--");
                    }
                    try {
                        databean.setAdr2(objBean.getAdr2());
                    } catch (NullPointerException npe) {
                        databean.setAdr2("--");
                    }
                    try {
                        databean.setCity(objBean.getCity());
                    } catch (NullPointerException npe) {
                        databean.setCity("--");
                    }
                    try {
                        databean.setUserType(objBean.getUserType().getUserType());
                    } catch (NullPointerException npe) {
                        databean.setUserType("--");
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

    public boolean deleteUser(String duserName) throws Exception {
        boolean isUserDeleted = false;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "delete Customer wu"
                    + "  where wu.customerId =:username";
            query = session.createQuery(sql);

            query.setString("username", duserName);
            int result = query.executeUpdate();
            if (1 == result) {
                isUserDeleted = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
        return isUserDeleted;
    }
    @Override
    public void findUser(RegisterUserBean inputBean) throws Exception {
        List<Customer> finfuserlist = null;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Customer wu where wu.customerId =:cusId";
            query = session.createQuery(sql);
            query.setString("cusId", inputBean.getUpName());
            finfuserlist = query.list();

            if (0 < finfuserlist.size()) {
                inputBean.setUpCustID(finfuserlist.get(0).getCustomerId() + "");
                inputBean.setUpfirstName(finfuserlist.get(0).getFirstName());
                inputBean.setUplastName(finfuserlist.get(0).getLastName());
                inputBean.setUpadr1(finfuserlist.get(0).getAdr1());
                inputBean.setUpadr2(finfuserlist.get(0).getAdr2());
                inputBean.setUpcity(finfuserlist.get(0).getCity());
                inputBean.setUpcontact(finfuserlist.get(0).getContact());
                inputBean.setUpuserType(finfuserlist.get(0).getUserType().getUserTypeId() + "");

                if (!inputBean.getUpuserType().equals("5")) {
                    UserLogin loginDetails = (UserLogin) session.get(UserLogin.class, Integer.parseInt(inputBean.getUpuserType()));
                    inputBean.setUpuserName(loginDetails.getUserName());
                } else {
                    inputBean.setUpuserName("");
                }

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

    public boolean updateUser(RegisterUserBean inputBean) throws Exception {
        boolean isUpdated = false;
        Session session = null;
        Query query = null;
        Query queryLogin = null;
        List<Customer> tleWebUser = null;
        List<UserLogin> userLogin = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "from Customer wu where wu.customerId =:cusId";
            String sqllogin = "from UserLogin wu where wu.customer =:cus";
            query = session.createQuery(sql);
            query.setString("cusId", inputBean.getUpCustID());

            queryLogin = session.createQuery(sqllogin);
            queryLogin.setString("cus", inputBean.getUpCustID());
            userLogin = queryLogin.list();
            tleWebUser = query.list();
            if (tleWebUser.size() > 0) {
                tleWebUser.get(0).setFirstName(inputBean.getUpfirstName());
                tleWebUser.get(0).setLastName(inputBean.getUplastName());
                tleWebUser.get(0).setContact(inputBean.getUpcontact());
                tleWebUser.get(0).setAdr1(inputBean.getUpadr1());
                tleWebUser.get(0).setAdr2(inputBean.getUpadr2());
                tleWebUser.get(0).setCity(inputBean.getUpcity());

                if (userLogin.size() > 0) {
                    userLogin.get(0).setUserName(inputBean.getUpuserName());
                    if (inputBean.isIsChecked()) {
                        userLogin.get(0).setPassword(Util.generateHash(inputBean.getUpNewPw()));
                        session.save(userLogin.get(0));
                    }
                }
//                tu.setUserLoginId(inputBean.getUpuserTypeId());
//                tleWebUser.get(0).setEpicTleUserProfile(tu);
//                if (inputBean.isIsChecked()) {
//                    tleWebUser.get(0).setPassword(Util.generateHash(inputBean.getUpNewPw()));
//                }
                session.save(tleWebUser.get(0));
                session.getTransaction().commit();
                isUpdated = true;
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
                session.close();
                session = null;
            }
        }
        return isUpdated;
    }

    @Override
    public Map<Integer, String> getStatusTypes() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
