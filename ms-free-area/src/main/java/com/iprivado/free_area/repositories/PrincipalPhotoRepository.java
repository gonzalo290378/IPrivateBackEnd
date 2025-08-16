package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrincipalPhotoRepository extends JpaRepository<PrincipalPhoto, Long> {

}
