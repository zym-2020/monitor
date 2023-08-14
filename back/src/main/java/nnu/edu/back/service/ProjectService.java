package nnu.edu.back.service;

import nnu.edu.back.pojo.Project;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/07/29/14:17
 * @Description:
 */
public interface ProjectService {
    String uploadAvatar(MultipartFile file);

    void getAvatar(String pictureName, HttpServletResponse response);

    void multipartUpload(MultipartFile file, String key, String number);

    void mergeMultipartFile(String key, int total);

    Project createProject(String projectName, String avatar, String description, String institution, String location, String time, String type);

    List<Project> pageQueryProject(String keyword, String type, int page, int size);
}
