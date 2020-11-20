package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "labelDetails")
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "labelDetails")
    private List<NoteDetails> noteDetails;

    public LabelDetails(LabelDTO labelDTO) {
        this.createDate = now;
        this.labelName = labelDTO.labelName;
    }
}
