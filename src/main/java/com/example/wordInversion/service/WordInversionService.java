package com.example.wordInversion.service;

import com.example.wordInversion.entity.WordInversion;
import com.example.wordInversion.repository.WordInversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordInversionService {

    private final WordInversionRepository repository;

    @Autowired
    public WordInversionService(WordInversionRepository repository) {
        this.repository = repository;
    }

    /**
     * Inverse all words in a request and stores request/response pair in db
     * @param sentence
     * @return the WordInversion entity (existing or newly created)
     */
    public WordInversion inverseWords(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            throw new IllegalArgumentException("Sentence can not be null or empty");
        }
        //Check if sentence already exists
        Optional<WordInversion> existing = repository.findByRequestSentenceIgnoreCase(sentence);
        if(existing.isPresent()){
            return existing.get();
        }
        String invertedSentence = inverseAllWords(sentence);
        WordInversion inversion = new WordInversion(sentence, invertedSentence);
        return repository.save(inversion);
    }

    /**
     * Check if a sentence already exists in database
     */
    public boolean existsBySentenceIgnoreCase(String sentence){
        return repository.existsByRequestSentenceIgnoreCase(sentence);
    }

    /**
     *Inverse each word while preserving the word order and spaces
     * @param sentence the input sentence
     * @return the sentence with each word reversed
     */

    private String inverseAllWords(String sentence) {
        StringBuilder result = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();

        for(char c : sentence.toCharArray()){
            if(Character.isWhitespace(c)){
                if(!currentWord.isEmpty()){
                    result.append(currentWord.reverse());
                    currentWord.setLength(0);
                }
                result.append(c);
            } else {
                currentWord.append(c);
            }
        }

        if(!currentWord.isEmpty()){
            result.append(currentWord.reverse());
        }

        return result.toString();
    }

    /**
     * Find all request/reponse pairs where the give word is present
     * @param word the word to search
     * @return list of WordInversions containing the word
     */
    public List<WordInversion> findByWord(String word) {
        if(word ==null||word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        return repository.findByWordInRequestOrResponse(word);
    }

    /**
     * Find all request where the give word is present
     * @param word the word to search
     * @return list of WordInversions containing the word
     */
    public List<WordInversion> findByWordInRequest(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        return repository.findByWordInRequest(word);
    }

    /**
     * Find all response where the give word is present
     * @param word the word to search
     * @return list of WordInversions containing the word
     */
     public List<WordInversion> findByWordInResponse(String word) {
        if (word == null || word.isEmpty()) {
             throw new IllegalArgumentException("Word cannot be null or empty");
         }
        return repository.findByWordInResponse(word);
    }

    /**
     * Find all request/response pairs
     * @return list all WordInversions
     */
    public List<WordInversion> getAllInversions() {
        return repository.findAll();
    }

    /**
     * Find the specific inversion by ID
     * @param id
     * @return the WordInversion Entity
     */
    public WordInversion getInversionById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Inversion not found with id: " + id));
    }
}
