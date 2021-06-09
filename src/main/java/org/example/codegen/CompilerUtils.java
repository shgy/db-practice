package org.example.codegen;

import io.airlift.bytecode.ParameterizedType;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static io.airlift.bytecode.BytecodeUtils.toJavaIdentifierString;
import static io.airlift.bytecode.ParameterizedType.typeFromJavaClassName;
import static java.time.ZoneOffset.UTC;

public class CompilerUtils {

    private static final AtomicLong CLASS_ID = new AtomicLong();
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("YYYYMMdd_HHmmss");

    public static ParameterizedType makeClassName(String baseName, Optional<String> suffix)
    {
        String className = baseName
                + "_" + suffix.orElseGet(() -> Instant.now().atZone(UTC).format(TIMESTAMP_FORMAT))
                + "_" + CLASS_ID.incrementAndGet();
        return typeFromJavaClassName("org.example.filter.$gen." + toJavaIdentifierString(className));
    }

    public static ParameterizedType makeClassName(String baseName)
    {
        return makeClassName(baseName, Optional.empty());
    }

}
