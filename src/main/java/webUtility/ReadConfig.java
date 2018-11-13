package webUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import static webUtility.LogFileMessage.takeScreenShot;
import org.slf4j.Logger;
public class ReadConfig {

    private static Properties prop = null;
    private static String localDirPath = System.getProperty("user.dir");
    Logger log ;

    public ReadConfig() {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/configuration/frameworkConfig.properties");
            FileInputStream fileRead = new FileInputStream(file);
            prop = new Properties();
            prop.load(fileRead);
        } catch (FileNotFoundException e) {
            takeScreenShot(e);
            e.getStackTrace();
        } catch (IOException e) {
            takeScreenShot(e);
            e.getStackTrace();
        }
    }

    public String getPropvalue(String propertyKey) {
        return prop.getProperty(propertyKey);
    }

    public String getChromePropvalue() {
        return (localDirPath + "/src/driver/chrome/chromedriver.exe");
    }

}
