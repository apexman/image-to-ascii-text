package com.maksimov.imgtoascii.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TODO impl
public class ImageToTextImageConverter implements Converter<BufferedImage, BufferedImage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageToTextImageConverter.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss-ms");
    private int xResolution = 1;
    private int yResolution = 2 * xResolution;
    private final ImageModifier imageModifier = new ImageModifier();

    public BufferedImage convert(BufferedImage image) throws IOException {
        if (true) {
            throw new UnsupportedOperationException();
        }
        LOGGER.debug("Convert ti ASCII image");

        BufferedImage asciiImageExample = imageModifier.getCorrespondingAsciiCharAsImage(0);

        xResolution = asciiImageExample.getWidth();
        yResolution = asciiImageExample.getHeight();

        image = imageModifier.convertToGrey(image);

        image = imageModifier.convertToLowResolution(image, xResolution, yResolution);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = resultImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.BLACK);

        String path = String.format("temp/savedImageASCII-%s.png", dateTimeFormatter.format(LocalDateTime.now()));
        BufferedImage asciiImage;
        for (int j = 0; j < imageHeight; j += yResolution) {
            for (int i = 0; i < imageWidth; i += xResolution) {
                int imageRGB = image.getRGB(i, j);
                Color c = new Color(imageRGB);
                int red = c.getRed();

                asciiImage = imageModifier.getCorrespondingAsciiCharAsImage(red);

                graphics.drawImage(asciiImage, i, j, null);
            }
        }
        graphics.dispose();
        ImageIO.write(resultImage, "png", new File(path));
        LOGGER.debug("Done");

        return resultImage;
    }

}
