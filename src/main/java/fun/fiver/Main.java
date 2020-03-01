package fun.fiver;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        ClassReader classReader = new ClassReader(args[0]);
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

        Map<Integer, String> lineNumberToSource = formLineNumberToSourceMap();

        for (MethodNode methodNode : methodNodeList) {
            System.out.println();
            System.out.println(methodNode.name);
            methodNode.accept(new CFGMethodVisitor(Opcodes.ASM4, lineNumberToSource));
        }


    }

    private static Map<Integer, String> formLineNumberToSourceMap() {

        Map<Integer, String> lineNumberToSource = new HashMap<>();

        try {
            File file = new File("C:\\Users\\Fatih\\IdeaProjects\\Control Graph Generator\\src\\main\\java\\TestData.java");
            FileReader fileReader = new FileReader(file);
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
