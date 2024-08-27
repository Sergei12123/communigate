package com.example.communigate.text_categorize;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.stats.MultivariateEstimator;
import com.aliasi.util.AbstractExternalizable;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class TextCategorization {


    public static final String EMAILS_TXT_PATH = "src/main/resources/emails.txt";
    public static final String CLASSIFIER_SER_PATH = "src/main/resources/classifier.ser";

    public static void main(String[] args) {
        trainCategorizator(true);
    }

    public static DynamicLMClassifier<NGramProcessLM> trainCategorizator(final boolean storeModelFile) {
        String[] categories = {"NONE", "TASK", "MEETING", "TASK_AND_MEETING"};

        int nGram = 3;
        DynamicLMClassifier<NGramProcessLM> classifier = DynamicLMClassifier.createNGramProcess(categories, nGram);

        trainClassifier(classifier);
        if (storeModelFile) {
            saveClassifierToFile(classifier);
        }
        return classifier;
    }

    public static Map<String, String> getEmailClassificationMap(String filePath) throws IOException {
        Map<String, String> emailClassificationMap = new HashMap<>();

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                String[] parts = line.split("\",");

                if (parts.length == 2) {
                    String email = parts[0].replaceFirst("\"", "");
                    String classification = parts[1].trim();  // Trim leading and trailing whitespace
                    emailClassificationMap.put(email, classification);
                }
            });
        }

        return emailClassificationMap;
    }

    private static void trainClassifier(DynamicLMClassifier<NGramProcessLM> classifier) {

        Map<String, String> emailClassificationMap = new HashMap<>();
        try {
            emailClassificationMap = getEmailClassificationMap(EMAILS_TXT_PATH);
        } catch (IOException e) {
            log.error("Can't find training sample");
        }

        List<Classified<CharSequence>> list = new ArrayList<>();
        emailClassificationMap.forEach((key, value) -> list.add(new Classified<>(key, new Classification(value))));
        list.forEach(classifier::handle);
    }

    private static void saveClassifierToFile(DynamicLMClassifier<NGramProcessLM> classifier) {
        try {
            AbstractExternalizable.compileTo(classifier, new File(TextCategorization.CLASSIFIER_SER_PATH));
            log.info("Classifier saved to: " + TextCategorization.CLASSIFIER_SER_PATH);
        } catch (IOException e) {
            log.error("Can't save classifier model");
        }

    }

    public static LMClassifier<NGramProcessLM, MultivariateEstimator> loadClassifierFromFile(String filePath) throws IOException, ClassNotFoundException {
        Object readObject = AbstractExternalizable.readObject(new File(filePath));
        if (readObject instanceof LMClassifier<?, ?> classifier) {
            @SuppressWarnings("unchecked")
            LMClassifier<NGramProcessLM, MultivariateEstimator> nGramClassifier = (LMClassifier<NGramProcessLM, MultivariateEstimator>) classifier;
            return nGramClassifier;
        }
        return null;
    }

}
