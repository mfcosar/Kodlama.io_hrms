package kodlamaio.hrms.core.adapters.cloudinary;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String upload(File file) throws IOException;
	String upload(MultipartFile file) throws IOException;

}
