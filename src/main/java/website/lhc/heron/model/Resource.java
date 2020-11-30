package website.lhc.heron.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Resource implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件名称
     */
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 文件路径
     */
    @Column(name = "resource_path")
    private String resourcePath;

    /**
     * 文件类型
     */
    @Column(name = "resource_type")
    private String resourceType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 上传时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上传者
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 存储桶
     */
    @Column(name = "bucket_name")
    private String bucketName;

    private static final long serialVersionUID = 1L;
}
