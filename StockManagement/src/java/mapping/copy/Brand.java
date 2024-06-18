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
 * Brand generated by hbm2java
 */
@Entity
@Table(name="brand"
    ,catalog="warehouse"
)
public class Brand  implements java.io.Serializable {


     private Integer brandId;
     private String brand;
     private Set<WarehouseItem> warehouseItems = new HashSet<WarehouseItem>(0);

    public Brand() {
    }

    public Brand(String brand, Set<WarehouseItem> warehouseItems) {
       this.brand = brand;
       this.warehouseItems = warehouseItems;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="BRAND_ID", unique=true, nullable=false)
    public Integer getBrandId() {
        return this.brandId;
    }
    
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    
    @Column(name="BRAND", length=45)
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="brand")
    public Set<WarehouseItem> getWarehouseItems() {
        return this.warehouseItems;
    }
    
    public void setWarehouseItems(Set<WarehouseItem> warehouseItems) {
        this.warehouseItems = warehouseItems;
    }




}


