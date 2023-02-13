package com.trackerimmo.service;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDList;
import jakarta.annotation.Resource;


public class ImageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

	@Value("${file.upload-dir}")
	String FILE_DIRECTORY;

	@Autowired
	Environment env;

	@Resource
	private Supplier<Predictor<Image, NDList>> predictorProvider;


	private ImageFactory imageFactory;

	private void initPredictor(String name) {
	    		
		try (var predictor = predictorProvider.get()) {
			File file = new ClassPathResource(name).getFile();
			Path path = file.toPath();
			var results = predictor.predict(imageFactory.fromFile(path));

			Image img = imageFactory.fromFile(path);

			for (var result : results) {
				logger.info("result {}", result.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processIamge(String name) {
		String imageModel = env.getProperty("prediction.image");
		initPredictor(name);
		File folder = new File(FILE_DIRECTORY);
		if (!folder.exists()) {
			folder.mkdir();
		}

		File[] listOfFiles = folder.listFiles();

	}

}
