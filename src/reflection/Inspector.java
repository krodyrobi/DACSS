package reflection;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Inspector {
    File dir;

    public Inspector(File dir) {
        this.dir = dir;
    }

    public void report(String class_name) {
        try {
            URL url = dir.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);

            Class cls = cl.loadClass(class_name);
            System.out.println(inspect(cls));

            Class[] innerClasses = cls.getDeclaredClasses();
            System.out.println("\n\nFirst level Inner Classes:\n=====================\n");
            for(Class c: innerClasses)
                System.out.println(inspect(c));


        } catch (MalformedURLException ignored) {
        } catch (ClassNotFoundException ignored) {
            System.out.println("Class not found: " + class_name);
        }
    }

    private String inspect(Class cls) {
        String output = "";

        Constructor constructors[]  = cls.getDeclaredConstructors();
        Method methods[] = cls.getDeclaredMethods();
        Class[] theInterfaces = cls.getInterfaces();
        Field[] fields = cls.getDeclaredFields();
        Class[] parameterTypes;

        int modifiers = cls.getModifiers();
        boolean isAbstract = Modifier.isAbstract(modifiers);
        boolean isInterface = Modifier.isInterface(modifiers);
        boolean isPublic = Modifier.isPublic(modifiers);
        boolean isPrivate;

        output += (isPublic ? "public " : "") + (isInterface ? "interface " : ((isAbstract ? "abstract " : "") + "class "));
        output += cls.getSimpleName() + "\n";

        output += "Interfaces implemented:";
        for (Class c: theInterfaces) {
            String interfaceName = c.getSimpleName();
            output += " " + interfaceName;
        }
        output += "\nExtended class: ";
        output += cls.getSuperclass().getSimpleName() + "\n\n";

        output += "Fields:\n";
        for(Field f: fields) {
            modifiers = f.getModifiers();
            isPublic = Modifier.isPublic(modifiers);
            isPrivate = Modifier.isPrivate(modifiers);

            output += (isPrivate ? "private " : (isPublic ? "public " : ""))
                    + f.getType().getSimpleName() + " " + f.getName() + "\n";
        }


        output += "\nConstructors:\n";
        for(Constructor c: constructors) {
            modifiers = c.getModifiers();
            isPublic = Modifier.isPublic(modifiers);
            isPrivate = Modifier.isPrivate(modifiers);

            parameterTypes = c.getParameterTypes();

            output += (isPrivate ? "private " : (isPublic ? "public " : ""))
                    + c.getName() + "(";

            for(Class k: parameterTypes)
                output += k.getSimpleName() + ", ";

            if(parameterTypes.length > 0)
                output = output.substring(0, output.length() - 2); //remove last comma and space
            output += ")\n";
        }

        output += "\nMethods:\n";
        for(Method m: methods) {
            modifiers = m.getModifiers();
            isPublic = Modifier.isPublic(modifiers);
            isPrivate = Modifier.isPrivate(modifiers);

            parameterTypes = m.getParameterTypes();

            output += (isPrivate ? "private " : (isPublic ? "public " : ""))
                    + m.getName() + "(";

            for(Class k: parameterTypes)
                output += k.getSimpleName() + ", ";

            if(parameterTypes.length > 0)
                output = output.substring(0, output.length() - 2); //remove last comma and space
            output += "): " + m.getReturnType().getSimpleName() + "\n";
        }

        return output;
    }
}
