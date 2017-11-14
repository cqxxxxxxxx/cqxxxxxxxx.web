#!/usr/bin/env bash

cd /root/code/cqxxxxxxxx.web
git pull

#//运行maven容器，以指定的settimg.xml（里面修改了maven远程仓库的镜像，改用国内的，下载速度快）来执行命令编译并install代码，编译后的文件挂载到host下。
docker run -v ~/.m2:/root/.m2 -v "$PWD":/tmp/build --rm -w /tmp/build maven:3.3.3-jdk-8 mvn clean package  -s docker/settings.xml -Dmaven.test.skip=true
#//上一步为止，项目已经打包编译好了。因为是spring-boot项目 只要以jar方式运行就阔以了
#//根据传入的参数 创建个当前项目的 docker image，详见下面的dockerFile
docker build --build-arg APP_MODULE="cqxxxxxxxx.web" --build-arg APP_ENV="dev" -t cqxxxxxxxx.web .
#//如果以前有运行过，查询 golf-wx 这个旧的容器, 并停止 移除它
#//CID=$(docker ps -a | grep 'cqxxxxxxxx.web' | awk '{print $1}');
#//docker stop $CID
#//docker rm $CID
#//执行新起一个容器 -d:后台运行容器 -p:host跟contrainer中端口映射 -e:设置环境变量这里是设置容器的时区 也可以通过数据卷挂载到host时区文件来统一时区，看docker的学习简记
docker run -d -p 8080:9000 -v ~/logs:/logs --name webdemo -e TZ=Asia/Shanghai cqxxxxxxxx.web