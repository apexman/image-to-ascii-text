package com.maksimov.imgtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageModifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageModifier.class);
    private static final double RED_COEFF = 0.2125;
    private static final double GREEN_COEFF = 0.7154;
    private static final double BLUE_COEFF = 0.0721;
    private static final String ACCEPTABLE_CHARACTERS = "@08OCocui|/;:,'. ";

    public BufferedImage convertToGrey(BufferedImage image) {
        LOGGER.debug("Convert to grey");
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int imageRGB = image.getRGB(i, j);
                Color c = new Color(imageRGB);
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                int converted = (int) (red * RED_COEFF + green * GREEN_COEFF + blue * BLUE_COEFF);

                Color greyColor = new Color(converted, converted, converted);

                result.setRGB(i, j, greyColor.getRGB());
            }
        }
        LOGGER.debug("Conversion done to grey");
        return result;
    }

    public BufferedImage convertToLowResolution(BufferedImage image, int xResolution, int yResolution) {
        LOGGER.debug("Convert to low resolution");
        BufferedImage compressedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int grey = 0;
        for (int i = 0; i < image.getWidth(); i += xResolution) {
            for (int j = 0; j < image.getHeight(); j += yResolution) {
                for (int x = 0; x < xResolution; x++) {
                    for (int y = 0; y < yResolution; y++) {
                        int imageRGB = image.getRGB(i, j);
                        Color c = new Color(imageRGB);
                        int red = c.getRed();

                        grey += red;
                    }
                }

                grey /= xResolution * yResolution + 1;
                for (int x = 0; x < xResolution; x++) {
                    for (int y = 0; y < yResolution; y++) {
                        if ((i + x) < image.getWidth() && (j + y) < image.getHeight()) {
                            compressedImage.setRGB(i + x, j + y, (new Color(grey, grey, grey).getRGB()));
                        }
                    }
                }
            }
        }
        LOGGER.debug("Conversion done to low resolution");
        return compressedImage;
    }

    public String getCorrespondingAsciiChar(int grey) {
        double coeff = grey / 255.0;
        int index = (int) (coeff * ACCEPTABLE_CHARACTERS.length());
        char c = ACCEPTABLE_CHARACTERS.charAt(index);
        return String.valueOf(c);
    }

    public BufferedImage getCorrespondingAsciiCharAsImage(int grey) {
        String text = getCorrespondingAsciiChar(grey);

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 8);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        return img;
    }
}
