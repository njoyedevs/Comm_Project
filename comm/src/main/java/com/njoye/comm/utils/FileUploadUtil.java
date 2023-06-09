package com.njoye.comm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;
 

public class FileUploadUtil {
	
	public static void saveFile(
	        String uploadDir,
	        String fileName,
	        MultipartFile multipartFile
	        ) throws IOException {
	    
	    System.out.println("Received file: " + fileName + " (" + multipartFile.getSize() + " bytes)");
	    
	    Path uploadPath = Paths.get(uploadDir);
	    System.out.println("This is the uploadPath: " + uploadPath);
	    
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }
	     
	    try (InputStream inputStream = multipartFile.getInputStream()) {
	        Path filePath = uploadPath.resolve(fileName);
	        System.out.println("Saving file to: " + filePath);
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException ioe) {        
	        throw new IOException("Could not save image file: " + fileName, ioe);
	    }      
	}
}
