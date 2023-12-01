package com.maksimov.imgtoascii;

import java.io.IOException;

public interface Converter<T, K> {
    K convert(T input) throws IOException;
}
