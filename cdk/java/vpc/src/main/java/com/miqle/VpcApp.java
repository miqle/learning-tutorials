package com.miqle;

import com.miqle.config.AppConfig;
import com.miqle.config.NatProviderConfig;
import com.miqle.config.VpcSubnetConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import software.amazon.awscdk.core.App;

@SpringBootApplication
@Import({AppConfig.class,
        NatProviderConfig.class,
        VpcSubnetConfiguration.class})
public class VpcApp
  implements CommandLineRunner {

    @Autowired
    private App app;
 
    private static Logger LOG = LoggerFactory
      .getLogger(VpcApp.class);
 
    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(VpcApp.class, args);
        LOG.info("APPLICATION FINISHED");
    }
  
    @Override
    public void run(String... args) {
        app.synth();
    }
}