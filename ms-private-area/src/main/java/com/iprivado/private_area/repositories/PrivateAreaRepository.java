package com.iprivado.private_area.repositories;

import com.iprivado.private_area.models.entity.PrivateArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateAreaRepository extends JpaRepository<PrivateArea, Long> {


}
