package ass.stankmic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class ReflexeTrezor {
    
    public static void main(String[] args) throws Exception {
        ClassLoader cl = new CustomClassLoader();
        Class c = cl.loadClass("Trezor");
        
        Object instance = c.newInstance();
        
        Field first = c.getDeclaredField("firstLocked");
        first.setAccessible(true);
        first.set(instance,false);
        Field second = c.getDeclaredField("secondLocked");
        second.setAccessible(true);
        second.set(instance, false);
        
        Method m = c.getDeclaredMethod("open");
        m.invoke(instance);
    }
    
}
