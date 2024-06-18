/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.login.service;

import com.myapp.login.bean.HomeValues;
import com.myapp.login.bean.ModuleBean;
import com.myapp.login.bean.PageBean;
import com.myapp.login.bean.TaskBean;
import com.myapp.login.bean.UserLoginBean;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.UserLogin;
import com.myapp.mapping.UserPrivilage;
import com.myapp.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author thilinath
 */
public class LoginService implements LoginServiceInf{

    @Override
    public UserLoginBean getDbUserPassword(UserLoginBean userLoginBean) throws Exception {
        Session session = null;
        session = HibernateUtil.sessionFactory.openSession();
        try {
            UserLogin initBean = (UserLogin) session.createCriteria(UserLogin.class)
                    .add(Restrictions.eq("userName", userLoginBean.getUserName())) // the leading wild card can become a problem since it cannot be indexed by the DB!
                    .uniqueResult();

            userLoginBean.setDBuserName(initBean.getUserName());
            userLoginBean.setDBname(initBean.getCustomer().getFirstName()+" "+initBean.getCustomer().getLastName());
            userLoginBean.setDBpassword(initBean.getPassword());
//            userLoginBean.setDBuserStatus(initBean.getEpicTleStatus().getCode());
            userLoginBean.setDBuserappCode(initBean.getCustomer().getUserType().getUserTypeId());
            userLoginBean.setUserRole(initBean.getCustomer().getUserType().getUserType());
            userLoginBean.setId(initBean.getUserLoginId());
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
        return userLoginBean;
    }

    @Override
    public boolean checkUsername(UserLoginBean userLoginBean) throws Exception {
        
        Session session = null;
        session = HibernateUtil.sessionFactory.openSession();
        boolean result = false;
        try {

            UserLogin initBean = (UserLogin) session.createCriteria(UserLogin.class)
                    .add(Restrictions.eq("userName", userLoginBean.getUserName())) // the leading wild card can become a problem since it cannot be indexed by the DB!
                    .uniqueResult();
            result = (initBean!=null);
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
        return result;
        
    }

    @Override
    public boolean varifilogin(UserLoginBean userLoginBean) throws Exception {
        if (Util.generateHash(userLoginBean.getPassword()).equals(userLoginBean.getDBpassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Integer> getUserprofilePageidList(int dbUserprofile) throws Exception {
        List<Integer> pageList = new ArrayList<Integer>();
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "select pp.section.sectionCode from UserPrivilage as pp where pp.userType.userTypeId =:code";
            Query query = session.createQuery(sql).setInteger("code", dbUserprofile);
            
            pageList = query.list();
            
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
        return pageList;
    }

    @Override
    public Map<ModuleBean, List<PageBean>> getModulePageByUser(int dBuserappCode) throws Exception {
        HashMap<ModuleBean, List<PageBean>> moduleBean = new HashMap<ModuleBean, List<PageBean>>();
        List<UserPrivilage> moduleList;
        Session session = null;
        Transaction tx = null;
        Query query;
        try {
            HibernateUtil hibernateInit = new HibernateUtil();
            hibernateInit.initialize();
            session = HibernateUtil.sessionFactory.openSession();
            tx = session.beginTransaction();
            String sql = "from UserPrivilage as pp where pp.userType.userTypeId =:code";
            query = session.createQuery(sql).setInteger("code", dBuserappCode);
            moduleList = query.list();
            
            Set<ModuleBean> hs = new HashSet<ModuleBean>();
            for (int i = 0; i < moduleList.size(); i++) {
                ModuleBean mbean = new ModuleBean();
                mbean.setMODULE_ID(moduleList.get(i).getModule().getModuleCode()+"");
                mbean.setMODULE_NAME(moduleList.get(i).getModule().getDescription());
                hs.add(mbean);
            }

            moduleBean = new HashMap<ModuleBean, List<PageBean>>();
            for (ModuleBean bean : hs) {
                List<PageBean> pages = new ArrayList<PageBean>();
                for (int j = 0; j < moduleList.size(); j++) {

                    if (String.valueOf(moduleList.get(j).getModule().getModuleCode()).equals(bean.getMODULE_ID())) {
                        PageBean pageBean = new PageBean();

                        pageBean.setMODULE(moduleList.get(j).getModule().getModuleCode()+"");
                        pageBean.setPAGE_ID(moduleList.get(j).getSection().getSectionCode()+"");
                        pageBean.setPAGE_NAME(moduleList.get(j).getSection().getSection());
                        pageBean.setPAGE_URL(moduleList.get(j).getSection().getUrl());
                        pages.add(pageBean);
                    }
                }
                moduleBean.put(bean, pages);
            }
            tx.commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            throw ex;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
        return moduleBean;

    }

    @Override
    public void getHomeValues(HomeValues homeValues) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<ModuleBean, List<PageBean>> getModulePageTaskByUser(int dBuserappCode) {
        HashMap<ModuleBean, List<PageBean>> moduleBean = new HashMap<ModuleBean, List<PageBean>>();
        List<UserPrivilage> moduleList;
        Session session = null;
        Transaction tx = null;
        Query query;
        try {
            HibernateUtil hibernateInit = new HibernateUtil();
            hibernateInit.initialize();
            session = HibernateUtil.sessionFactory.openSession();
            tx = session.beginTransaction();
            String sql = "from UserPrivilage as pp where pp.userType.userTypeId =:code";
            query = session.createQuery(sql).setInteger("code", dBuserappCode);
            moduleList = query.list();
            Set<ModuleBean> hs = new HashSet<ModuleBean>();
            for (int i = 0; i < moduleList.size(); i++) {
                ModuleBean mbean = new ModuleBean();
                mbean.setMODULE_ID(moduleList.get(i).getModule().getModuleCode()+"");
                mbean.setMODULE_NAME(moduleList.get(i).getModule().getDescription());
                hs.add(mbean);
            }

            moduleBean = new HashMap<ModuleBean, List<PageBean>>();
            for (ModuleBean bean : hs) {
                List<PageBean> pages = new ArrayList<PageBean>();
                for (int j = 0; j < moduleList.size(); j++) {
                    if (String.valueOf(moduleList.get(j).getModule().getModuleCode()).equals(bean.getMODULE_ID())) {
                        PageBean pageBean = new PageBean();

                        pageBean.setMODULE(moduleList.get(j).getModule().getModuleCode()+"");
                        pageBean.setPAGE_ID(moduleList.get(j).getSection().getSectionCode()+"");
                        pageBean.setPAGE_NAME(moduleList.get(j).getSection().getSection());
                        pageBean.setPAGE_URL(moduleList.get(j).getSection().getUrl());
                        pages.add(pageBean);
                    }
                }
                moduleBean.put(bean, pages);
            }
            tx.commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            throw ex;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
        return moduleBean;
    }

    @Override
    public HashMap<String, List<TaskBean>> getAllPageTask(int profileID) throws Exception {
        
        HashMap<String, List<TaskBean>> pageTaskMap = new HashMap<String, List<TaskBean>>();
        List<UserPrivilage> pageList = new ArrayList<UserPrivilage>();
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            // String sql = "select pmp.epicTlePage.pageId from EpicTleProfilePrivilage as pmp where pmp.epicTleUserProfile.code =:code";
            String sql = "from UserPrivilage as pp where pp.userType.userTypeId =:code";
            Query query = session.createQuery(sql).setInteger("code", profileID);
            pageList = query.list();

            Set<String> section = new HashSet<String>();
            String secName = "";
            for (int i = 0; i < pageList.size(); i++) {
                if (!secName.equals(pageList.get(i).getSection().getSectionCode())) {
                    section.add(pageList.get(i).getSection().getSectionCode()+"");
                    secName = pageList.get(i).getSection().getSection();
                }
            }

            for (String strSec : section) {
                List<TaskBean> tasklist = new ArrayList<TaskBean>();
                for (int i = 0; i < pageList.size(); i++) {
                    if (String.valueOf(pageList.get(i).getSection().getSectionCode()).equals(strSec)) {
                        TaskBean bean = new TaskBean();
                        bean.setTASK_ID(pageList.get(i).getTask().getTaskId()+"");
                        bean.setTASK_NAME(pageList.get(i).getTask().getTask());
                        tasklist.add(bean);
                    }
                }
                pageTaskMap.put(strSec, tasklist);
            }
        } catch (Exception e) {
            if (session != null) {
                session.flush();
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

        return pageTaskMap;
    }

    @Override
    public void getProcessingData(UserLoginBean userLoginBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getFilterData(UserLoginBean userLoginBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
