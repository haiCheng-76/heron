package webiste.lhc.heron.framework.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class MinIoService {

    @Value(value = "${minio.url}")
    private String url;

    @Value(value = "${minio.accessKey}")
    private String accessKey;

    @Value(value = "${minio.secretKey}")
    private String secretKey;

    /**
     * 获取MinioClient
     *
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    private MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(url, accessKey, secretKey, true);
    }

    /**
     * minio文件上传
     *
     * @param bucketName  存储桶
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param contentType 文件类型
     * @param size        文件大小
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InsufficientDataException
     * @throws InvalidArgumentException
     * @throws InternalException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws XmlPullParserException
     * @throws ErrorResponseException
     * @throws RegionConflictException
     * @throws InvalidObjectPrefixException
     */
    public String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType, long size) throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, InvalidObjectPrefixException {
        MinioClient client = minioClient();
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
            // 设置存储桶策略
            client.setBucketPolicy(bucketName, "*", PolicyType.READ_ONLY);
        }
        client.putObject(bucketName, fileName, inputStream, size, contentType);
        return client.getObjectUrl(bucketName, fileName);
    }
}
