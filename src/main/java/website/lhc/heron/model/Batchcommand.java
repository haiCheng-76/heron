package website.lhc.heron.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Batchcommand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createDate")
    private Date createdate;

    @Column(name = "isDelete")
    private Boolean isdelete;

    @Column(name = "updateDate")
    private Date updatedate;

    private Integer version;

    private String areas;

    @Column(name = "commandName")
    private String commandname;

    private Boolean executed;

    private String name;

    @Column(name = "taskId")
    private String taskid;

    private Integer duration;

    @Column(name = "autoUse")
    private Boolean autouse;

    private String remark;

    @Column(name = "approveStatus")
    private String approvestatus;

    private String code;

    @Column(name = "serialNumbers")
    private String serialnumbers;

    @Column(name = "userIds")
    private String userids;

    @Column(name = "commandBody")
    private String commandbody;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return createDate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return isDelete
     */
    public Boolean getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete
     */
    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return updateDate
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * @param updatedate
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return areas
     */
    public String getAreas() {
        return areas;
    }

    /**
     * @param areas
     */
    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    /**
     * @return commandName
     */
    public String getCommandname() {
        return commandname;
    }

    /**
     * @param commandname
     */
    public void setCommandname(String commandname) {
        this.commandname = commandname == null ? null : commandname.trim();
    }

    /**
     * @return executed
     */
    public Boolean getExecuted() {
        return executed;
    }

    /**
     * @param executed
     */
    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return taskId
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * @param taskid
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * @return duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return autoUse
     */
    public Boolean getAutouse() {
        return autouse;
    }

    /**
     * @param autouse
     */
    public void setAutouse(Boolean autouse) {
        this.autouse = autouse;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return approveStatus
     */
    public String getApprovestatus() {
        return approvestatus;
    }

    /**
     * @param approvestatus
     */
    public void setApprovestatus(String approvestatus) {
        this.approvestatus = approvestatus == null ? null : approvestatus.trim();
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return serialNumbers
     */
    public String getSerialnumbers() {
        return serialnumbers;
    }

    /**
     * @param serialnumbers
     */
    public void setSerialnumbers(String serialnumbers) {
        this.serialnumbers = serialnumbers == null ? null : serialnumbers.trim();
    }

    /**
     * @return userIds
     */
    public String getUserids() {
        return userids;
    }

    /**
     * @param userids
     */
    public void setUserids(String userids) {
        this.userids = userids == null ? null : userids.trim();
    }

    /**
     * @return commandBody
     */
    public String getCommandbody() {
        return commandbody;
    }

    /**
     * @param commandbody
     */
    public void setCommandbody(String commandbody) {
        this.commandbody = commandbody == null ? null : commandbody.trim();
    }
}
