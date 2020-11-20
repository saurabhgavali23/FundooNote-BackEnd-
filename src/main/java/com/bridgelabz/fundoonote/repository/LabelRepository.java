package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.LabelDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<LabelDetails, Long> {
}
