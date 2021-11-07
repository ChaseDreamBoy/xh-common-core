# xh-common-core

Java common tool classes


# 在其他 java 模块调用操作过程

1. 在项目目录新建 lib 文件夹，并把 `common-core-1.0.0.jar` 放到该 lib 文件夹下面
2. 在该项目的 pom.xml 里面加入本依赖
   ```
        <dependency>
            <groupId>com.xh</groupId>
            <artifactId>common-core</artifactId>
            <version>1.0.0</version>
            <scope>system</scope>
            <systemPath>${basedir}/lib/common-core-1.0.0.jar</systemPath>
        </dependency>
   ```
3. idea 设置(不设置会报错): `idea >> File >> Project Structure >> Project Settings >> Libraries >> 把该 jar 包加进来`
4. 就可以使用了


