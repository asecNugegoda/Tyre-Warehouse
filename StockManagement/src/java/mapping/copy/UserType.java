package mapping.copy;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UserType generated by hbm2java
 */
@Entity
@Table(name="user_type"
    ,catalog="warehouse"
)
public class UserType  implements java.io.Serializable {


     private Integer userTypeId;
     private String userType;
     private Set<UserPrivilage> userPrivilages = new HashSet<UserPrivilage>(0);
     private Set<Customer> customers = new HashSet<Customer>(0);

    public UserType() {
    }

    public UserType(String userType, Set<UserPrivilage> userPrivilages, Set<Customer> customers) {
       this.userType = userType;
       this.userPrivilages = userPrivilages;
       this.customers = customers;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="USER_TYPE_ID", unique=true, nullable=false)
    public Integer getUserTypeId() {
        return this.userTypeId;
    }
    
    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    
    @Column(name="USER_TYPE", length=45)
    public String getUserType() {
        return this.userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="userType")
    public Set<UserPrivilage> getUserPrivilages() {
        return this.userPrivilages;
    }
    
    public void setUserPrivilages(Set<UserPrivilage> userPrivilages) {
        this.userPrivilages = userPrivilages;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="userType")
    public Set<Customer> getCustomers() {
        return this.customers;
    }
    
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }




}

