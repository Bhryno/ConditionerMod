package com.github.conditioner.mod.startup.asm;

import com.github.conditioner.mod.startup.asm.impl.*;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.function.BiConsumer;

public class ClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] buffer) {
        if (transformedName.equals("net.minecraft.client.resources.AbstractResourcePack")) {
            return transform(buffer, (cn, mn) -> AbstractResourcePackTransformer.transform(mn));
        } else if (name.equals("com.github.conditioner.mod.util.CapeImageBuffer")) {
            return transform(buffer, (cn, mn) -> CapeImageBufferTransformer.transform(mn));
        } else if (name.equals("CapeUtils")) {
            return CapeUtilsTransformer.transform(buffer);
        }else if (transformedName.equals("net.minecraft.entity.EntityLivingBase")) {
            return transform(buffer, EntityLivingBaseTransformer::transform);
        } else if (transformedName.equals("net.minecraftforge.fml.client.config.GuiUtils")) {
            return transform(buffer, (cn, mn) -> GuiUtilsTransformer.transform(mn));
        } else if (transformedName.equals("net.minecraft.client.Minecraft")) {
            return transform(buffer, (cn, mn) -> MinecraftTransformer.transform(mn));
        } else if (transformedName.equals("net.minecraft.client.network.NetHandlerPlayClient")) {
            return transform(buffer, NetHandlerPlayClientTransformer::transform);
        } else {
            return buffer;
        }
    }

    private byte[] transform(byte[] buffer, BiConsumer<ClassNode, MethodNode> consumer) {
        ClassReader cr = new ClassReader(buffer);
        ClassNode cn = new ClassNode();

        cr.accept(cn, 0);
        cn.methods.forEach(mn -> consumer.accept(cn, mn));
        ClassWriter cw = new ClassWriter(0);

        cn.accept(cw);
        return cw.toByteArray();
    }
}
