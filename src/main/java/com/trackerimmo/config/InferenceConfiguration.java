package com.trackerimmo.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;


public class InferenceConfiguration {

	public static final class AdTranslator implements Translator<Image, NDList> {

		private static final List<String> CLASSES = Arrays.asList("covid-19", "normal");

		ImageFactory factory = ImageFactory.getInstance();
		
		@Override
		public NDList processInput(TranslatorContext ctx, Image input) {
			Image image = factory.fromImage(input);
			NDArray array = image.toNDArray(ctx.getNDManager());
			array = NDImageUtils.resize(array, 224).transpose(2,0,1).div(255.0f);
			return new NDList(array);
		}

		@Override
		public NDList processOutput(TranslatorContext ctx, NDList list) {
			return (NDList) list.singletonOrThrow();
//			NDArray probabilities = list.singletonOrThrow();
//			return new Classifications(CLASSES, probabilities);
		}

	}
    		
	@Bean
	public ImageFactory imageFactory() {
		return ImageFactory.getInstance();
	}

	@Bean
	public Criteria<Image, Classifications> criteria() {
		 return Criteria.builder()
		         .setTypes(Image.class, Classifications.class) // defines input and output data type
		         .optModelUrls("file:///var/models/my_resnet50") // search models in specified path
		         .optModelName("resnet50") // specify model file prefix
		         .build();
	}

	@Bean
	public ZooModel<Image, Classifications> model(@Qualifier("criteria") Criteria<Image, Classifications> criteria)
			throws MalformedModelException, ModelNotFoundException, IOException {
		return ModelZoo.loadModel(criteria);
	}

	/**
	 * Scoped proxy is one way to have a predictor configured and closed.
	 * 
	 * @param model object for which predictor is expected to be returned
	 * @return predictor object that can be used for inference
	 */
	@Bean(destroyMethod = "close")
	@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
	public Predictor<Image, DetectedObjects> predictor(ZooModel<Image, DetectedObjects> model) {
		return model.newPredictor();
	}

	/**
	 * Inject with @Resource or autowired. Only safe to be used in the try with
	 * resources.
	 * 
	 * @param model object for which predictor is expected to be returned
	 * @return supplier of predictor for thread-safe inference
	 */
	@Bean
	public Supplier<Predictor<Image, Classifications>> predictorProvider(ZooModel<Image, Classifications> model) {
		return model::newPredictor;
	}
}
