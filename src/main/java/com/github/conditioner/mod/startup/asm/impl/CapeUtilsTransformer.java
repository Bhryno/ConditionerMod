package com.github.conditioner.mod.startup.asm.impl;

import com.github.conditioner.mod.util.CapeImageBuffer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

public class CapeUtilsTransformer {

    public static byte[] transform(byte[] buffer) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        RemappingClassAdapter adapter = new RemappingClassAdapter(cw, new Remapper() {
            @Override
            public String map(String name) {
                if (name.equals("CapeUtils$1")) {
                    return CapeImageBuffer.class.getCanonicalName().replace(".", "/");
                }
                return name;
            }
        });
        ClassReader cr = new ClassReader(buffer);

        cr.accept(adapter, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }
}
