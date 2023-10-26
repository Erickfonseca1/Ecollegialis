package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Text;

@Controller
@RequestMapping("/")
public class AlunoController {

    @RequestMapping("/aluno")
    public ModelAndView showHomepage(ModelAndView mv) {
        mv.addObject("nome", "FelaDaPuta");
        mv.setViewName("aluno");
        return mv;
    }
}
