package imgtoascii;

import com.maksimov.imgtoascii.converter.ImageToTextConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Objects;

class ImageToTextConverterTest {
    private final ImageToTextConverter converter = new ImageToTextConverter();

    @Test
    @Timeout(10)
    void whenLittlePicToAscii() throws IOException {
        File image = converter.convert(getFile("files/img.png"));

        byte[] expectedBytes = Files.readAllBytes(Paths.get(getFile("files/imgExpected.txt").toURI()));
        byte[] resultBytes = Files.readAllBytes(Paths.get(image.toURI()));
        Assertions.assertTrue(MessageDigest.isEqual(expectedBytes, resultBytes));
    }

    @Test
    @Timeout(10)
    void whenBigPicToAscii() throws IOException {
        File image = converter.convert(getFile("files/drhouse.jpg"));
        Assertions.assertNotNull(image);
    }

    private File getFile(String filePath) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).getFile());
    }
}
