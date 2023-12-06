package com.maksimov.imgtoascii.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.format.DateTimeFormatter;

public class ImageToTextConverter implements Converter<FileInputStream, InputStream> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageToTextConverter.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss-ms");
    private final int xResolution = 1;
    private final int yResolution = 2 * xResolution;
    private final ImageModifier imageModifier = new ImageModifier();

    @Override
    public InputStream convert(FileInputStream input) throws IOException {
        LOGGER.debug("Convert to ASCII");
        LOGGER.debug("Choose the size (in px) that an ASCII character will represent");
        BufferedImage image = ImageIO.read(input);
        image = imageModifier.convertToGrey(image);
        image = imageModifier.convertToLowResolution(image, xResolution, yResolution);
        ByteArrayInputStream converted = convertToTextFile(image);
        LOGGER.debug("Conversion done to ASCII");
        return converted;
    }

    private ByteArrayInputStream convertToTextFile(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int j = 0; j < image.getHeight(); j += yResolution) {
            StringBuilder asciiString = new StringBuilder();
            for (int i = 0; i < image.getWidth(); i += xResolution) {
                int imageRGB = image.getRGB(i, j);
                asciiString.append(imageModifier.getCorrespondingAsciiChar(new Color(imageRGB).getRed()));
            }
            asciiString.append("\n");
            byte[] bytes = asciiString.toString().getBytes();
            outputStream.write(bytes);
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
