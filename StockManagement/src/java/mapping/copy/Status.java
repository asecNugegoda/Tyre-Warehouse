package mapping.copy;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status generated by hbm2java
 */
@Entity
@Table(name="status"
    ,catalog="warehouse"
)
public class Status  implements java.io.Serializable {


     private int statusId;
     private String type;
     private String status;
     private Set<JobCard> jobCards = new HashSet<JobCard>(0);
     private Set<PurchasingOrder> purchasingOrders = new HashSet<PurchasingOrder>(0);
     private Set<WarehouseItem> warehouseItems = new HashSet<WarehouseItem>(0);
     private Set<JobCardItem> jobCardItems = new HashSet<JobCardItem>(0);
     private Set<JobCardServices> jobCardServiceses = new HashSet<JobCardServices>(0);

    public Status() {
    }

	
    public Status(int statusId) {
        this.statusId = statusId;
    }
    public Status(int statusId, String type, String status, Set<JobCard> jobCards, Set<PurchasingOrder> purchasingOrders, Set<WarehouseItem> warehouseItems, Set<JobCardItem> jobCardItems, Set<JobCardServices> jobCardServiceses) {
       this.statusId = statusId;
       this.type = type;
       this.status = status;
       this.jobCards = jobCards;
       this.purchasingOrders = purchasingOrders;
       this.warehouseItems = warehouseItems;
       this.jobCardItems = jobCardItems;
       this.jobCardServiceses = jobCardServiceses;
    }
   
     @Id 

    
    @Column(name="STATUS_ID", unique=true, nullable=false)
    public int getStatusId() {
        return this.statusId;
    }
    
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    
    @Column(name="TYPE", length=45)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="STATUS", length=45)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<JobCard> getJobCards() {
        return this.jobCards;
    }
    
    public void setJobCards(Set<JobCard> jobCards) {
        this.jobCards = jobCards;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<PurchasingOrder> getPurchasingOrders() {
        return this.purchasingOrders;
    }
    
    public void setPurchasingOrders(Set<PurchasingOrder> purchasingOrders) {
        this.purchasingOrders = purchasingOrders;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<WarehouseItem> getWarehouseItems() {
        return this.warehouseItems;
    }
    
    public void setWarehouseItems(Set<WarehouseItem> warehouseItems) {
        this.warehouseItems = warehouseItems;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<JobCardItem> getJobCardItems() {
        return this.jobCardItems;
    }
    
    public void setJobCardItems(Set<JobCardItem> jobCardItems) {
        this.jobCardItems = jobCardItems;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<JobCardServices> getJobCardServiceses() {
        return this.jobCardServiceses;
    }
    
    public void setJobCardServiceses(Set<JobCardServices> jobCardServiceses) {
        this.jobCardServiceses = jobCardServiceses;
    }




}

