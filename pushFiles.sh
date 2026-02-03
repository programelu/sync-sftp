echo "new file 1" > newfile1.txt
echo "new file 2" > newfile2.txt
docker cp newfile1.txt sftp-client:/app/local/out
docker cp newfile2.txt sftp-client:/app/local/out
rm -f newfile1.txt
rm -f newfile2.txt


