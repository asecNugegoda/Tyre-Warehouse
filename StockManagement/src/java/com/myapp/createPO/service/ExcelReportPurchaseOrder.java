package com.myapp.createPO.service;

import com.myapp.createPO.bean.PurchaseOrderBean;
import com.myapp.mapping.HibernateUtil;
import com.myapp.mapping.ItemOrder;
import com.myapp.mapping.OrderDealer;
import com.myapp.mapping.PurchasingOrder;
import com.myapp.util.ExcelCommon;
import com.myapp.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author nipun_t
 */
public class ExcelReportPurchaseOrder {

    private static final int columnCount = 1;
    private static final int headerRowCount = 0;

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static Object generateExcelReport(PurchaseOrderBean inputBean) throws Exception {
        Session session = null;
        Object returnObject = null;
        Query queryCount, querySearch2, querySearch3, query = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            String directory = Util.getOSLogPath("/tmp/systemPurchasingOrder");

            File file = new File(directory);
            deleteDir(file);
            session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();

            int count = 0;

            String sqlCount = "select count(status) from PurchasingOrder po where po.status.statusId =:stat and (po.date >= :beginDate and po.date <= :endDate)";
            queryCount = session.createQuery(sqlCount);
            queryCount.setInteger("stat", 3);

            Date beginDate = dateFormatter.parse(inputBean.getFromdate());
            queryCount.setParameter("beginDate", beginDate);
            Date endDate = dateFormatter.parse(inputBean.getTodate());
            endDate.setDate(endDate.getDate() + 1);
            queryCount.setParameter("endDate", endDate);

            if (queryCount.uniqueResult() != null) {
                count = ((Number) queryCount.uniqueResult()).intValue();
                if (count == 0) {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("System Purchasing Order");
                    sheet.autoSizeColumn(count);
                    ExcelReportPurchaseOrder.createExcelTableHeaderSection(workbook, 0);
                    returnObject = workbook;
                }
            }
            if (count > 0) {
                long maxRow = Long.parseLong("10000");
                int currRow = headerRowCount;
                int fileCount = 0;
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("System Purchasing Order");
                currRow = ExcelReportPurchaseOrder.createExcelTableHeaderSection(workbook, currRow);
                int selectRow = 10000;
                int numberOfTimes = count / selectRow;
                if ((count % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                String sqlSearch = "from PurchasingOrder po where po.status.statusId =:stat and (po.date >= :beginDate and po.date <= :endDate)";
                query = session.createQuery(sqlSearch);
                query.setInteger("stat", 3);

                beginDate = dateFormatter.parse(inputBean.getFromdate());
                query.setParameter("beginDate", beginDate);
                endDate = dateFormatter.parse(inputBean.getTodate());
                endDate.setDate(endDate.getDate() + 1);
                query.setParameter("endDate", endDate);

                Iterator itSearch = query.iterate();
                query.setFirstResult(from);
                query.setMaxResults(selectRow);
                for (int i = 0; i < numberOfTimes; i++) {
                    while (itSearch.hasNext()) {
                        PurchaseOrderBean databean = new PurchaseOrderBean();
                        PurchasingOrder objBean = (PurchasingOrder) itSearch.next();

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
                        for (int j = 0; j < it2.size(); j++) {

                            try {
                                databean.setDealer(it2.get(0).getDealer().getDeaerName());
                            } catch (Exception e) {
                                databean.setDealer("---");
                            }

                        }
                        
                        String sqlSearch3 = "from ItemOrder po where po.purchasingOrder.order =:stat";
                        querySearch3 = session.createQuery(sqlSearch3);
                        querySearch3.setInteger("stat", objBean.getOrder());

                        List<ItemOrder> it3 = querySearch3.list();
                        for (int j = 0; j < it3.size(); j++) {

                            try {
                                databean.setBrand(it3.get(0).getWarehouseItem().getBrand().getBrand());
                            } catch (Exception e) {
                                databean.setBrand("---");
                            }
                            try {
                                databean.setQnty(it3.get(0).getQnty());
                            } catch (Exception e) {
                                databean.setQnty(0);
                            }
                            try {
                                databean.setCategory(it3.get(0).getWarehouseItem().getCategory().getCategory());
                            } catch (Exception e) {
                                databean.setCategory("---");
                            }
                            try {
                                databean.setItem(it3.get(0).getWarehouseItem().getItemName());
                            } catch (Exception e) {
                                databean.setItem("---");
                            }

                        }

                        databean.setFullCount(count);
                        if (currRow + 1 > maxRow) {
                            fileCount++;
                            ExcelReportPurchaseOrder.writeTemporaryFile(workbook, fileCount, directory);
                            workbook = ExcelReportPurchaseOrder.createExcelTopSection(inputBean);
                            sheet = workbook.getSheetAt(0);
                            currRow = headerRowCount;
                            ExcelReportPurchaseOrder.createExcelTableHeaderSection(workbook, currRow);
                        }
                        currRow = ExcelReportPurchaseOrder.createExcelTableBodySection(workbook, databean, currRow, listrownumber);
                        listrownumber++;
                    }

                    from = from + selectRow;
                }

                if (fileCount > 0) {
                    fileCount++;
                    ExcelReportPurchaseOrder.writeTemporaryFile(workbook, fileCount, directory);
                    ByteArrayOutputStream outputStream = ExcelCommon.zipFiles(file.listFiles());
                    returnObject = outputStream;
                } else {
                    for (int i = 0; i < columnCount; i++) {
                        //to auto size all column in the sheet
                        sheet.autoSizeColumn(i);
                    }
                    returnObject = workbook;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.clear();
                session.getTransaction().commit();
                session.close();

            }
        }
        return returnObject;
    }

    private static XSSFWorkbook createExcelTopSection(PurchaseOrderBean inputBean) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("System Purchasing Order");
        return workbook;
    }

    private static int createExcelTableHeaderSection(XSSFWorkbook workbook, int currrow) throws Exception {
        XSSFCellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue("Purchasing Order ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("Agent");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Brand");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Type");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Item");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Qnty");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }

    private static void writeTemporaryFile(XSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i < columnCount; i++) {
                //to auto size all column in the sheet
                sheet.autoSizeColumn(i);
            }

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getCanonicalPath());
            if (fileCount > 0) {
                file = new File(directory + File.separator + "Syatem Purchasing Order_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Syatem Purchasing Order.xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private static int createExcelTableBodySection(XSSFWorkbook workbook, PurchaseOrderBean dataBean, int currrow, int rownumber) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue(dataBean.getOrder_id());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getDealer());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getBrand());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getCategory());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getItem());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getQnty());
        cell.setCellStyle(rowColumnCell);

        return currrow;
    }
}
