package com.example.sftpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.MessageChannel;

@Configuration
public class SftpConfig {

    @Value("${sftp.server.host}")
    private String sftpServerHost;

    @Value("${sftp.server.port}")
    private String sftpServerPort;

    @Value("${sftp.server.username}")
    private String sftpServerUsername;

    @Value("${sftp.server.password}")
    private String sftpServerPassword;

    @Bean
    public SessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpServerHost);
        factory.setPort(Integer.parseInt(sftpServerPort));
            factory.setUser(sftpServerUsername);
        factory.setPassword(sftpServerPassword);
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    public MessageChannel sftpOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public SftpRemoteFileTemplate sftpTemplate(SessionFactory sf) {
        return new SftpRemoteFileTemplate(sf);
    }

    @Bean
    public SyncService getSftpService() {
        return new SyncService();
    }
}