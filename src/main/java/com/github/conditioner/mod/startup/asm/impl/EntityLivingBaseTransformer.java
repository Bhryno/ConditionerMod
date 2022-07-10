package com.github.conditioner.mod.startup.asm.impl;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityLivingBaseTransformer implements Opcodes {

    public static void transform(ClassNode cn, MethodNode mn) {
        String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name, mn.name, mn.desc);

        if (methodName.equals("getLook") || methodName.equals("func_70676_i")) {
            String entity = FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/entity/Entity");
            String entityPlayerSP = FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/client/entity/EntityPlayerSP");
            InsnList insnList = new InsnList();
            LabelNode ln = new LabelNode();

            insnList.add(new VarInsnNode(ALOAD, 0));
            insnList.add(new TypeInsnNode(INSTANCEOF, entityPlayerSP));
            insnList.add(new JumpInsnNode(IFEQ, ln));
            insnList.add(new VarInsnNode(ALOAD, 0));
            insnList.add(new VarInsnNode(FLOAD, 1));
            insnList.add(new MethodInsnNode(INVOKESPECIAL, entity, mn.name, mn.desc, false));
            insnList.add(new InsnNode(ARETURN));
            insnList.add(ln);
            mn.instructions.insertBefore(mn.instructions.getFirst(), insnList);
        }
    }
}
