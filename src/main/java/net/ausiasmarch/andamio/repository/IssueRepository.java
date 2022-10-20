package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
}
