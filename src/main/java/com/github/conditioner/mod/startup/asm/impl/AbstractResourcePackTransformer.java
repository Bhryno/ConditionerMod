package com.github.conditioner.mod.startup.asm.impl;

import com.github.conditioner.mod.util.ResourceHandler;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AbstractResourcePackTransformer implements Opcodes {

    public static void transform(MethodNode mn) {
        if ((mn.name.equals("getPackImage") || mn.name.equals("func_110586_a")) && mn.desc.equals("()Ljava/awt/image/BufferedImage;")) {
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                if (ain.getOpcode() == ARETURN) {
                    mn.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, ResourceHandler.class.getCanonicalName().replace(".", "/"), "scalePackImage", "(Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;", false));
                }
            }
        }
    }
}
