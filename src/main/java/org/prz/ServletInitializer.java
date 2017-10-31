package org.prz;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Uruchamia aplikację, gdy uruchamiana na serwerze
 *
 * @author Tomasz Janas
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application
                .sources(PraktykiApplication.class)
                .bannerMode(Banner.Mode.OFF);
    }

}
