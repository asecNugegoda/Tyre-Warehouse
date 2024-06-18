/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.profile.service;


import com.myapp.mapping.Customer;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.Module;
import com.myapp.mapping.Section;
import com.myapp.mapping.SectionTask;
import com.myapp.mapping.Task;
import com.myapp.mapping.UserLogin;
import com.myapp.mapping.UserPrivilage;
import com.myapp.mapping.UserType;
import com.myapp.profile.bean.UserProfileBean;
import com.myapp.profile.bean.UserProfileInputBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thilinath
 */
public class UserProfileService implements UserProfileInf {

    @Override
    public int insertUserProfile(UserProfileInputBean inputBean) throws Exception {
        UserType userProfile = null;
        Session session = null;
        int newProfileId =0;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            int count = 0;
            String sqlCount = "select max(userTypeId) from UserType";
            Query queryCount = session.createQuery(sqlCount);
            Iterator itCount = queryCount.iterate();
            int decimal = (int) itCount.next();
            count = decimal;

            userProfile = new UserType();
            userProfile.setUserType(inputBean.getProfilename());

            session.beginTransaction();
            newProfileId = (int) session.save(userProfile);
            session.getTransaction().commit();

        } catch (Exception ex) {
            if (session != null) {
                session.close();
                session = null;
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }

        }
        return (int) newProfileId;
    }

    @Override
    public List<UserProfileBean> loadSearchUserProfile(UserProfileInputBean inputBean, String orderBy, int first, int max) throws Exception {
        List<UserProfileBean> dataList = new ArrayList<UserProfileBean>();
        Session session = null;

        try {

            long count = 0;

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query queryCount;
            Query querySearch;
            if (inputBean.getSearchName().equals("")) {
                String sqlCount = "select count(userTypeId) from UserType wu" + orderBy;
                queryCount = session.createQuery(sqlCount);
            } else {
                String sqlCount = "select count(userTypeId) from UserType wu where wu.userType LIKE:des" + orderBy;
                queryCount = session.createQuery(sqlCount);
                queryCount.setString("des", "%" + inputBean.getSearchName() + "%");
            }

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                if (inputBean.getSearchName().equals("")) {
                    String sqlSearch = "from UserType wu" + orderBy;
                    querySearch = session.createQuery(sqlSearch);
                } else {
                    String sqlSearch = "from UserType wu where wu.userType LIKE :des " + orderBy;
                    querySearch = session.createQuery(sqlSearch);
                    querySearch.setString("des", "%" + inputBean.getSearchName() + "%");
                }

                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);
                Iterator it = querySearch.iterate();
                while (it.hasNext()) {

                    UserProfileBean databean = new UserProfileBean();
                    UserType objBean = (UserType) it.next();
                    try {
                        databean.setProfileID(objBean.getUserTypeId());
                    } catch (NullPointerException npe) {
                        databean.setProfileID(0);
                    }
                    try {
                        databean.setName(objBean.getUserType());
                    } catch (Exception npe) {
                        databean.setName("--");
                    }

                    databean.setFullCount(count);

                    dataList.add(databean);
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
    public boolean userRoleDelete(UserProfileInputBean Bean) throws Exception {
        boolean isUserDeleted = false;
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "delete UserPrivilage wu"
                    + "  where wu.userType.userTypeId =:code";
            query = session.createQuery(sql);

            query.setInteger("code", Bean.getProfileID());
            int result = query.executeUpdate();
            if (1 == result) {
                isUserDeleted = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
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

        try {
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "delete UserType wu"
                    + "  where wu.userTypeId =:code";
            query = session.createQuery(sql);

            query.setInteger("code", Bean.getProfileID());
            int result = query.executeUpdate();
            if (1 == result) {
                isUserDeleted = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
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
        return isUserDeleted;
    }

    @Override
    public boolean isAnyUserUsingThisProfile(UserProfileInputBean Bean) throws Exception {
        boolean ok = false;
        Session session = null;
        try {
            List<Customer> loginUser = null;

            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Customer wu where wu.userType.userTypeId =:code";
            Query query = session.createQuery(sql);
            query.setInteger("code", Bean.getProfileID());
            loginUser = query.list();

            if (loginUser.size() > 0) {
                ok = true;
            }
        } catch (Exception e) {
            if (session != null) {
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

        return ok;
    }

    @Override
    public void getmodulemap(UserProfileInputBean inputBean) throws Exception {
        List<Module> module = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Module ";
            Query query = session.createQuery(sql);
            module = query.list();

            for (int i = 0; i < module.size(); i++) {
                inputBean.getModulesMap().put(module.get(i).getModuleCode()+"", module.get(i).getDescription());
            }

        } catch (Exception ex) {
            if (session != null) {
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
    }

    @Override
    public void getmodulePagemap(UserProfileInputBean inputBean) throws Exception {
        List<Section> section = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from Section se where se.module.moduleCode=:code ";
            Query query = session.createQuery(sql);
            query.setString("code", inputBean.getModules());
            section = query.list();

            for (Section sect : section) {
                inputBean.getAllPageMap().put(sect.getSectionCode()+"", sect.getSection());
            }

        } catch (Exception ex) {
            if (session != null) {
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
    }

    @Override
    public void getPageTaskmap(UserProfileInputBean inputBean) throws Exception {
        List<Section> section = null;
        List<UserPrivilage> privilages = null;
        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
//            String sql = "from EpicTleModule ";
//            Query query = session.createQuery(sql);
            String sql = "from Section se where se.sectionCode=:sectionId ";
            Query query = session.createQuery(sql);
            query.setString("sectionId", inputBean.getPages());
            section = query.list();

//            for (int i = 0; i < tleSection.size(); i++){
//                for(){
//                
//                }
//            }
            for (Section sec : section) {
                for (Object taskobj : sec.getSectionTasks()) {
                    SectionTask task = (SectionTask) taskobj;
                    inputBean.getUnSelectedTaskMap().put(task.getTask().getTaskId()+"", task.getTask().getTask());
                }

            }
            if (inputBean.getFlag().equalsIgnoreCase("update")) {

                sql = "from UserPrivilage se where se.section.sectionCode=:sectionId and se.userType.userTypeId=:code";
                query = session.createQuery(sql);
                query.setString("sectionId", inputBean.getPages());
                query.setInteger("code", inputBean.getUpProfileID());
                privilages = query.list();

                for (UserPrivilage privilage : privilages) {
                    inputBean.getSelectedTaskMap().put(privilage.getTask().getTaskId()+"", privilage.getTask().getTask());
                }
            }

        } catch (Exception ex) {
            if (session != null) {
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
    }

    @Override
    public boolean alredyInsertProfile(String profileName) throws Exception {
        boolean already = false;
        try {
            List<UserType> page = null;
            Session session = null;
            try {

                session = HibernateUtil.sessionFactory.openSession();
                String sql = "from UserType au where au.userType=:des ";
                Query query = session.createQuery(sql);
                query.setString("des", profileName);
                page = query.list();

                if (!page.isEmpty()) {
                    already = true;
                }
            } catch (Exception e) {
                if (session != null) {
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
        } catch (Exception e) {
            throw e;
        }
        return already;
    }

    @Override
    public void insertUserProfileModule(int profileID, UserProfileInputBean input) throws Exception {
        String[] taskArray = input.getTaskString().split("\\|");
        UserPrivilage privilages = new UserPrivilage();
        Session session = null;
        try {
            for (int i = 0; i < taskArray.length; i++) {
                if (!taskArray[i].isEmpty()) {
                    session = HibernateUtil.sessionFactory.openSession();
                    UserType profile = new UserType();
                    profile.setUserTypeId(profileID);

                    Module module = new Module();
                    module.setModuleCode(Integer.parseInt(input.getModules()));

                    Section section = new Section();
                    section.setSectionCode(Integer.parseInt(input.getPages()));

                    Task task = new Task();
                    task.setTaskId(Integer.parseInt(taskArray[i]));

                    privilages.setUserType(profile);
                    privilages.setModule(module);
                    privilages.setSection(section);
                    privilages.setTask(task);

                    session.beginTransaction();
                    session.save(privilages);
                    session.getTransaction().commit();

                }
            }

        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
    }

    @Override
    public void getUpdateData(UserProfileInputBean inputBean) throws Exception {
        Session session = null;
        Query query = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            UserType userProfile = (UserType) session.get(UserType.class, inputBean.getUpProfileID());

            if (userProfile != null) {
                inputBean.setUpName(userProfile.getUserType());
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
                session.flush();
                session.close();
                session = null;
            }
        }
    }

    @Override
    public boolean updateUserProfile(UserProfileInputBean inputBean) throws Exception {
        boolean ok = false;
        Session session = null;
        Query query = null;

        try {

            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            String sql = "DELETE UserPrivilage wu where wu.userType.userTypeId =:code and wu.section.sectionCode =:sectionId";
            query = session.createQuery(sql);

            query.setInteger("code", inputBean.getUpProfileID());
            query.setString("sectionId", inputBean.getPages());
            int result = query.executeUpdate();
            session.getTransaction().commit();
            result = 1;
            if (1 == result) {
                List<UserType> UserProfiles = new ArrayList<UserType>();
                session = HibernateUtil.sessionFactory.openSession();
                UserType UserProfile = (UserType) session.get(UserType.class, inputBean.getUpProfileID());
                UserProfile.setUserType(inputBean.getUpName());
                
                session.beginTransaction();
                session.save(UserProfile);
                session.getTransaction().commit();
                ok = true;
            } else {
                ok = false;
            }

            if (ok) {
                String[] taskArray = inputBean.getTaskString().split("\\|");
                UserPrivilage privilages = new UserPrivilage();
                for (int i = 0; i < taskArray.length; i++) {
                    if (!taskArray[i].isEmpty()) {
                        session = HibernateUtil.sessionFactory.openSession();
                        UserType profile = new UserType();
                        profile.setUserTypeId(inputBean.getUpProfileID());

                        Module module = new Module();
                        module.setModuleCode(Integer.parseInt(inputBean.getModules()));

                        Section section = new Section();
                        section.setSectionCode(Integer.parseInt(inputBean.getPages()));

                        Task task = new Task();
                        task.setTaskId(Integer.parseInt(taskArray[i]));

                        privilages.setUserType(profile);
                        privilages.setModule(module);
                        privilages.setSection(section);
                        privilages.setTask(task);

                        session.beginTransaction();
                        session.save(privilages);
                        session.getTransaction().commit();

                    }
                }
            }

        } catch (Exception e) {
            if (session != null) {
                e.printStackTrace();
                session.getTransaction().rollback();
                session.close();
                session = null;
            }
            ok = false;
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                session = null;
            }
        }
        return ok;
    }

    @Override
    public UserProfileInputBean getPagePath(String page, UserProfileInputBean inputBean) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
