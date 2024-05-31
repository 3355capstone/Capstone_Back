package ConnetcTrip.Capstone.global.file.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String save(MultipartFile multipartFile);

    void delete(String filePath);
}
