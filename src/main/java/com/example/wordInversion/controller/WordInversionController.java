package com.example.wordInversion.controller;

import com.example.wordInversion.dto.InversionReponse;
import com.example.wordInversion.dto.InversionRequest;
import com.example.wordInversion.entity.WordInversion;
import com.example.wordInversion.service.WordInversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/inversions")
public class WordInversionController {

    private final WordInversionService inversionService;

    @Autowired
    public WordInversionController(WordInversionService inversionService){
        this.inversionService = inversionService;
    }

    /**
     * POST /api/inversions/inverse
     * Inverse all words in a sentence
     * Request body: { "sentence": "abc def"}
     * Response: {"id": 1, "request": "abc def", "response": "cba def", "createdAt":".."}
     * If sentence already exists, return existing record with "isCached" : true
     */
    @PostMapping("inverse")
    public ResponseEntity<InversionReponse> inverseWords(@RequestBody InversionRequest request){
        try{
            //Check if already exists
            boolean alreadyExists = inversionService.existsBySentenceIgnoreCase(request.getSentence());

            WordInversion inversion = inversionService.inverseWords(request.getSentence());
            return ResponseEntity.ok(new InversionReponse(inversion, alreadyExists));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     *  GET /api/inversions
     * @return Get full list of all request/reponse pairs
     */

    @GetMapping
    public ResponseEntity<List<InversionReponse>> getAllInversions(){
        List<WordInversion> inversions = inversionService.getAllInversions();
        List<InversionReponse> reponses = inversions.stream()
                .map(InversionReponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reponses);
    }

    /**
     * GET /api/inversions/{id}
     * @param id
     * @return Get a specific inversion by id
     */

    @GetMapping("/{id}")
    public ResponseEntity<InversionReponse> getInversionById(@PathVariable Long id){
        try {
            WordInversion inversion = inversionService.getInversionById(id);
            return ResponseEntity.ok(new InversionReponse(inversion));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/inversion/search?word=abc
     * @param word
     * @return Find all request/response pairs that contain specific word
     */

    @GetMapping("/search")
    public ResponseEntity<List<InversionReponse>> searchByWord(@RequestParam String word){
        try {
            List<WordInversion> inversions = inversionService.findByWord(word);
            List<InversionReponse> responses = inversions.stream()
                    .map(InversionReponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/inversion/search/request?word=abc
     * @param word
     * @return Find all request that contain specific word
     */
    @GetMapping("/search/request")
    public ResponseEntity<List<InversionReponse>> searchByWordInRequest(@RequestParam String word){
       try {
           List<WordInversion> inversions = inversionService.findByWordInRequest(word);
           List<InversionReponse> reponses = inversions.stream()
                   .map(InversionReponse::new)
                   .collect(Collectors.toList());
           return ResponseEntity.ok(reponses);
       }catch(IllegalArgumentException e){
           return ResponseEntity.badRequest().build();
       }
    }

    /**
     * GET /api/inversion/search/response?word=abc
     * @param word
     * @return Find all response that contain specific word
     */
    @GetMapping("/search/response")
    public ResponseEntity<List<InversionReponse>> searchByWordInResponse(@RequestParam String word){
        try {
            List<WordInversion> inversions = inversionService.findByWordInResponse(word);
            List<InversionReponse> reponses = inversions.stream()
                    .map(InversionReponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reponses);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
