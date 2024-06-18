/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.util;

import com.myapp.constant.Configurations;
import com.myapp.constant.DbConfiguraitonBean;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.History;
import com.myapp.mapping.Module;
import com.myapp.mapping.Task;
import com.myapp.mapping.UserLogin;
import com.myapp.mapping.UserType;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jpos.iso.ISOUtil;
import org.w3c.dom.Document;

/**
 *
 * @author thilinath
 */
public class Util {

    public static boolean validateString(String text) throws Exception { //  VF0
//        return text.matches("^[a-zA-Z0-9\\s_-]+$");
        return text.matches("^[\\p{L} .'-]+$");
    }

    public static boolean validateVF0(String text) throws Exception { //  VF0
        return text.matches("^[a-zA-Z0-9\\s_-]+$");
    }

    public static boolean validateNAME(String text) throws Exception {
        return text.matches("^[a-zA-Z0-9 ]+$") && text.length() <= 50;
    }

    public static boolean validateNUMBER(String numericString) throws Exception {
        return numericString.matches("^[0-9]*$");
    }

    public static boolean validateEMAIL(String email) throws Exception {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") && email.length() <= 50;
    }

    public static boolean validatePHONENO(String numericString) throws Exception {
        return numericString.matches("^[0-9]*$") && numericString.length() <= 15;
    }

    public static boolean validateNIC(String nic) {
        return nic.matches("^[0-9]+[VX]?$") && nic.length() == 10;
    }

    public static boolean validateSPECIALCHAR(String specialChars) throws Exception {
        return specialChars.matches("[~@#$&!~]+");
    }

    public static boolean validateDESCRIPTION(String text) {
        return text.matches("^(.*/)?(?:$|(.+?)(?:(\\.[^.]*$)|$))") && text.length() <= 150;
    }

    public static boolean validateHEXA(String text) {
        return text.matches("[\\dA-Fa-f]+");
    }

    public static boolean validateURL(String text) {

        return text.matches("\\b(https?|ftp|file|ldap)://"
                + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
                + "*[-A-Za-z0-9+&@#/%=~_|]") && text.length() <= 150;
    }

    public static String generateHash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        return hashtext;
    }

    public static java.sql.Date getLocalDate() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        return date2;
    }

//    public static java.sql.Date getqlLocalDate() throws Exception {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = new Date();
//        java.sql.Date date2 = new java.sql.Date(d.getTime());
//        return date2;
//    }
    /* Added by danushka_r */
    public static Date getOrclLocalDate() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        java.util.Date date2 = new java.util.Date(d.getTime());
        return date2;
    }

    public static Map<Integer, String> getUserTypes() throws Exception {

        Map<Integer, String> usertypesmap = new HashMap<Integer, String>();
        List<UserType> tleMUserTypes = null;

        Session session = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            String sql = "from UserType";
            Query query = session.createQuery(sql);

            tleMUserTypes = query.list();

            for (int i = 0; i < tleMUserTypes.size(); i++) {
//                usertypesmap.put(tleUserTypes.get(i).getCode(), tleUserTypes.get(i).getDescription());
                usertypesmap.put(tleMUserTypes.get(i).getUserTypeId(), tleMUserTypes.get(i).getUserType());
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return usertypesmap;
    }

    public static Map<Integer, String> getBasicStatus() {
        Map<Integer, String> basicStatus = new HashMap<Integer, String>();
        basicStatus.put(StatusValues.ACTIVE, "Active");
        basicStatus.put(StatusValues.INACTIVE, "Inactive");
        return basicStatus;
    }

    public static String getDateFE() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();
        return sdf.format(date1);
    }

    //**********get files path in a directory****************
    public static List getPopulateFilesList(File dir) throws IOException {
        List<String> filesListInDir = new ArrayList<String>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filesListInDir.add(file.getAbsolutePath());
            } else {
                getPopulateFilesList(file);
            }
        }
        return filesListInDir;
    }

    public static String getTimestamoToDate(String datea) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date d = df.parse(datea);
        SimpleDateFormat fin = new SimpleDateFormat("yyyy-MM-dd");
        return fin.format(d);
    }

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static String getDatabaseConfig() {
        ResourceBundle rb = ResourceBundle.getBundle("context");

        return rb.getString("epic.tle.database.config");
        //  return "mysql";
    }

//    public static Map<Integer, String> getStatusValues(int from, int to) {
//        Session session = null;
//        List result = new ArrayList();
//        Map<Integer, String> status = new HashMap<Integer, String>();
//        try {
//            session = HibernateUtil.sessionFactory.openSession();
//            Query query = session.createQuery("from EpicTleStatus o order by o.code asc");
//            query.setFirstResult(from);
//            query.setMaxResults(to);
//            result = query.list();
//
//            if (HibernateUtil.dbConfig.equals("mysql")) {
//                for (com.epic.tle.mapping.EpicTleStatus stat : (List<com.epic.tle.mapping.EpicTleStatus>) result) {
//                    status.put(stat.getCode(), stat.getDescription());
//                }
//            } else {
//                for (com.epic.tle.orcel.mapping.EpicTleStatus stat : (List<com.epic.tle.orcel.mapping.EpicTleStatus>) result) {
//                    status.put(stat.getCode().intValue(), stat.getDescription());
//                }
//            }
//        } catch (Exception e) {
//            if (session != null) {
//                session.flush();
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
//        return status;
//    }
    public static List getMasterValues(int from, int to, String table) {
        Session session = null;
        List result = new ArrayList();
        try {
            session = HibernateUtil.sessionFactory.openSession();
            Query query = session.createQuery("from " + table + " o order by o.code asc");
            query.setFirstResult(from);
            query.setMaxResults(to);
            result = query.list();

        } catch (Exception e) {
            if (session != null) {
                session.flush();
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
        return result;
    }

    public static String tomorrowDate() {
        DateFormat yeartomorrw = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();

        return yeartomorrw.format(tomorrow);
    }

    public static String currentDate() {
        DateFormat yeartodate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return yeartodate.format(date);
    }

    public static DbConfiguraitonBean xmlConfiguraion(String path) throws Exception {

        DbConfiguraitonBean config = new DbConfiguraitonBean();
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            config.setDialect(doc.getElementsByTagName("dbdialect").item(0).getTextContent());
            config.setDriverClass(doc.getElementsByTagName("dbpooldriver").item(0).getTextContent());
            config.setShowSql("false");
            config.setUrl(doc.getElementsByTagName("dbpoolurl").item(0).getTextContent());
            config.setUsername(doc.getElementsByTagName("dbpoolusername").item(0).getTextContent());
            config.setPassword(removeASC(doc.getElementsByTagName("dbpoolpassword").item(0).getTextContent()));
            config.setZeroDateTimeBehavior("convertToNull");
            String pooltype = (doc.getElementsByTagName("dbpooltype").
                    item(0).getTextContent().equals("1")) ? "oracle" : "mysql";
            Configurations.DB_CONF = pooltype;
            config.setDbpooltype(pooltype);

            config.setPoolSize(doc.getElementsByTagName("dbpoolmaxcon").item(0).getTextContent());
            config.setDbpoolmax(doc.getElementsByTagName("dbpoolmax").item(0).getTextContent());
            config.setDbpoolmin(doc.getElementsByTagName("dbpoolmin").item(0).getTextContent());
            config.setDbpooltimeout(doc.getElementsByTagName("dbpoolcontimeout").item(0).getTextContent());
//            config.setDbpoolsource(doc.getElementsByTagName("dbpoolsource").item(0).getTextContent());
            Configurations.SERVER_NODE = Integer.parseInt(doc.getElementsByTagName("servernode").item(0).getTextContent());
//            Configurations.NODE_STATUS = doc.getElementsByTagName("servernode").item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return config;
    }

    public static String getWebLogPath(String require) {
        return Configurations.PATH_ROOT + Configurations.WebLogPath + require;
    }

    public static String removeASC(String asci) throws Exception {
        return new String(ISOUtil.hex2byte(new String(ISOUtil.hex2byte(asci)))).trim();
    }

    public static String getOSLogPath(String logpath) throws Exception {
        String path = null;
        try {
            String linuxPath = logpath + "/";
            String conForwordToBack = linuxPath.replace("/", "\\");
            if (System.getProperty("os.name").startsWith("Windows")) {
                path = conForwordToBack;
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                path = linuxPath;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return path;
    }

    public static void insertHistoryRecord(String logType, String modules, String operation, String remark) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        String ip = request.getRemoteAddr();
        History tleHistory = new History();
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.sessionFactory.openSession();
            tx = session.beginTransaction();
            UserLogin tleuser = (UserLogin) session.get(UserLogin.class, Integer.parseInt(logType));
            tleHistory.setUserLogin(tleuser);

            Module module = new Module();
            module.setModuleCode(Integer.parseInt(modules));
            tleHistory.setModule(module);

            Task opr = new Task();
            opr.setTaskId(Integer.parseInt(operation));
            tleHistory.setTask(opr);

            tleHistory.setRemark(remark);//add message
            tleHistory.setDate(Util.getLocalDate());

            session.save(tleHistory);
            tx.commit();
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

}
