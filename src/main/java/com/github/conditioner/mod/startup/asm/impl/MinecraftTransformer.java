package com.github.conditioner.mod.startup.asm.impl;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class MinecraftTransformer implements Opcodes {

    public static void transform(MethodNode mn) {
        Iterator<AbstractInsnNode> it = mn.instructions.iterator();

        while (it.hasNext()) {
            AbstractInsnNode ain = it.next();

            if (ain.getOpcode() == INVOKESTATIC) {
                MethodInsnNode min = (MethodInsnNode) ain;

                if (min.owner.equals("java/lang/System") && min.name.equals("gc")) {
                    it.remove();
                }
            }
        }
    }
}
