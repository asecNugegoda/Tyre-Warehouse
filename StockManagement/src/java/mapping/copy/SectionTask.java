package mapping.copy;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SectionTask generated by hbm2java
 */
@Entity
@Table(name="section_task"
    ,catalog="warehouse"
)
public class SectionTask  implements java.io.Serializable {


     private SectionTaskId id;
     private Section section;
     private Task task;

    public SectionTask() {
    }

    public SectionTask(SectionTaskId id, Section section, Task task) {
       this.id = id;
       this.section = section;
       this.task = task;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="sectionSectionCode", column=@Column(name="SECTION_SECTION_CODE", nullable=false) ), 
        @AttributeOverride(name="taskTaskId", column=@Column(name="TASK_TASK_ID", nullable=false) ) } )
    public SectionTaskId getId() {
        return this.id;
    }
    
    public void setId(SectionTaskId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SECTION_SECTION_CODE", nullable=false, insertable=false, updatable=false)
    public Section getSection() {
        return this.section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TASK_TASK_ID", nullable=false, insertable=false, updatable=false)
    public Task getTask() {
        return this.task;
    }
    
    public void setTask(Task task) {
        this.task = task;
    }




}


