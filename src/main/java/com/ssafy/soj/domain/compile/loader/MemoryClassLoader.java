package com.ssafy.soj.domain.compile.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MemoryClassLoader extends URLClassLoader {

    private Map<String, byte[]> classBytes = new HashMap<>();

    public MemoryClassLoader(Map<String, byte[]> classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes.putAll(classBytes);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buffer = classBytes.get(name);
        if (buffer == null) return super.findClass(name);
        classBytes.remove(name);
        return defineClass(name, buffer, 0, buffer.length);
    }
}