package com.example.project.SpringBootWithAWSS3Operations.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.project.SpringBootWithAWSS3Operations.entity.S3Operation;
import com.example.project.SpringBootWithAWSS3Operations.service.S3OperationService;

@RestController
@RequestMapping(value = "/api/v1/aws")
public class S3OperationController {

	@Autowired
	private S3OperationService s3OperationService;

	@PostMapping(value = "/bucket/create/{bucketName}")
	public String createBucket(@PathVariable String bucketName) {
		return s3OperationService.createBucket(bucketName);
	}

	@GetMapping(value = "/bucket/list")
	public List<String> getBucketList() {
		return s3OperationService.getBucketList();
	}

	@GetMapping(value = "/bucket/files/{bucketName}")
	public List<S3Operation> getBucketfiles(@PathVariable String bucketName) {
		return s3OperationService.getBucketfiles(bucketName);
	}

	@DeleteMapping(value = "/bucket/delete/hard/{bucketName}")
	public String hardDeleteBucket(@PathVariable String bucketName) {
		return s3OperationService.hardDeleteBucket(bucketName);
	}

	@DeleteMapping(value = "/bucket/delete/{bucketName}")
	public String softDeleteBucket(@PathVariable String bucketName) {
		return s3OperationService.softDeleteBucket(bucketName);
	}

	@PostMapping(value = "/bucket/file/upload/{bucketName}")
	public String fileUplaod(@PathVariable String bucketName, MultipartFile file) {
		return s3OperationService.fileUplaod(bucketName, file);
	}

	@DeleteMapping(value = "/bucket/file/delete/{bucketName}/{fileName}")
	public String deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
		return s3OperationService.deleteFile(bucketName, fileName);
	}

	@GetMapping(value = "/bucket/file/download/{bucketName}/{fileName}")
	public StreamingResponseBody downloadFile(@PathVariable String bucketName, @PathVariable String fileName,
			HttpServletResponse httpResponse) {
		S3Operation downloadFile = s3OperationService.downloadFile(bucketName, fileName);
		httpResponse.setContentType("application/octet-stream");
		httpResponse.setHeader("Content-Disposition",
				String.format("inline; filename=\"%s\"", downloadFile.getFileName()));
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				outputStream.write(downloadFile.getFile());
				outputStream.flush();
			}
		};
	}
}
