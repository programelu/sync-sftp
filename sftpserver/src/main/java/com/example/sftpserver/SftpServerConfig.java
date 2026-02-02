package com.example.sftpserver;

import jakarta.annotation.PostConstruct;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Configuration
public class SftpServerConfig implements BeanFactoryPostProcessor, DisposableBean {

    private SshServer sshServer;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        sshServer = SshServer.setUpDefaultServer();
        sshServer.setPort(2222); // change to 22 in prod (needs root or CAP_NET_BIND_SERVICE)

        sshServer.setKeyPairProvider(
                new SimpleGeneratorHostKeyProvider(Path.of("hostkey.ser"))
        );

        sshServer.setSubsystemFactories(
                List.of(new SftpSubsystemFactory())
        );

        sshServer.setPasswordAuthenticator(passwordAuthenticator());

        try {
            sshServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SFTP server started on port 2222");
    }

    @Override
    public void destroy() throws Exception {
        if (sshServer != null) {
            sshServer.stop();
        }
    }

    private PasswordAuthenticator passwordAuthenticator() {
        return (username, password, session) ->
                "testuser".equals(username) && "testpass".equals(password);
    }


}

