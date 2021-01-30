package com.assistant.repos;

import com.assistant.domain.mysql.SmsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsInfoRepos extends JpaRepository<SmsInfo,Long> {
}
