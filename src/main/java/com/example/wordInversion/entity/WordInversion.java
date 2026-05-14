package com.example.wordInversion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "word_inversion")
@NoArgsConstructor
@Getter
@Setter
public class WordInversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_sentence", nullable = false, length = 2000)
    private String requestSentence;

    @Column(name = "response_sentence", nullable = false, length = 2000)
    private String responseSentence;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public WordInversion(String requestSentence,String reponseSentence){
        this.requestSentence = requestSentence;
        this.responseSentence = reponseSentence;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "WordInversion {" +
                "id=" + id +
                ", requestSentance= '"+ requestSentence + '\'' +
                ", responseSentance= '"+ responseSentence + '\'' +
                ", createdAt=" + createdAt + '}';
    }
}
