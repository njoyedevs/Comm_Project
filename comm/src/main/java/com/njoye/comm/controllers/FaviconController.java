//package com.njoye.comm.controllers;
//
//import java.io.IOException;
//
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class FaviconController {
//
//    @GetMapping("/favicon.ico")
//    public ResponseEntity<Resource> favicon() throws IOException {
//        Resource resource = new ClassPathResource("static/images/CommLogo2.png");
//        return ResponseEntity.ok()
//                .contentLength(resource.contentLength())
//                .body(resource);
//    }
//}
//
