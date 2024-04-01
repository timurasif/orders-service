##!/bin/bash
#
#echo "Setting up the DB..."
#"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -padmin -e "CREATE DATABASE IF NOT EXISTS OrderManagement;"
#"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -padmin OrderManagement -e "CREATE TABLE IF NOT EXISTS Orders (
#    id INT AUTO_INCREMENT PRIMARY KEY,
#    distance INT,
#    status VARCHAR(100)
#);"
#echo "DB setup complete."
#
#echo "Building the application..."
#mvn clean package
#echo "app built successfully."
#
#echo "Starting application..."
#java -jar target/demo-0.0.1-SNAPSHOT.jar


echo "Starting Docker..."
docker-compose up -d
echo "Docker started."