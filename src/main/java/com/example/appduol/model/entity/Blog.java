package com.example.appduol.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class Blog {

  private static final int MIN_TITLE_LENGTH = 10;
  private static final int MAX_TITLE_LENGTH = 255;
  private static final int MIN_CONTENT_LENGTH = 10;
  private static final int MAX_CONTENT_LENGTH = 100000;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Length(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH, message = "Title must be between " + MIN_TITLE_LENGTH + " and " +
      MAX_TITLE_LENGTH + " characters long")
  @NotEmpty(message = "Please enter the title")
  private String title;

  @NotNull
  @ManyToOne
  private Topic topic;

  @Length(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH, message =
      "Content must be between " + MIN_CONTENT_LENGTH + " and " + MAX_CONTENT_LENGTH +
          " characters long")
  @NotEmpty(message = "Please enter the content")
  @Column(length = 100000)
  private String content;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "count_of_reads")
  private Integer countOfReads;

  private Boolean checked;
}
