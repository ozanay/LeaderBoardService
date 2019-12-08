package com.leaderboard.provider.persistence.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "scores")
@Table(indexes = {@Index(name = "leaderboard_index", columnList = "scoreValue,user_id")})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column
    private double scoreValue;

    @Column
    private long timestamp;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
