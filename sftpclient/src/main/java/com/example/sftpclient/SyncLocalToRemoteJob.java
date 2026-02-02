package com.example.sftpclient;

import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class SyncLocalToRemoteJob {

    @Autowired
    SyncService syncService;

    @Value("${local.out.dir}")
    private String localOutDir;

    @Value("${remote.in.dir}")
    private String remoteInDir;

    @Scheduled(fixedRate = 10_000)
    public void syncLocalToRemote() {
        File localSource = new File(localOutDir);
        File[] files = localSource.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                syncService.uploadFile(file.getAbsolutePath(), remoteInDir);
            }
        }
    }
}
