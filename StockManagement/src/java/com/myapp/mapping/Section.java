package com.myapp.mapping;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Section generated by hbm2java
 */
@Entity
@Table(name="section"
    ,catalog="warehouse"
)
public class Section  implements java.io.Serializable {


     private int sectionCode;
     private Module module;
     private String section;
     private Set<SectionTask> sectionTasks = new HashSet<SectionTask>(0);
     private String url;
     private Set<UserPrivilage> userPrivilages = new HashSet<UserPrivilage>(0);

    public Section() {
    }

	
    public Section(int sectionCode, Module module) {
        this.sectionCode = sectionCode;
        this.module = module;
    }
    public Section(int sectionCode, Module module, String section, Set<SectionTask> sectionTasks, String url, Set<UserPrivilage> userPrivilages) {
       this.sectionCode = sectionCode;
       this.module = module;
       this.section = section;
       this.sectionTasks = sectionTasks;
       this.url = url;
       this.userPrivilages = userPrivilages;
    }
   
     @Id 

    
    @Column(name="SECTION_CODE", unique=true, nullable=false)
    public int getSectionCode() {
        return this.sectionCode;
    }
    
    public void setSectionCode(int sectionCode) {
        this.sectionCode = sectionCode;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MODULE_CODE", nullable=false)
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(Module module) {
        this.module = module;
    }

    
    @Column(name="SECTION", length=45)
    public String getSection() {
        return this.section;
    }
    
    public void setSection(String section) {
        this.section = section;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="section")
    public Set<SectionTask> getSectionTasks() {
        return this.sectionTasks;
    }
    
    public void setSectionTasks(Set<SectionTask> sectionTasks) {
        this.sectionTasks = sectionTasks;
    }

    
    @Column(name="URL", length=45)
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="section")
    public Set<UserPrivilage> getUserPrivilages() {
        return this.userPrivilages;
    }
    
    public void setUserPrivilages(Set<UserPrivilage> userPrivilages) {
        this.userPrivilages = userPrivilages;
    }




}


