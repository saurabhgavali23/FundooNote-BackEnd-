package com.bridgelabz.fundoonote.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class LabelDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String labelName;

    @JsonIgnore
    @Transient
    LocalDateTime now = LocalDateTime.now();

    @CreationTimestamp
    public LocalDateTime createDate;

    @UpdateTimestamp
    public LocalDateTime modifiedDate = now;

    public LabelDetails(Long id, String labelName) {
        this.createDate = now;
        this.id = id;
        this.labelName = labelName;
    }
}