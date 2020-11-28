package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.CollaboratorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "collaboratorDetails")
public class CollaboratorDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String emailId;

    @JsonIgnore
    @Transient
    LocalDateTime now = LocalDateTime.now();

    @CreationTimestamp
    public LocalDateTime createDate;

    @UpdateTimestamp
    public LocalDateTime modifiedDate = now;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "collaboratorDetails")
    private List<NoteDetails> noteDetails;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "collaboratorDetails")
    private List<UserDetails> userDetails;

    public CollaboratorDetails(CollaboratorDTO collaboratorDTO){
        this.createDate = now;
        this.emailId = collaboratorDTO.emailId;
    }
}
