@echo off
cd /d D:\Projects\Updated\eco-blog-backend-main\eco-blog-backend-main
java -Xmx512m -Xms256m -jar target\ecoblog-api.jar > app.log 2>&1
