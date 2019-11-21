package com.sg.imdb2jpa;

import com.sg.imdb2jpa.service.IMDBLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Hello world!
 */
@Component
public class App {
    private static Log log = LogFactory.getLog(App.class);

    @Autowired
    IMDBLoader imdbLoader;

    public static void main(String[] args) {
        StringJoiner sj = new StringJoiner(", ");
        for (String arg : args) {
            sj.add(arg);
        }
        log.debug("started with args: " + sj.toString());
        if (args.length == 0) {
            log.info("imdb data not found");
            return;
        }
        String minRate = "9.0";
        if (args.length == 1) {
            log.info("minimal rate not set, default value 9.0");
        } else {
            minRate = args[1];
        }

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        App app = context.getBean(App.class);
        app.loadIMDB(args[0], minRate);

        // let HSQLDB completes its work
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    public void loadIMDB(String sourceDir, String minRate) {
        try {
            URL url = new File(sourceDir).toURI().toURL();
            Double minRating = Optional.ofNullable(minRate)
                    .filter(StringUtils::isNotBlank)
                    .map(Double::parseDouble)
                    .orElse(null);
            imdbLoader.loadFromDir(url, minRating);
        } catch (Exception e) {
            log.error("error loading IMDB", e);
        }
    }
}
