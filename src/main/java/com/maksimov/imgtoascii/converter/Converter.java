package com.maksimov.imgtoascii.converter;

import java.io.IOException;

public interface Converter<T, K> {
    K convert(T input) throws IOException;
}
