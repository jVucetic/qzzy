package com.example.qzzy.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

}
