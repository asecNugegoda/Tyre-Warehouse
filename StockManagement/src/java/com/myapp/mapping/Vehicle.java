package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Vehicle generated by hbm2java
 */
@Entity
@Table(name="vehicle"
    ,catalog="warehouse"
)
public class Vehicle  implements java.io.Serializable {


     private Integer vehicleId;
     private String brand;
     private Customer customer;
     private String vehicleNo;

    public Vehicle() {
    }

	
    public Vehicle(Customer customer) {
        this.customer = customer;
    }
    public Vehicle(String brand, Customer customer, String vehicleNo) {
       this.brand = brand;
       this.customer = customer;
       this.vehicleNo = vehicleNo;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="VEHICLE_ID", unique=true, nullable=false)
    public Integer getVehicleId() {
        return this.vehicleId;
    }
    
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    
    @Column(name="BRAND", length=45)
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    @Column(name="VEHICLE_NO", length=45)
    public String getVehicleNo() {
        return this.vehicleNo;
    }
    
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }




}


