package com.trackerimmo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

@Service
public class CityComputing {

	private static final Logger logger = LoggerFactory.getLogger(CityComputing.class);

	Environment env;

	TokenizerModel tokenModel;
	TokenNameFinderModel nameFinderModel;
	SentenceModel sentenceModel;

	Tokenizer tokenFinder;
	NameFinderME nameFinder;
	SentenceDetectorME sentenceDetectorME;

	public CityComputing(Environment env) {
		this.env = env;
		String tokenModelFile = env.getProperty("nlp.tokenizer.file");
		Resource resource = new ClassPathResource(tokenModelFile);
		try (InputStream modelIn = resource.getInputStream()) {
			this.tokenModel = new TokenizerModel(modelIn);
			this.tokenFinder = new TokenizerME(tokenModel);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		String nerModelFile = env.getProperty("nlp.ner.sentence.file");
		Resource resource2 = new ClassPathResource(nerModelFile);
		try (InputStream modelIn = resource2.getInputStream()) {
			this.sentenceModel = new SentenceModel(modelIn);
			this.sentenceDetectorME = new SentenceDetectorME(sentenceModel);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		String nerModelLocation = env.getProperty("nlp.ner.location.file");
		Resource locationResource3 = new ClassPathResource(nerModelLocation);
		try (InputStream modelIn = locationResource3.getInputStream()) {
			this.nameFinderModel = new TokenNameFinderModel(modelIn);
			this.nameFinder = new NameFinderME(nameFinderModel);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

	public String[] tokenizeText(String text) {
		String[] tokens = tokenFinder.tokenize(text);
		return tokens;
	}

	public String[] sequenceFinder(String text) {
//		Span[] names = sentenceDetectorME.sentDetect(tokenizeText(text));
		String sentences[] = sentenceDetectorME.sentDetect(text);
		for (String s : sentences)
			System.out.println(s.toString());

		return sentences;
	}

	public List<String> locationFinder(String text) {
		String[] tokens = tokenizeText(text);
		Span locationsSpan[] = nameFinder.find(tokens);
		List<String> locations = new ArrayList<String>(1);
		for (Span s : locationsSpan) {
			locations.add(tokens[s.getStart()]);
		}

		return locations;
	}

	public void trainModel() {
		MarkableFileInputStreamFactory in = null;
		ObjectStream sampleStream = null;
		TrainingParameters params = new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 70);
		params.put(TrainingParameters.CUTOFF_PARAM, 1);

		try {
			String tokenModelFile = env.getProperty("nlp.annotated.locationentences");
			Resource resource = new ClassPathResource(tokenModelFile);
			File modelIn = resource.getFile();
			in = new MarkableFileInputStreamFactory(modelIn);

			sampleStream = new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));

			nameFinderModel = NameFinderME.train("fr", null, sampleStream, params,
					TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));

			File output = new File("ner-custom-model.bin");
			FileOutputStream outputStream = new FileOutputStream(output);
			nameFinderModel.serialize(outputStream);

		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
