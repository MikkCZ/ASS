package ass.stankmic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class ReflexeTrida {
    
    public static void main(String[] args) throws Exception {
        ClassLoader cl = new CustomClassLoader();
        Class c = cl.loadClass("ass.stankmic.Trida");
        
        Object instance = c.newInstance();
        
        Field f1 = c.getDeclaredField("cislo");
        System.out.println(f1.getName() + " : " + f1.isAccessible());
        f1.setAccessible(true);
        System.out.println(f1.get(instance));
        f1.set(instance,5);
        System.out.println(f1.get(instance));
        
        Trida t = new Trida();
        System.out.println(t.getClass().getClassLoader());
        
        Method m = c.getDeclaredMethod("metoda");
        m.invoke(instance);
        System.out.println(c.getClassLoader());
    }
    
}
