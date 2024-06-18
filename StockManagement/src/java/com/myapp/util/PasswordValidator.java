/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.util;

import com.myapp.userManagement.bean.PasswordPolicyBean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thilinath
 */
public class PasswordValidator {

    public synchronized String validatePassword(String password) {

        char characters[] = password.toCharArray();
        PasswordPolicyBean passPolcyBean = getPasswordPolicys();

        if (null != passPolcyBean) {

            if (Integer.parseInt(passPolcyBean.getMin_len()) <= characters.length && Integer.parseInt(passPolcyBean.getMax_len()) >= characters.length) {

                if (isValidSpecialChars(characters, passPolcyBean)) {

                    if (Integer.parseInt(passPolcyBean.getMin_upercase()) <= numOfUpperCases(characters) && Integer.parseInt(passPolcyBean.getMin_lowcase()) <= numOflowerCases(characters)) {

                        if (Integer.parseInt(passPolcyBean.getMin_numerics().trim()) <= numOfNumerics(characters)) {

//                            if (Integer.parseInt(passPolcyBean.getMin_spcl_chars()) <= numOfSpecialChars(characters, passPolcyBean) && Integer.parseInt(passPolcyBean.getMax_spcl_chars()) >= numOfSpecialChars(characters, passPolcyBean)) {
                            return "success";
//                            } else {
//                                return "Invalid number of Special Characters. Min is " + passPolcyBean.getMin_numerics() +" Max is "+passPolcyBean.getMax_spcl_chars()+ " values.";
//                            }
                        } else {
                            return "Invalid Password, Your password should be " + passPolcyBean.getMin_numerics() + " or more numeric values.";
                        }

                    } else {
                        return "Invalid Password, Your password should be " + passPolcyBean.getMin_upercase() + " or more Uppercase letters and " + passPolcyBean.getMin_lowcase() + " or more Lowercase letters.";
                    }

                } else {
                    return "Invalid Password, Use no of special charactors in range ( " + passPolcyBean.getMin_spcl_chars() + " , " + passPolcyBean.getMax_spcl_chars() + " ) : Allowed special characters ( " + passPolcyBean.getAllowspecialcharacters() + " ) ";
                }

            } else {
                return "Invalid Password legnth. Please Insert " + passPolcyBean.getMin_len() + " to " + passPolcyBean.getMax_len() + " Values.";
            }
        } else {
            return "Error Please Create Password Policy First";
        }
    }

    private PasswordPolicyBean getPasswordPolicys() {
        PasswordPolicyBean pass = null;
        try {

            pass = this.viewPasswordPolicyData();

        } catch (Exception e) {

            try {
                LogFileCreator.writeErrorToLog(e);
            } catch (Exception ex) {
                Logger.getLogger(PasswordValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pass;
    }

    public PasswordPolicyBean viewPasswordPolicyData() throws Exception {
//        HibernateUtil hibernateInit = new HibernateUtil();
//        PasswordPolicyBean passwordPolicyBean = new PasswordPolicyBean();
//        PasswordPolicyFactory passwordPolicyFactory = new PasswordPolicyFactory();
//        return passwordPolicyFactory.getPasswordPolicyServiceInf().viewPasswordPolicyDetails(passwordPolicyBean);
            return null;
    }

    private int numOfUpperCases(char[] charArray) {
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i])) {
                numbers++;
            }
        }
        return numbers;
    }

    private int numOflowerCases(char[] charArray) {
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLowerCase(charArray[i])) {
                numbers++;
            }
        }
        return numbers;
    }

    private int numOfNumerics(char[] charArray) {
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (String.valueOf(charArray[i]).matches("[0-9]")) {
                numbers++;
            }
        }
        return numbers;
    }

    private boolean isValidSpecialChars(char[] charArray, PasswordPolicyBean passpolBean) {

        int numbers = 0, numOfDoler = 0, counter = 0;
        String validVal = "", secondValidator = "$";
        char chars[] = passpolBean.getAllowspecialcharacters().toCharArray();
        numOfDoler = passpolBean.getAllowspecialcharacters().compareTo("$");

        for (int i = 0; i < chars.length; i++) {
            if (!"$".equals(String.valueOf(chars[i]))) {

                if ((counter + 1) != (chars.length - numOfDoler)) {
                    validVal += String.valueOf(chars[i]) + "|";

                } else {
                    validVal += String.valueOf(chars[i]);

                }
                counter++;

            }

        }

        for (int i = 0; i < charArray.length; i++) {
            if (String.valueOf(charArray[i]).matches(validVal)) {
                numbers++;
            } else if (String.valueOf(charArray[i]).matches("[" + secondValidator + "]")) {
                numbers++;
            }
        }

        if (numbers >= Integer.parseInt(passpolBean.getMin_spcl_chars()) && numbers <= Integer.parseInt(passpolBean.getMax_spcl_chars())) {
            return true;
        } else {
            return false;
        }
    }

    private int numOfSpecialChars(char[] charArray, PasswordPolicyBean passpolBean) {
        String spclChars = passpolBean.getAllowspecialcharacters();
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (String.valueOf(charArray[i]).matches("[" + spclChars + "]")) {
                numbers++;
            }
        }
        return numbers;
    }

}
