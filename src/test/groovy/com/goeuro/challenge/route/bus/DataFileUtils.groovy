package com.goeuro.challenge.route.bus

import static java.lang.System.lineSeparator
import static java.nio.charset.StandardCharsets.UTF_8

class DataFileUtils {

    public static final String DATA_FILE_PATH_NAME = "src/test/resources/example"
    public static final String CORRECT_DATA = '2' + lineSeparator() +
            '1 16 148 140 19' + lineSeparator() +
            '2 5 114 153 11 169' + lineSeparator()

    static def writeDataFile(String data) {
        new File(DATA_FILE_PATH_NAME).withPrintWriter(UTF_8.name(), {
            out ->
                out.print data
        })
    }
}
