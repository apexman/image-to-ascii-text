package com.maksimov.imgtoascii;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class ImageConverter {
	private double RED_COEFF = 0.2125;
	private double GREEN_COEFF = 0.7154;
	private double BLUE_COEFF = 0.0721;

	private int xResolution = 1;
	private int yResolution = 2 * xResolution;
	private String among = "@08Ooui|/;:,'. ";

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

	private String getCorrespondingAsciiChar(int grey) {
		double coeff = grey / 255.0;
		int index = (int) (coeff * (double) among.length());
		char c = among.charAt(index);
		return String.valueOf(c);
	}

}
