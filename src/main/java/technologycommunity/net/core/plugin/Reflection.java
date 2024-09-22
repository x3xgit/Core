package technologycommunity.net.core.plugin;

import org.jetbrains.annotations.NotNull;
import technologycommunity.net.core.annotations.AutoRegister;
import technologycommunity.net.core.command.CoreCommand;
import technologycommunity.net.core.listener.internal.CoreListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class Reflection {
    public static class Scanner {
        public static void scanAndRegister(final Class<?> coreParent) {
            final @NotNull List<Class<?>> classes = Reflection.findValidClasses(coreParent);

            for (final Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(AutoRegister.class)) {
                    final String clazzName = clazz.getSimpleName();

                    if (CoreCommand.class.isAssignableFrom(clazz)) {
                        try {
                            final CoreCommand command = (CoreCommand) clazz.getDeclaredConstructor().newInstance();
                            if (!command.isRegistered())
                                command.register();
                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                            Core.getInstance().getCoreLogger().error("Could not register command %s via AutoRegister.".formatted(clazzName));
                        }
                    } else if (CoreListener.class.isAssignableFrom(clazz)) {
                        try {
                            ((CoreListener) clazz.getDeclaredConstructor().newInstance()).register();
                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                            Core.getInstance().getCoreLogger().error("Could not register listener %s via AutoRegister.".formatted(clazzName));
                        }
                    }
                }
            }
        }
    }

    private static List<Class<?>> findValidClasses(final @NotNull Class<?> c) {
        final List<Class<?>> classes = new ArrayList<>();

        final Pattern anonymousClassPattern = Pattern.compile("\\w+\\$[0-9]$");

        try (final JarFile file = new JarFile(Core.getSource(c))) {
            for (final Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                final JarEntry jar = entry.nextElement();
                final String name = jar.getName().replace("/", ".");

                if (!name.endsWith(".class"))
                    continue;

                final String className = name.substring(0, name.length() - 6);
                final Class<?> clazz;

                try {
                    clazz = Core.class.getClassLoader().loadClass(className);
                } catch (final ClassFormatError | VerifyError | NoClassDefFoundError | ClassNotFoundException | IncompatibleClassChangeError error) {
                    continue;
                }

                if (!Modifier.isAbstract(clazz.getModifiers()) && !anonymousClassPattern.matcher(className).find())
                    classes.add(clazz);
            }

        } catch (final Throwable ignored) {

        }

        return classes;
    }
}
