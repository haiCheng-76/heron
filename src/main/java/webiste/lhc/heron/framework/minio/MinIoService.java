package webiste.lhc.heron.framework.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
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


    private MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(url, accessKey, secretKey, true);
    }

    public String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType, long size) throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException {
        MinioClient client = minioClient();
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
        client.putObject(bucketName, fileName, inputStream, size, contentType);
        return client.getObjectUrl(bucketName, fileName);
    }
}
