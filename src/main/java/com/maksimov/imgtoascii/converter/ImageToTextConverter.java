package com.maksimov.imgtoascii.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageToTextConverter implements Converter<File, File> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageToTextConverter.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss-ms");
    private final int xResolution = 1;
    private final int yResolution = 2 * xResolution;
    private final ImageModifier imageModifier = new ImageModifier();

    @Override
    public File convert(File input) throws IOException {
        LOGGER.debug("Convert to ASCII");
        LOGGER.debug("Choose the size (in px) that an ASCII character will represent");
        BufferedImage image = ImageIO.read(input);
        image = imageModifier.convertToGrey(image);
        image = imageModifier.convertToLowResolution(image, xResolution, yResolution);
        File file = convertToTextFile(image);
        LOGGER.debug("Conversion done to ASCII");
        return file;
    }

    private File convertToTextFile(BufferedImage image) throws IOException {
        String path1 = String.format("temp/savedASCII-%s.txt", dateTimeFormatter.format(LocalDateTime.now()));
        Path path = Paths.get(path1);
        Files.createDirectories(path.getParent());
        File file = new File(path1);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel fileChannel = fileOutputStream.getChannel()
        ) {

            StringBuilder asciiString = new StringBuilder();
            for (int j = 0; j < image.getHeight(); j += yResolution) {
                for (int i = 0; i < image.getWidth(); i += xResolution) {
                    int imageRGB = image.getRGB(i, j);
                    Color c = new Color(imageRGB);
                    int red = c.getRed();
                    asciiString.append(imageModifier.getCorrespondingAsciiChar(red));
                }
                asciiString.append("\n");
                byte[] bytes = asciiString.toString().getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                fileChannel.write(buffer);
                asciiString = new StringBuilder();
            }
        }
        return file;
    }

}
