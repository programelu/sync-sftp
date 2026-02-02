package com.example.sftpserver;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SftpServerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread serverThread = new Thread(() -> {
            // start your SFTP server here
            System.out.println("SFTP Server running...");
            // simulate blocking server
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        serverThread.setDaemon(false); // important! JVM waits for this thread
        serverThread.start();
    }
}

