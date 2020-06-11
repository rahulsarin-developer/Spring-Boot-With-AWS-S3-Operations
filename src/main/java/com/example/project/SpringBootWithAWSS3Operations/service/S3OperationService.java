package com.example.project.SpringBootWithAWSS3Operations.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.SpringBootWithAWSS3Operations.entity.S3Operation;

public interface S3OperationService {

	String fileUplaod(String bucketName, MultipartFile file);

	String createBucket(String bucketName);

	List<String> getBucketList();

	List<S3Operation> getBucketfiles(String bucketName);

	String softDeleteBucket(String bucketName);

	String hardDeleteBucket(String bucketName);

	String deleteFile(String bucketName, String fileName);

	S3Operation downloadFile(String bucketName, String fileName);
}
