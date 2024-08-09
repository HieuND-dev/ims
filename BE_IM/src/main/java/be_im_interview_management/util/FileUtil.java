package be_im_interview_management.util;

import org.springframework.http.MediaType;

/**
 * Created by: HieuND64
 * Date Time: 7/31/2024 11:06 PM
 */
public class FileUtil {
    public static MediaType getMediaTypeForFileName(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".doc")) {
            return MediaType.valueOf("application/msword");
        } else if (fileName.endsWith(".docx")) {
            return MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
