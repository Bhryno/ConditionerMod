package com.github.conditioner.mod.startup.asm.impl;

import com.github.conditioner.mod.startup.hook.NetHandlerPlayClientHook;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class NetHandlerPlayClientTransformer implements Opcodes {

    public static void transform(ClassNode cn, MethodNode mn) {
        String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name, mn.name, mn.desc);

        if (methodName.equals("handleResourcePack") || methodName.equals("func_175095_a")) {
            InsnList insnList = new InsnList();
            LabelNode ln = new LabelNode();

            insnList.add(new VarInsnNode(ALOAD, 0));
            insnList.add(new VarInsnNode(ALOAD, 1));
            insnList.add(new MethodInsnNode(INVOKEVIRTUAL, S48PacketResourcePackSend.class.getCanonicalName().replace(".", "/"), "func_179784_b", "()Ljava/lang/String;", false));
            insnList.add(new VarInsnNode(ALOAD, 1));
            insnList.add(new MethodInsnNode(INVOKEVIRTUAL, S48PacketResourcePackSend.class.getCanonicalName().replace(".", "/"), "func_179783_a", "()Ljava/lang/String;", false));
            insnList.add(new MethodInsnNode(INVOKESTATIC, NetHandlerPlayClientHook.class.getCanonicalName().replace(".", "/"), "validateResourcePackUrl", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Ljava/lang/String;Ljava/lang/String;)Z", false));
            insnList.add(new JumpInsnNode(IFNE, ln));
            insnList.add(new InsnNode(RETURN));
            insnList.add(ln);
            mn.instructions.insertBefore(mn.instructions.getFirst(), insnList);
        }
    }
}
