package com.piotr.url_shortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

public interface LinksRepository extends JpaRepository<Link, UUID> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM link WHERE created_at < :recordLiveTime", nativeQuery = true)
    void deleteOldRecords(@Param("recordLiveTime") Instant recordLiveTime);
}

