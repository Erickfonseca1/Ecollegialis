package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusReuniao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {


}