package com.crypto.AppOnboarding.repository;

import com.crypto.AppOnboarding.entity.AppEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoRepository extends JpaRepository<AppEntity,Integer> {

}
