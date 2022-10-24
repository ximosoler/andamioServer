package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {

    
      DeveloperEntity findByUsernameAndPassword(String username, String password);
    
    
}
