package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    public static String md5(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(Paths.get(filePath));
             DigestInputStream dis = new DigestInputStream(is, md)) {
            /* Read decorated stream (dis) to EOF as normal... */
        }
        byte[] digest = md.digest();
        return new String(digest);
    }

    /**
     * @return key: absolute path; value: md5
     */
    public static Map<String, String> fileMd5Dict(String dirPath) throws NoSuchAlgorithmException, IOException {
        Map<String, String> result = new HashMap<>();
        File folder = new File(dirPath);
        if (!folder.isDirectory()) {
            return null;
        }
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                result.putAll(fileMd5Dict(file.getAbsolutePath()));
            } else {
                result.put(file.getAbsolutePath(), md5(file.getAbsolutePath()));
            }
        }
        return result;
    }
}
