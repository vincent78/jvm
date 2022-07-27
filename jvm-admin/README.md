

# build 
生成镜像
> mvn dockerfile:build

# 启动命令
> docker run -itd -p 9091:9091 -m 512m --rm jvm-admin:1.0.0-SNAPSHOT

# 访问网址
> http://localhost:9091/monitor/info
