package com.trackerimmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackerimmo.model.Ad;

public interface AdRepository extends JpaRepository<Ad, Long>{

}
