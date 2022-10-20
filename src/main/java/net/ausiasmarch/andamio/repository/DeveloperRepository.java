package net.ausiasmarch.andamio.repository;

import java.util.List;
import net.ausiasmarch.andamio.entity.DeveloperEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {

}
