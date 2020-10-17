加入`maven-assembly-plugin`插件
---
1. 使用`mvn clean package -DskipTests -e`将文件打包，并生成`.tar.gz`和`.jar`两个文件
* `.tar.gz` 用于全量发布
* `.jar`用于增量发布
