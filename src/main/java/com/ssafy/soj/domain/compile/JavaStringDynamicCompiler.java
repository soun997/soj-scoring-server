package com.ssafy.soj.domain.compile;

import com.ssafy.soj.domain.compile.loader.MemoryClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JavaStringDynamicCompiler {

    private JavaCompiler compiler;

    private StandardJavaFileManager stdFileManager;

    private JavaStringDynamicCompiler() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.stdFileManager = compiler.getStandardFileManager(null, null, null);
    }

    private static final class JavaStringDynamicCompilerHolder {
        private static final JavaStringDynamicCompiler INSTANCE = new JavaStringDynamicCompiler();
    }

    public static JavaStringDynamicCompiler getInstance() {
        return JavaStringDynamicCompilerHolder.INSTANCE;
    }

    public Map<String, byte[]> compile(String filename, String source) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdFileManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(filename, source);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null,
                    null, null, Collections.singletonList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result) {
                throw new RuntimeException(this.getClass().getName() + ":" + filename + " Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }

    public Class<?> loadClass(String name, Map<String, byte[]> classBytes)
            throws ClassNotFoundException, IOException {
        try (MemoryClassLoader classLoader = new MemoryClassLoader(classBytes)) {
            return classLoader.loadClass(name);
        }
    }
}