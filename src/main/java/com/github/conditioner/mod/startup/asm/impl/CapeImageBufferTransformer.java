package com.github.conditioner.mod.startup.asm.impl;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class CapeImageBufferTransformer implements Opcodes {

    public static void transform(MethodNode mn) {
        for (AbstractInsnNode ain : mn.instructions.toArray()) {
            if (ain instanceof MethodInsnNode) {
                MethodInsnNode min = (MethodInsnNode) ain;

                if (min.name.equals("parseCape")) {
                    min.owner = "CapeUtils";
                } else if (min.name.equals("setLocationOfCape")) {
                    min.setOpcode(INVOKEVIRTUAL);
                    min.owner = "net/minecraft/client/entity/AbstractClientPlayer";
                    min.desc = "(Lnet/minecraft/util/ResourceLocation;)V";
                }
            }
        }
    }
}
