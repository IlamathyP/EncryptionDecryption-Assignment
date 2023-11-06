package com.springboot.AES.repository;

import com.springboot.AES.entity.SecretKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretKeyRepository extends JpaRepository<SecretKeyEntity,Integer> {


    SecretKeyEntity findBysecretKeyName(String secretKeyName);
}
