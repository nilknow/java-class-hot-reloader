import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

public class Agentmain {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent started with agentArgs: " + agentArgs);
        System.out.println("Java agent is running!");

        Class<?>[] loadedClasses = inst.getAllLoadedClasses();
        for (Class<?> loadedClass : loadedClasses) {
            if (loadedClass.getName().contains("com.nilknow.simplestspringboot.HiController")) {
                System.out.println("Loaded class: " + loadedClass.getName());
                MyTransformer transformer = new MyTransformer("C:\\Users\\nilknow\\IdeaProjects\\nilknow-blog\\simplest-spring-boot\\build\\classes\\java\\main\\com\\nilknow\\simplestspringboot\\HiController.class");
                inst.addTransformer(transformer);
            }
        }
    }

    public static class MyTransformer implements ClassFileTransformer {
        private final String classPath;

        public MyTransformer(String classPath) {
            this.classPath = classPath;
        }

        public byte[] transform(ClassLoader loader, String className,
                                Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) {
            try {
                Path path = Paths.get(classPath);
                return Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
