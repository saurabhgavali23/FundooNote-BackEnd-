package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.LabelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface LabelRepository extends JpaRepository<LabelDetails, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE label_details u SET u.label_name =?1, u.modified_date =?2 WHERE u.id =?3 AND u.user_id =?4", nativeQuery = true)
    int updateLabel(String labelName, LocalDateTime modifiedDate, Long labelId, long userId);

    @Query(value = "SELECT * FROM label_details u WHERE u.user_id =?1 AND u.label_name IS NOT NULL", nativeQuery = true)
    List<LabelDetails> getLabelList(Long userId);
}
