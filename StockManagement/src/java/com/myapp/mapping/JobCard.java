package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * JobCard generated by hbm2java
 */
@Entity
@Table(name="job_card"
    ,catalog="warehouse"
)
public class JobCard  implements java.io.Serializable {


     private Integer jobCardId;
     private Customer customer;
     private Date jobDate;
     private Status status;
     private UserLogin userLogin;

    public JobCard() {
    }

	
    public JobCard(Customer customer, Status status, UserLogin userLogin) {
        this.customer = customer;
        this.status = status;
        this.userLogin = userLogin;
    }
    public JobCard(Customer customer, Date jobDate, Status status, UserLogin userLogin) {
       this.customer = customer;
       this.jobDate = jobDate;
       this.status = status;
       this.userLogin = userLogin;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="JOB_CARD_ID", unique=true, nullable=false)
    public Integer getJobCardId() {
        return this.jobCardId;
    }
    
    public void setJobCardId(Integer jobCardId) {
        this.jobCardId = jobCardId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="JOB_DATE", length=26)
    public Date getJobDate() {
        return this.jobDate;
    }
    
    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", nullable=false)
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_LOGIN_ID", nullable=false)
    public UserLogin getUserLogin() {
        return this.userLogin;
    }
    
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }




}

