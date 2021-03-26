package Main.utils;

import java.util.HashMap;
import java.util.Map;

public class
ServiceLocater {

    // Hier wird eine neue hashmap instanziiert um einen string mit einem object zu verknüpfen
    private static HashMap<String, Object> services = new HashMap<String, Object>();

    // methode um den namen des services und die instanz zu übergeben
    public static void RegisterService(Object instance, String instance_name) {
        services.put(instance_name, instance);
    }
// hier wird durch die hashmap iterriert und falls ein service vorhanden ist wird er in tmp gespeichert
    public static Object GetService(String instance_name) {
        if (services.size() == 0) return null;
        Object tmp = null;
        for (Map.Entry<String, Object> o : services.entrySet()) {
            if (o.getKey().equals(instance_name)) {
                tmp = o.getValue();
            }
        }
        return tmp;
    }
}
