package mapping.copy;
// Generated Nov 10, 2018 4:53:52 PM by Hibernate Tools 4.3.1


import com.myapp.mapping.History;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Task generated by hbm2java
 */
@Entity
@Table(name="task"
    ,catalog="warehouse"
)
public class Task  implements java.io.Serializable {


     private int taskId;
     private String task;
     private Set<History> histories = new HashSet<History>(0);
     private Set<SectionTask> sectionTasks = new HashSet<SectionTask>(0);
     private Set<UserPrivilage> userPrivilages = new HashSet<UserPrivilage>(0);

    public Task() {
    }

	
    public Task(int taskId) {
        this.taskId = taskId;
    }
    public Task(int taskId, String task, Set<History> histories, Set<SectionTask> sectionTasks, Set<UserPrivilage> userPrivilages) {
       this.taskId = taskId;
       this.task = task;
       this.histories = histories;
       this.sectionTasks = sectionTasks;
       this.userPrivilages = userPrivilages;
    }
   
     @Id 

    
    @Column(name="TASK_ID", unique=true, nullable=false)
    public int getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    
    @Column(name="TASK", length=45)
    public String getTask() {
        return this.task;
    }
    
    public void setTask(String task) {
        this.task = task;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="task")
    public Set<History> getHistories() {
        return this.histories;
    }
    
    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="task")
    public Set<SectionTask> getSectionTasks() {
        return this.sectionTasks;
    }
    
    public void setSectionTasks(Set<SectionTask> sectionTasks) {
        this.sectionTasks = sectionTasks;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="task")
    public Set<UserPrivilage> getUserPrivilages() {
        return this.userPrivilages;
    }
    
    public void setUserPrivilages(Set<UserPrivilage> userPrivilages) {
        this.userPrivilages = userPrivilages;
    }




}


