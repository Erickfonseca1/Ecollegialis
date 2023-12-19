package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Authority;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.User;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String userHome(Principal principal) {
        User user = userRepository.findById(principal.getName()).get();
        List<Authority> authorities = user.getAuthorities();

        String result;

        //se authorities possuir ROLE_COORDENADOR, então redireciona para /coordenador
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDENADOR"))) {
            result = "/coordenador/home";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR"))) {
            result = "/professor/home";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            result = "/admin/home";
        } else {
            result = "/aluno/home";
        }


        return result;
    }
}
