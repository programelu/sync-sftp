This is a PoC to sync folders between a sftp-client and sftp-sever (push servers from client -> server and pull files from server to client)
The communication between sftp-client and sftp-server is done through sftp
Project uses spring integration

SyncLocalToRemoteJob -> take all the files in sftp-client container from local/out  and push them through sftp to sftp-server in remote/in
SyncRemoteToLocalJob -> take all the files in sftp-server container from remote/out and push them through sftp to sftp-client in local/in

Build
To build the project run ./build.sh

Start sftp-client, sftp-server
To start sftp-client and sftp-server using docker compose run:
docker compose up

Cleanup
To clean all the docker images and containers run ./clean.sh
