FROM openjdk:8

# 指定当前操作目录
WORKDIR /_data/workspace/ASE/

#容器启动后执行的操作
CMD java -jar ASE-docker.jar