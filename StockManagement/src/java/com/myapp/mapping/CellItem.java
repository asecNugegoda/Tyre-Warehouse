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
 * CellItem generated by hbm2java
 */
@Entity
@Table(name="cell_item"
    ,catalog="warehouse"
)
public class CellItem  implements java.io.Serializable {

     private Integer id;
     private Cell cell;
     private Double qnty;
     private StockItem stockItem;
     private String hr_cell;
     private String vr_cell;

    public CellItem() {
    }

	
    public CellItem(Cell cell, StockItem stockItem) {
        this.cell = cell;
        this.stockItem = stockItem;
    }
    public CellItem(Cell cell, Double qnty, StockItem stockItem, String hr_cell, String vr_cell) {
       this.cell = cell;
       this.qnty = qnty;
       this.stockItem = stockItem;
       this.hr_cell = hr_cell;
       this.vr_cell = vr_cell;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ID", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CELL_CELL", nullable=false)
    public Cell getCell() {
        return this.cell;
    }
    
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    
    @Column(name="QNTY", precision=22, scale=0)
    public Double getQnty() {
        return this.qnty;
    }
    
    public void setQnty(Double qnty) {
        this.qnty = qnty;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STOCK_ITEM_ID", nullable=false)
    public StockItem getStockItem() {
        return this.stockItem;
    }
    
    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
    @Column(name="HR_CELL", length=45)
    public String getHr_cell() {
        return hr_cell;
    }

    public void setHr_cell(String hr_cell) {
        this.hr_cell = hr_cell;
    }

    @Column(name="VR_CELL", length=45)
    public String getVr_cell() {
        return vr_cell;
    }

    public void setVr_cell(String vr_cell) {
        this.vr_cell = vr_cell;
    }


}


