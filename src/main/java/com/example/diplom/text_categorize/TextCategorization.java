package com.example.diplom.text_categorize;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;
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

@Slf4j
public class TextCategorization {

    public static void main(String[] args) {
        trainCategorizator();
    }

    public static void trainCategorizator() {
        String[] categories = {"NONE", "TASK", "MEETING", "TASK_AND_MEETING"};

        int nGram = 5;
        DynamicLMClassifier<NGramProcessLM> classifier = DynamicLMClassifier.createNGramProcess(categories, nGram);

        trainClassifier(classifier);
        saveClassifierToFile(classifier, "src/main/resources/classifier.ser");
    }

    public static Map<String, String> getEmailClassificationMap(String filePath) throws IOException {
        Map<String, String> emailClassificationMap = new HashMap<>();

        Files.lines(Paths.get(filePath)).forEach(line -> {
            String[] parts = line.split("\",");

            if (parts.length == 2) {
                String email = parts[0].replaceFirst("\"", "");
                String classification = parts[1].trim();  // Trim leading and trailing whitespace
                emailClassificationMap.put(email, classification);
            }
        });

        return emailClassificationMap;
    }

    private static void trainClassifier(DynamicLMClassifier<NGramProcessLM> classifier) {

        Map<String, String> emailClassificationMap;
        try {
            emailClassificationMap = getEmailClassificationMap("src/main/resources/emails.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Classified<CharSequence>> list = new ArrayList<>();
        emailClassificationMap.forEach((key, value) -> {
            list.add(new Classified<>(key, new Classification(value)));
        });
        list.forEach(classifier::handle);
    }

    private static void saveClassifierToFile(DynamicLMClassifier<NGramProcessLM> classifier, String filePath) {
        try {
            AbstractExternalizable.compileTo(classifier, new File(filePath));
            log.info("Classifier saved to: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static LMClassifier loadClassifierFromFile(String filePath) throws IOException, ClassNotFoundException {
        return (LMClassifier) AbstractExternalizable.readObject(new File(filePath));
    }

}
