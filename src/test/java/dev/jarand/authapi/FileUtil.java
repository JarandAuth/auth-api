package dev.jarand.authapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class FileUtil {

    public static String fileAsString(String path) {
        final var inputStream = FileUtil.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("Failed to get resource as stream for file: " + path);
        }
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
    }
}
