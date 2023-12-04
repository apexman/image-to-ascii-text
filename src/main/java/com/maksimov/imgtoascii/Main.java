package com.maksimov.imgtoascii;

import com.maksimov.imgtoascii.converter.ImageModifier;
import com.maksimov.imgtoascii.converter.ImageToTextConverter;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        ImageToTextConverter imageToTextConverter = new ImageToTextConverter();
        var filePath = "drhouse.jpg";
        File file = new File(Main.class.getClassLoader().getResource(filePath).getFile());
        org.apache.log4j.Logger.getLogger(ImageModifier.class).setLevel(Level.OFF);
        org.apache.log4j.Logger.getLogger(ImageToTextConverter.class).setLevel(Level.OFF);
        ArrayList<Long> durations = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            var start = System.nanoTime();
            File converted = imageToTextConverter.convert(file);
            long duration = (System.nanoTime() - start) / 1_000_000;
            durations.add(duration);
            LOGGER.info("{} ms", duration);
            converted.deleteOnExit();
        }
        LOGGER.info("Median: {} ms", median(durations));
    }

    private static Long median(ArrayList<Long> durations) {
        List<Long> sorted = durations.stream().sorted().toList();
        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 1)
            return sorted.get(middle);
        else
            return (sorted.get(middle - 1) + sorted.get(middle)) / 2;
    }
}
