package website.lhc.heron.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SystemMonitorVo implements Serializable {
    /**
     * CPU 核心数
     */
    @Column(name = "cpu_core_count")
    private Integer cpuCoreCount;

    /**
     * cpu系统使用率
     */
    @Column(name = "cpu_sys_usage")
    private String cpuSysUsage;

    /**
     * cpu用户使用率
     */
    @Column(name = "cpu_user_usage")
    private String cpuUserUsage;

    /**
     * cpu当前等待率
     */
    @Column(name = "cpu_wait_usage")
    private String cpuWaitUsage;

    /**
     * cpu当前使用率
     */
    @Column(name = "cpu_usage")
    private String cpuUsage;

    /**
     * 总内存
     */
    @Column(name = "memory_total")
    private String memoryTotalStr;

    /**
     * 内存使用量
     */
    @Column(name = "memory_used")
    private String memoryUsedStr;

    /**
     * 剩余内存
     */
    @Column(name = "memory_available")
    private String memoryAvailableStr;

    /**
     * 内存使用率
     */
    @Column(name = "memory_usage")
    private String memoryUsage;

    /**
     * jvm内存总量
     */
    @Column(name = "jvm_memory_total")
    private String jvmMemoryTotalStr;

    /**
     * jvm已使用内存
     */
    @Column(name = "jvm_memory_used")
    private String jvmMemoryUsedStr;

    /**
     * jvm剩余内存
     */
    @Column(name = "jvm_memory_avaliable")
    private String jvmMemoryAvaliableStr;

    /**
     * jvm内存使用率
     */
    @Column(name = "jvm_memory_usage")
    private String jvmMemoryUsage;

    /**
     * java版本
     */
    @Column(name = "java_version")
    private String javaVersion;

    /**
     * 操作系统名
     */
    @Column(name = "os_name")
    private String osName;

    /**
     * 系统架构
     */
    @Column(name = "os_arch")
    private String osArch;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
