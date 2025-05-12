package br.com.hahn.msuploadfile.domain.repository;

import br.com.hahn.msuploadfile.domain.model.NamesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamesListRepository extends JpaRepository<NamesList, Long> {

}
