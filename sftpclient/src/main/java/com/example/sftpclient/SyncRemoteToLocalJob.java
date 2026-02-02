package com.example.sftpclient;

import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SyncRemoteToLocalJob {

    @Autowired
    SyncService syncService;

    @Value("${remote.out.dir}")
    private String remoteOutDir;

    @Value("${local.in.dir}")
    private String localInDir;

    @Scheduled(fixedRate = 10_000)
    public void syncRemoteToLocal() {
        List<SftpClient.DirEntry> listOfDirEntries = syncService.listRemoteFolderWithDetails(remoteOutDir);
        for (SftpClient.DirEntry remoteDirEntry : listOfDirEntries) {
            if (remoteDirEntry.getAttributes().isRegularFile()) {
                System.out.println("download " + remoteOutDir + "/" + remoteDirEntry.getFilename());
                syncService.downloadFile( remoteOutDir + "/" + remoteDirEntry.getFilename(), localInDir);
            }
        }
    }
}
