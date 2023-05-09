import java.lang.reflect.Field;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Injector {
    private Properties properties;

    public Injector(String propertiesFilePath) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
            System.out.println("Loaded properties: " + properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T inject(T object) {
        Class<?> objectClass = object.getClass();

        for (Field field : objectClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = field.getType().getName();
                String implementationClassName = properties.getProperty(interfaceName);

                try {
                    Class<?> implementationClass = Class.forName(implementationClassName);
                    Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    field.set(object, implementationInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }
}
