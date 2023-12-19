package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Authority;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Authority.AuthorityId;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId>{
    
}
