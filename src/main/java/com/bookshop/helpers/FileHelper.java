package com.bookshop.helpers;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;

public class FileHelper {

    private static final String[] IMAGE_FILE_TYPES = {"jpg", "jpeg", "png"};

    public static boolean isAllowImageType(String fileName) {
        String fileExtension = FilenameUtils.getExtension(fileName);
        return Arrays.asList(IMAGE_FILE_TYPES).contains(fileExtension.toLowerCase());
    }

    public static String randomUniqueFileName(String fileName) {
        String fileBaseName = FilenameUtils.getBaseName(fileName);
        String fileExtension = FilenameUtils.getExtension(fileName);
        String randomUnique = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 10);
        return fileBaseName + "-" + randomUnique + "." + fileExtension;
    }
}
