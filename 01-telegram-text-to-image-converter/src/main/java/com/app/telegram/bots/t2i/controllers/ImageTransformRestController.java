package com.app.telegram.bots.t2i.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.telegram.bots.t2i.ImageTransformService;
import com.app.telegram.bots.t2i.models.ImageInfo;

@RestController
@RequestMapping("api/v1")
public class ImageTransformRestController {
	private static final Logger logger = Logger.getLogger(ImageTransformRestController.class.getName());
	@Autowired
	private ImageTransformService service;
	@PostMapping("/direct")
	public void transformTextToImageDirect(@RequestBody ImageInfo imageInfo, HttpServletResponse response)
	{
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("image/png");
		logger.info(imageInfo.toString());
		service.directTextToImage(os, imageInfo);

	}
	
	@PostMapping("/camelOnly")
	public void transformCamelTextToImage(@RequestBody ImageInfo imageInfo, HttpServletResponse response)
	{
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("image/png");
		logger.info(imageInfo.toString());
		service.camelTextToImage(os, imageInfo);
	}
	
}
