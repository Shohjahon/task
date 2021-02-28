package uz.ivoice.bot.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Utils {
    private static final PrintStream printStream = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    public static <T> Optional<T> checkForMapping(String json, TypeReference<T> classType) {
        try {
            return Optional.of(new ObjectMapper().readValue(json, classType));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static void printf(String content, String ...params){
        printStream.printf(content, params);
    }
}
