package fun.fiver;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String currentDirectory = System.getProperty("user.dir");
        File sourceFile = new File(currentDirectory, args[0]);

        Runtime rt = Runtime.getRuntime();
        String[] commands = {"javac", sourceFile.getAbsolutePath()};
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        URI u = sourceFile.getParentFile().toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(urlClassLoader, u.toURL());

        ClassReader classReader = new ClassReader(args[0].replaceFirst("[.][^.]+$", ""));
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        List<MethodNode> methodNodeList = classNode.methods;

        /*

        for (MethodNode methodNode : methodNodeList) {
            InsnList insnList = methodNode.instructions;
            for (AbstractInsnNode abstractInsnNode : insnList) {
                System.out.println(abstractInsnNode);
            }
        }

         */

        Map<Integer, String> lineNumberToSource = formLineNumberToSourceMap(sourceFile);

        for (MethodNode methodNode : methodNodeList) {
            System.out.println();
            System.out.println(methodNode.name);
            methodNode.accept(new CFGMethodVisitor(Opcodes.ASM4, lineNumberToSource));
        }


    }

    private static Map<Integer, String> formLineNumberToSourceMap(File sourceFile) {

        Map<Integer, String> lineNumberToSource = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(sourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                i += 1;
                lineNumberToSource.put(i, "" + i + " : " + line.trim());
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineNumberToSource;
    }

}
