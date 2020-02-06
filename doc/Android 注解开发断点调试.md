## Android 注解开发断点调试

### 1.Edit Configurations

![iamge](../image/Configurations.png)

### 2.创建Remote

![image](../image/remote.png)

### 3.在命令行中执行：

```
./gradlew clean build -Dorg.gradle.daemon=false -Dorg.gradle.debug=true
```

### 4.打断点

在自定义的AbstractProcessor子类中打相应的断点。

### 5.开始运行debug

![image](../image/run.png)


