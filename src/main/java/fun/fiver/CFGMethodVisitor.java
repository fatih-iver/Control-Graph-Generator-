package fun.fiver;


import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFGMethodVisitor extends MethodVisitor {

    private String currentLabel;

    private List<String> labelsInOrder = new ArrayList<>();
    private Map<String, Integer> labelToLineNumber = new HashMap<>();
    private Map<String, String> labelToJumpLabel = new HashMap<>();
    private Map<String, String> labelToGoToLabel = new HashMap<>();

    private Map<String, String> labelToNextLabel = new HashMap<>();

    private List<Integer> labelLineNumbers = new ArrayList<>();
    private List<Integer> nextLabelLineNumber = new ArrayList<>();
    private List<Integer> jumpLabelLineNumber = new ArrayList<>();

    private String exitLabel;

    private Map<Integer, String> lineNumberToSource;


    public CFGMethodVisitor(int api, Map<Integer, String> lineNumberToSource) {
        super(api);
        this.lineNumberToSource = lineNumberToSource;
    }

    @Override
    public void visitParameter(String name, int access) {
        //System.out.println("visitParameter");
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        //System.out.println("visitAnnotationDefault");
        return super.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        //System.out.println("visitAnnotation");
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        //System.out.println("visitTypeAnnotation");
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
        //System.out.println("visitAnnotableParameterCount");
        super.visitAnnotableParameterCount(parameterCount, visible);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        //System.out.println("AnnotationVisitor");
        return super.visitParameterAnnotation(parameter, descriptor, visible);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        //System.out.println("visitAttribute");
        super.visitAttribute(attribute);
    }

    @Override
    public void visitCode() {
        //System.out.println("visitCode");
        super.visitCode();
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        //System.out.println("visitFrame");
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    @Override
    public void visitInsn(int opcode) {
        //System.out.println("visitInsn");
        super.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        //System.out.println("visitIntInsn");
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        //System.out.println("visitVarInsn");
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        //System.out.println("visitTypeInsn");
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        //System.out.println("visitFieldInsn");
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //System.out.println("visitMethodInsn");
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        //System.out.println("visitInvokeDynamicInsn");
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        //System.out.println("visitJumpInsn:" + opcode);
        labelToJumpLabel.put(currentLabel, label.toString());
        if (opcode == Opcodes.GOTO) {
            labelToGoToLabel.put(currentLabel, label.toString());
        }
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        //System.out.println("visitLabel");
        currentLabel = label.toString();
        labelsInOrder.add(currentLabel);
        super.visitLabel(label);
    }

    @Override
    public void visitLdcInsn(Object value) {
        //System.out.println("visitLdcInsn");
        super.visitLdcInsn(value);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        //System.out.println("visitIincInsn");
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        //System.out.println("visitTableSwitchInsn");
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        //System.out.println("visitLookupSwitchInsn");
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        //System.out.println("visitMultiANewArrayInsn");
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        //System.out.println("visitInsnAnnotation");
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        //System.out.println("visitTryCatchBlock");
        super.visitTryCatchBlock(start, end, handler, type);
    }

    @Override
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        //System.out.println("visitTryCatchAnnotation");
        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        //System.out.println("visitLocalVariable");
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
        //System.out.println("visitLocalVariableAnnotation");
        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        //System.out.println("visitLineNumber:" + line);
        labelToLineNumber.put(start.toString(), line);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        //System.out.println("visitMaxs");
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        //System.out.println("visitEnd");
        super.visitEnd();
        handleVisitEnd();
    }

    private void handleVisitEnd() {
        removeExitLabel();
        findLabelLineNumbers();
        formLabelToNextLabelMap();
        findNextLabelLineNumbers();
        findJumpLabelLineNumbers();
        formAndPrintSourceDigraph();
    }

    private void removeExitLabel() {
        exitLabel = labelsInOrder.remove(labelsInOrder.size() - 1);
    }

    private void findLabelLineNumbers() {
        for (int index = 0; index < labelsInOrder.size(); index++) {
            String currentLabel = labelsInOrder.get(index);
            if (labelToLineNumber.containsKey(currentLabel)) {
                labelLineNumbers.add(labelToLineNumber.get(currentLabel));
            } else {
                int previousIndex = index - 1;
                while (previousIndex >= 0) {
                    String previousLabel = labelsInOrder.get(previousIndex);
                    if (labelToLineNumber.containsKey(previousLabel)) {
                        labelLineNumbers.add(labelToLineNumber.get(previousLabel));
                        break;
                    }
                    previousIndex--;
                }
            }
        }
    }

    private void formLabelToNextLabelMap() {
        for (int i = 0; i < labelsInOrder.size() - 1; i++) {
            labelToNextLabel.put(labelsInOrder.get(i), labelsInOrder.get(i + 1));
        }
    }

    private void findNextLabelLineNumbers() {
        for (int i = 0; i < labelsInOrder.size(); i++) {
            String currentLabel = labelsInOrder.get(i);
            if (labelToGoToLabel.containsKey(currentLabel)) {
                nextLabelLineNumber.add(-1);
                continue;
            }
            if (labelToNextLabel.containsKey(currentLabel)) {
                nextLabelLineNumber.add(labelToLineNumber.get(labelToNextLabel.get(currentLabel)));
            } else {
                nextLabelLineNumber.add(-1);
            }
        }
    }

    private void findJumpLabelLineNumbers() {
        for (int i = 0; i < labelsInOrder.size(); i++) {
            String currentLabel = labelsInOrder.get(i);
            if (labelToJumpLabel.containsKey(currentLabel)) {
                jumpLabelLineNumber.add(labelToLineNumber.get(labelToJumpLabel.get(currentLabel)));
            } else {
                jumpLabelLineNumber.add(-1);
            }
        }
    }

    private void formAndPrintLineNumberDigraph() {
        System.out.println("digraph G {");

        for (int i = 0; i < labelLineNumbers.size(); i++) {
            if (i < nextLabelLineNumber.size()) {
                if (nextLabelLineNumber.get(i) != null && nextLabelLineNumber.get(i) != -1) {
                    System.out.println("  \"" + labelLineNumbers.get(i) + "\"" + " -> " + "\"" + nextLabelLineNumber.get(i) + "\"");
                }
            }
            if (i < jumpLabelLineNumber.size()) {
                if (jumpLabelLineNumber.get(i) != null && jumpLabelLineNumber.get(i) != -1) {
                    System.out.println("  \"" + labelLineNumbers.get(i) + "\"" + " -> " + "\"" + jumpLabelLineNumber.get(i) + "\"");
                }
            }
        }

        System.out.println("}");
    }

    private void formAndPrintSourceDigraph() {
        System.out.println("digraph G {");

        for (int i = 0; i < labelLineNumbers.size(); i++) {
            if (i < nextLabelLineNumber.size()) {
                if (nextLabelLineNumber.get(i) != null && nextLabelLineNumber.get(i) != -1) {
                    System.out.println("  \"" + lineNumberToSource.get(labelLineNumbers.get(i)).replace("\"", "\\\"") + "\"" + " -> " + "\"" + lineNumberToSource.get(nextLabelLineNumber.get(i)).replace("\"", "\\\"") + "\"");
                }
            }
            if (i < jumpLabelLineNumber.size()) {
                if (jumpLabelLineNumber.get(i) != null && jumpLabelLineNumber.get(i) != -1) {
                    System.out.println("  \"" + lineNumberToSource.get(labelLineNumbers.get(i)).replace("\"", "\\\"") + "\"" + " -> " + "\"" + lineNumberToSource.get(jumpLabelLineNumber.get(i)).replace("\"", "\\\"") + "\"");
                }
            }
        }

        System.out.println("}");
    }
}
