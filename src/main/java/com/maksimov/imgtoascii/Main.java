package com.maksimov.imgtoascii;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	private static Map<String, Builder> argsBuilder = new HashMap<>();

	static {
		argsBuilder.put("-i", Main::buildImageArg);
		argsBuilder.put("-url", Main::buildImageFromUrl);
		argsBuilder.put("-xr", Main::buildImageFromUrl);
		argsBuilder.put("-h", Main::buildHelp);
	}

	public static void main(String[] args) throws IOException {
		System.out.println(Arrays.toString(args));

		File imageFile = parseArgs(args);
		BufferedImage image = ImageIO.read(imageFile);

		ImageConverter imageConverter = new ImageConverter();
//		imageConverter.convertToASCII(image);
		imageConverter.convertToImageASCII(image);

		File outputfile = new File("" + "converted.png");
		ImageIO.write(image, "png", outputfile);

//		String inputFilename = "/home/alex/Desktop/love_death_robots.mp4";
//		VideoConverter videoConverter = new VideoConverter();
//		videoConverter.convertToAsciiVideo(inputFilename);
	}

	private static File parseArgs(String[] args) throws IOException {
		if (args == null || args.length == 0) {
			String path = Main.class.getResource("/drhouse.jpg").getPath();
			System.out.println(path);
			return new File(path);
//			throw new RuntimeException("Args must be specified. Enter -h for help");
		}

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (argsBuilder.containsKey(arg)) {
				File file = argsBuilder.get(arg).build(i, args);
				if (file == null) {
					System.exit(0);
				} else {
					return file;
				}
			}
		}

		throw new RuntimeException("Unknown args");
	}

	private interface Builder {
		File build(int index, String[] args) throws IOException;
	}

	private static File buildImageArg(int index, String[] args) throws IOException {
		if (args.length - 1 < index + 1) {
			throw new RuntimeException("Arg -i is not specified");
		}

		String path = args[index + 1];
		return new File(path);
	}

	private static File buildImageFromUrl(int index, String[] args) throws IOException {
		if (args.length - 1 < index + 1) {
			throw new RuntimeException("Arg -url is not specified");
		}

		String path = args[index + 1];
		return new File(path);
	}

	private static File buildXResolutionArg(int index, String[] args) throws IOException {
		if (args.length - 1 < index + 1) {
			throw new RuntimeException("Arg -xr is not specified");
		}

		String path = args[index + 1];
		return new File(path);
	}

	private static File buildHelp(int index, String[] args) throws IOException {
		System.out.println("Help:");
		System.out.println("-i		Image");
		System.out.println("-url	Url to image");
		System.out.println("-h		This help");

		return null;
	}
}
