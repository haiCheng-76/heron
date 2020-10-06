package webiste.lhc.heron.vo;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient("https://oss.haicheng.website/", "lhc", "1043153738");

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("hero");
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket("hero");
            }

            // 使用putObject上传一个文件到存储桶中。
            System.out.println("开始...");
            minioClient.putObject("hero", "aaa.zip", "E:\\apache-maven-3.6.3-bin.zip");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
