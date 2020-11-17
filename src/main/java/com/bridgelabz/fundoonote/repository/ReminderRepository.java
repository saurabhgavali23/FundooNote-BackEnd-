package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.ReminderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<ReminderDetails, Long> {
}
