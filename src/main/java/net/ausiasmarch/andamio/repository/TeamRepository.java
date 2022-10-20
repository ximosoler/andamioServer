package net.ausiasmarch.andamio.repository;

import java.util.List;
import net.ausiasmarch.andamio.entity.TeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

}
    
    
