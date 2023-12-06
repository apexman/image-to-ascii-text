package imgtoascii;

import com.maksimov.imgtoascii.converter.ImageToTextConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

class ImageToTextConverterTest {
    private final ImageToTextConverter converter = new ImageToTextConverter();

    @Test
    @Timeout(10)
    void whenLittlePicToAscii() throws IOException {
        InputStream image = converter.convert(getInputStream("files/img.png"));

        byte[] expectedBytes = getInputStream("files/imgExpected.txt").readAllBytes();
        byte[] resultBytes = image.readAllBytes();
        Assertions.assertTrue(MessageDigest.isEqual(expectedBytes, resultBytes));
    }

    @Test
    @Timeout(10)
    void whenBigPicToAscii() throws IOException {
        InputStream image = converter.convert(getInputStream("files/drhouse.jpg"));
        Assertions.assertNotNull(image);
    }

    private FileInputStream getInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(getClass().getClassLoader().getResource(filePath).getFile());
    }
}
