package configuration;


import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Singleton
public class Configuration {

    private final Properties p;

    private Configuration() {
        p = new Properties();
        try (InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream(ConstantesConfig.MYSQL_PROPERTIES_XML)) {
            p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String getProperty(String clave) {
        return p.getProperty(clave);
    }

}