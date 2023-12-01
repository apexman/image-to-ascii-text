package imgtoascii;

import com.maksimov.imgtoascii.ImageConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Objects;

class ImageConverterTest {
    private final ImageConverter imageConverter = new ImageConverter();

    @Test
    @Timeout(10)
    void whenLittlePicToAscii() throws IOException {
        File image = imageConverter.convertToAcsii(getImage("files/img.png"));

        byte[] expectedBytes = Files.readAllBytes(Paths.get(getFile("files/imgExpected.txt").toURI()));
        byte[] resultBytes = Files.readAllBytes(Paths.get(image.toURI()));
        Assertions.assertTrue(MessageDigest.isEqual(expectedBytes, resultBytes));
    }

    @Test
    @Timeout(10)
    void whenBigPicToAscii() throws IOException {
        File image = imageConverter.convertToAcsii(getImage("files/drhouse.jpg"));
        Assertions.assertNotNull(image);
    }

    private BufferedImage getImage(String filePath) throws IOException {
        return ImageIO.read(getFile(filePath));
    }

    private File getFile(String filePath) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).getFile());
    }
}
