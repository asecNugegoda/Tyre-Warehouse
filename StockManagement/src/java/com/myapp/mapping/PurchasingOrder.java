package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PurchasingOrder generated by hbm2java
 */
@Entity
@Table(name="purchasing_order"
    ,catalog="warehouse"
)
public class PurchasingOrder  implements java.io.Serializable {


     private Integer order;
     private Date date;
     private Status status;
     private Set<StockItem> stockItems = new HashSet<StockItem>(0);

    public PurchasingOrder() {
    }

	
    public PurchasingOrder(Status status) {
        this.status = status;
    }
    public PurchasingOrder(Date date, Status status, Set<StockItem> stockItems) {
       this.date = date;
       this.status = status;
       this.stockItems = stockItems;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ORDER", unique=true, nullable=false)
    public Integer getOrder() {
        return this.order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE", length=26)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_STATUS_ID", nullable=false)
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="purchasingOrder")
    public Set<StockItem> getStockItems() {
        return this.stockItems;
    }
    
    public void setStockItems(Set<StockItem> stockItems) {
        this.stockItems = stockItems;
    }




}

