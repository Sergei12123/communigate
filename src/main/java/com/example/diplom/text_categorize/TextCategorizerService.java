package com.example.diplom.text_categorize;

import com.aliasi.classify.Classification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.stats.MultivariateEstimator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TextCategorizerService {

    private final LMClassifier<NGramProcessLM, MultivariateEstimator> textCategorizer;

    public EmailCategory classifyTexts(final String text) {
        Classification classification = textCategorizer.classify(text);
        return EmailCategory.valueOf(classification.bestCategory());
    }

}
