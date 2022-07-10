package com.github.conditioner.mod.startup.asm.impl;

import com.github.conditioner.mod.startup.hook.GuiUtilsHook;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class GuiUtilsTransformer implements Opcodes {

    public static void transform(MethodNode mn) {
        if (mn.name.equals("drawHoveringText")) {
            int tooltipYIndex = -1;
            int tooltipHeightIndex = -1;

            for (LocalVariableNode lvn : mn.localVariables) {
                if (lvn.name.equals("tooltipY")) {
                    tooltipYIndex = lvn.index;
                } else if (lvn.name.equals("tooltipHeight")) {
                    tooltipHeightIndex = lvn.index;
                }
            }
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                if (ain instanceof IntInsnNode && ((IntInsnNode) ain).operand == 300 && ain.getNext().getOpcode() == ISTORE) {
                    InsnList insnList = new InsnList();

                    insnList.add(new VarInsnNode(ALOAD, 0));
                    insnList.add(new VarInsnNode(ILOAD, 4));
                    insnList.add(new VarInsnNode(ILOAD, tooltipYIndex));
                    insnList.add(new VarInsnNode(ILOAD, tooltipHeightIndex));
                    insnList.add(new MethodInsnNode(INVOKESTATIC, GuiUtilsHook.class.getCanonicalName().replace(".", "/"), "drawHoveringText", "(Ljava/util/List;III)V", false));
                    mn.instructions.insertBefore(ain, insnList);
                } else if (ain instanceof MethodInsnNode && ain.getOpcode() == INVOKESTATIC) {
                    String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(((MethodInsnNode) ain).owner, mn.name, mn.desc);

                    if (methodName.equals("enableRescaleNormal") || methodName.equals("func_179091_B")) {
                        mn.instructions.insert(ain, new MethodInsnNode(INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179121_F", "()V", false));
                    }
                }
            }
        }
    }
}
