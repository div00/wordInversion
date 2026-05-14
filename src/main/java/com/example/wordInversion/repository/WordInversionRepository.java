package com.example.wordInversion.repository;

import com.example.wordInversion.entity.WordInversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordInversionRepository extends JpaRepository<WordInversion, Long> {

    @Query("SELECT w FROM WordInversion w WHERE LOWER(w.requestSentence) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<WordInversion> findByWordInRequest(@Param("word") String word);

    @Query("SELECT w FROM WordInversion w WHERE LOWER(w.responseSentence) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<WordInversion> findByWordInResponse(@Param("word") String word);

    @Query("SELECT w FROM WordInversion w WHERE LOWER(w.requestSentence) LIKE LOWER(CONCAT('%', :word, '%')) OR LOWER(w.responseSentence) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<WordInversion> findByWordInRequestOrResponse(@Param("word") String word);

    @Query("SELECT w FROM WordInversion w ORDER BY w.createdAt DESC")
    List<WordInversion> findAllOrderedByCreatedAtDesc();

    /**
     * Find an existing inversion by the exact request sentence
     */
    Optional<WordInversion> findByRequestSentenceIgnoreCase(String requestSentence);

    boolean existsByRequestSentenceIgnoreCase(String requestSentence);

}
