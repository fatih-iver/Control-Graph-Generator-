package fun.fiver;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) throws IOException {

        ClassReader classReader = new ClassReader(args[0]);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        List<MethodNode> methodNodeList = classNode.methods;

        for (MethodNode methodNode : methodNodeList) {
            InsnList insnList = methodNode.instructions;
            ListIterator<AbstractInsnNode> abstractInsnNodeListIterator = insnList.iterator();
            while (abstractInsnNodeListIterator.hasNext()) {
                AbstractInsnNode abstractInsnNode = abstractInsnNodeListIterator.next();
                System.out.println(abstractInsnNode);
            }
        }

        for (MethodNode methodNode : methodNodeList) {
            System.out.println();
            System.out.println(methodNode.name);
            methodNode.accept(new CFGMethodVisitor(Opcodes.ASM4));
        }

    }

}
