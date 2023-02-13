package com.trackerimmo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DL4JProcessing {

	@Autowired
	Environment env;

	MultiLayerNetwork model;
	ComputationGraph pretrainedNet;

	@SuppressWarnings("deprecation")
	public DL4JProcessing(Environment env) {
		try {
//			String modelPath = env.getProperty("deeplearning.model");
//			File file = new ClassPathResource(modelPath).getFile();
//			model = KerasModelImport.importKerasSequentialModelAndWeights(file.getPath());
			ZooModel<?> zooModel = VGG16.builder().build();
			pretrainedNet = (ComputationGraph) zooModel.initPretrained(PretrainedType.IMAGENET);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void processIamge(String imagePath) {

		// Load the image into memory
		BufferedImage image;
		try {
			File imageFile = new ClassPathResource(imagePath).getFile();
			image = ImageIO.read(imageFile);

			// Convert the image into a numerical representation
			ImageLoader imageLoader = new ImageLoader(224, 224, 3);
			INDArray imageArray = imageLoader.asMatrix(imageFile);
			
			// Normalize the image data
//			imageArray = imageArray.div(255.0);
			INDArray input = imageArray.reshape(224, 224, 3);


			// Obtain the embeddings
			INDArray embeddings = pretrainedNet.outputSingle(imageArray);
			System.out.println("Embeddings: " + embeddings);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
