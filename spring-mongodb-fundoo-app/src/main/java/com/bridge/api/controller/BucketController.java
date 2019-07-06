package com.bridge.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridge.api.client.AmazonClient;
import com.bridge.api.response.Response;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

	/*
	 * @PostMapping("/uploadFile") public String uploadFile(@RequestPart(value =
	 * "file") MultipartFile file) { return this.amazonClient.uploadFile(file); }
	 * 
	 * @DeleteMapping("/deleteFile") public String deleteFile(@RequestPart(value =
	 * "url") String fileUrl) { return
	 * this.amazonClient.deleteFileFromS3Bucket(fileUrl); }
	 */
    
    @PostMapping("/uploadFile")
	   public ResponseEntity<Response> uploadFile(@RequestPart(value = "multipartFile") MultipartFile multipartFile,@RequestHeader String token ) throws IOException {
	     Response response = amazonClient.uploadFile(multipartFile, token);
	   	return new ResponseEntity<Response>(response,HttpStatus.OK);
	   }
	   
	   @DeleteMapping("/deleteFile")
	   public ResponseEntity<Response> deleteFile(@RequestHeader String fileName, @RequestHeader String token) {
	   	Response response = amazonClient.deleteFileFromS3Bucket(fileName, token);
	     return new ResponseEntity<Response>(response,HttpStatus.OK);
	   }
}