package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dealer generated by hbm2java
 */
@Entity
@Table(name="dealer"
    ,catalog="warehouse"
)
public class Dealer  implements java.io.Serializable {


     private Integer dealer;
     private String adr1;
     private String adr2;
     private String city;
     private String contact;
     private String deaerName;

    public Dealer() {
    }

    public Dealer(String adr1, String adr2, String city, String contact, String deaerName) {
       this.adr1 = adr1;
       this.adr2 = adr2;
       this.city = city;
       this.contact = contact;
       this.deaerName = deaerName;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="DEALER", unique=true, nullable=false)
    public Integer getDealer() {
        return this.dealer;
    }
    
    public void setDealer(Integer dealer) {
        this.dealer = dealer;
    }

    
    @Column(name="ADR1", length=45)
    public String getAdr1() {
        return this.adr1;
    }
    
    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    
    @Column(name="ADR2", length=45)
    public String getAdr2() {
        return this.adr2;
    }
    
    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    
    @Column(name="CITY", length=45)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    
    @Column(name="CONTACT", length=45)
    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    
    @Column(name="DEAER_NAME", length=45)
    public String getDeaerName() {
        return this.deaerName;
    }
    
    public void setDeaerName(String deaerName) {
        this.deaerName = deaerName;
    }




}

