./clean.sh
cd sftpclient 
mvn clean package
cd ..
cd sftpserver
mvn clean package
cd ..

