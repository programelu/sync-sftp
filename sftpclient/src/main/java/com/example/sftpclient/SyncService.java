package com.example.sftpclient;

import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SyncService {

    @Autowired
    SftpRemoteFileTemplate sftpTemplate;

    public void uploadFile(String localPath, String remotePath) {
        String fileName = Path.of(localPath).getFileName().toString();
        sftpTemplate.execute(session -> {
            session.write(Files.newInputStream(Paths.get(localPath)), remotePath + "/" + fileName);
            return null;
        });
    }

    public void downloadFile(String remotePath, String localPath) {
        System.out.println("remotePath:::" + remotePath);
        String fileName = Path.of(remotePath).getFileName().toString();
        sftpTemplate.execute(session -> {
            try (OutputStream os = Files.newOutputStream(
                    Paths.get(localPath + "/" + fileName),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )) {
                session.read(remotePath, os);
            }
            return null;
        });
    }

    public List<SftpClient.DirEntry> listRemoteFolderWithDetails(String remoteDir) {
        SftpClient.DirEntry[] entries =
                sftpTemplate.execute(session -> session.list(remoteDir));
        return entries == null ? List.of() : List.of(entries);
    }
}

