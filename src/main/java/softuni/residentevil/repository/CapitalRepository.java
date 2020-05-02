package softuni.residentevil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.residentevil.domain.entities.Capital;

@Repository
public interface CapitalRepository  extends JpaRepository<Capital, String> {
}
