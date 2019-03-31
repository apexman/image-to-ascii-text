package com.maksimov.imgtoascii;

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
import java.util.Scanner;

public class ImageConverter {
    private Logger logger = LoggerFactory.getLogger(ImageConverter.class);

    private double RED_COEFF = 0.2125;
    private double GREEN_COEFF = 0.7154;
    private double BLUE_COEFF = 0.0721;

    private int xResolution = 1;
    private int yResolution = 2 * xResolution;
    private String among = "@08OCocui|/;:,'. ";

    public BufferedImage convertToImageASCII(BufferedImage image) {
        System.out.println("Convert ti ASCII image");

        BufferedImage asciiImageExample = getCorrespondingAsciiCharAsImage(0);

        xResolution = asciiImageExample.getWidth();
        yResolution = asciiImageExample.getHeight();

        convertToGrey(image);

        convertToLowResolution(image);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = resultImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.BLACK);

        try {
            String path = "savedImageASCII.png";

            BufferedImage asciiImage;

            for (int j = 0; j < imageHeight; j += yResolution) {
                for (int i = 0; i < imageWidth; i += xResolution) {
                    int imageRGB = image.getRGB(i, j);
                    Color c = new Color(imageRGB);
                    int red = c.getRed();

                    asciiImage = getCorrespondingAsciiCharAsImage(red);

                    graphics.drawImage(asciiImage, i, j, null);
                }
            }

            graphics.dispose();

            ImageIO.write(resultImage, "png", new File(path));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Done");

        return resultImage;
    }

    public void convertToASCII(BufferedImage image) throws IOException {
        System.out.println("Convert to ASCII");

        System.out.println("Choose the size (in px) that an ASCII character will represent");
        Scanner scanner = new Scanner(System.in);
        int i1 = scanner.nextInt();

        if (i1 <= 0) {
            throw new IllegalArgumentException("Resolution number must be positive and integer");
        }

        xResolution = i1;
        yResolution = 2 * xResolution;

        convertToGrey(image);

        convertToLowResolution(image);

        String path = "savedASCII.txt";
        File file = new File(path);
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

                    asciiString.append(getCorrespondingAsciiChar(red));
                }
                asciiString.append("\n");

                byte[] bytes = asciiString.toString().getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                fileChannel.write(buffer);

                asciiString = new StringBuilder();
            }
        }

        System.out.println("Done conversion to ASCII");
    }

    public void convertToGrey(BufferedImage image) {
        System.out.println("Convert to grey");

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

                image.setRGB(i, j, greyColor.getRGB());
            }
        }

        System.out.println("Done conversion to grey");
    }

    public void convertToLowResolution(BufferedImage image) {
        System.out.println("Convert to low resolution");
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
                            image.setRGB(i + x, j + y, (new Color(grey, grey, grey).getRGB()));
                        }
                    }
                }
            }
        }
        System.out.println("Done conversion to low resolution");
    }

    private BufferedImage getCorrespondingAsciiCharAsImage(int grey) {
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

    private String getCorrespondingAsciiChar(int grey) {
        double coeff = grey / 255.0;
        int index = (int) (coeff * (double) among.length());
        char c = among.charAt(index);
        return String.valueOf(c);
    }

}
