package com.example.appduol.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch =  FetchType.LAZY)
  private Blog blog;
  private String text;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date")
  private LocalDateTime createdDate;
  @Column(name = "count_of_useful")
  private Integer countOfUseful;
  @Column(name = "count_of_unuseful")
  private Integer countOfUnuseful;
  private Boolean checked;
}
