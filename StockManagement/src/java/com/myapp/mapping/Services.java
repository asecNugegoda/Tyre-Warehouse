package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Services generated by hbm2java
 */
@Entity
@Table(name="services"
    ,catalog="warehouse"
)
public class Services  implements java.io.Serializable {


     private Integer serviceId;
     private String services;

    public Services() {
    }

    public Services(String services) {
       this.services = services;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="SERVICE_ID", unique=true, nullable=false)
    public Integer getServiceId() {
        return this.serviceId;
    }
    
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    
    @Column(name="SERVICES", length=45)
    public String getServices() {
        return this.services;
    }
    
    public void setServices(String services) {
        this.services = services;
    }




}


