package kodlamaio.hrms.core.adapters.cloudinary;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryFileManager implements CloudinaryFileService{

	private final Cloudinary cloudinary ;
	
	 @Autowired
	 public CloudinaryFileManager(Cloudinary cloudinary) {
	    this.cloudinary = cloudinary;
	 }
/*
	 @Override
	 public String upload(MultipartFile file) throws IOException {
		 
		 File uploadedFile = convertMultiPartToFile(file);
	        Map<?,?> uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
	        uploadedFile.delete();
	        return uploadResult.get("url").toString();
	        
	    }

	    private File convertMultiPartToFile(MultipartFile file) throws IOException {
	        File convFile = new File(file.getOriginalFilename());
	        FileOutputStream fos = new FileOutputStream(convFile);
	        fos.write(file.getBytes());
	        fos.close();
	        return convFile;
	    }
	    
	    */
	    
		@Override
		public String upload(File file) throws IOException{
			
			Map<?,?> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
	        return  (uploadResult.get("url").toString());
		}


}
