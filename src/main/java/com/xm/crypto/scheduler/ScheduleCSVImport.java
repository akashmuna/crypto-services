package com.xm.crypto.scheduler;

import com.xm.crypto.service.CryptoService;
import com.xm.crypto.util.CryptoConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@EnableAsync
@Component
public class ScheduleCSVImport {

    private static Logger LOGGER = LoggerFactory.getLogger(ScheduleCSVImport.class);

    @Value("${cryptoservice.dirpath}")
    private String dirpath;

    @Value("${cryptoservice.env}")
    private String env;

    private CryptoService cryptoService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public ScheduleCSVImport(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }

    /**
     * Scheduled Async Task to import the CSV
     */
    @Async
    @Scheduled(fixedRate = CryptoConstants.SCHEDULE)
    public void scheduleFixedRateTaskAsync() {
        LOGGER.info("START scheduleFixedRateTaskAsync()....");
        LOGGER.info("Fixed rate task at time - " + System.currentTimeMillis() / 1000);
        try {
            if(env.equalsIgnoreCase(CryptoConstants.LOCAL)) {
                LOGGER.info("Directory Path Passed: " + dirpath);
                Resource resource = resourceLoader.getResource(dirpath);
                LOGGER.info("Spring Resource Path : " + resource.getURI().getPath());
                cryptoService.importCsvRecords(resource.getURI().getPath());
            }else if(env.equalsIgnoreCase(CryptoConstants.DOCKER)){
                LOGGER.info("Directory Path Passed: " + dirpath);
                cryptoService.importCsvRecords(dirpath);
            }
        }catch (IOException e){
            LOGGER.error("Exception while Retrieving the File",e.getMessage());
        }
        LOGGER.info("End scheduleFixedRateTaskAsync()....");
    }

}
