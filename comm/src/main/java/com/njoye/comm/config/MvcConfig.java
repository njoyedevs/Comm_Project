//package com.njoye.comm.config;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import jakarta.servlet.http.HttpSession;
// 
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
// 
//	// Is it ok to take off the @Override here?
//    public void addResourceHandlers(
//    		ResourceHandlerRegistry registry,
//    		HttpSession session) {
//    	
//    	// Obtain userID from session
//        Long userId = (Long) session.getAttribute("userId");
//        
//        exposeDirectoryForUser("/images/", userId, registry);
//    }
//     
//    private void exposeDirectoryForUser(String dirName, Long userId, ResourceHandlerRegistry registry) {
//        Path uploadDir = Paths.get(dirName + "/" + userId);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//             
//        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
//             
//        registry.addResourceHandler("/" + dirName + "/" + userId + "/**").addResourceLocations("file:/"+ uploadPath + "/");
//    }
//}
