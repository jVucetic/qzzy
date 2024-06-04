package com.example.qzzy.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
