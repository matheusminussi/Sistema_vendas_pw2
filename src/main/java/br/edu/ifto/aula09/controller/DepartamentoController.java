package br.edu.ifto.aula09.controller;

import br.edu.ifto.aula09.model.entity.Departamento;
import br.edu.ifto.aula09.model.entity.Funcionario;
import br.edu.ifto.aula09.model.repository.DepartamentoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Transactional
@Scope("request")
@Controller
@RequestMapping("departamento")
public class DepartamentoController {

    @Autowired
    DepartamentoRepository departamentoRepository;
    @Autowired
    Departamento departamento;//será criado o objeto na sessão

    @GetMapping("/form")
    public String form(Departamento departamento) {
        return "departamento/form";
    }

    @PostMapping("/funcionario/add")
    public ModelAndView funcionarioAdd(Funcionario funcionario) {
        departamento.getFuncionarios().add(funcionario);
        funcionario.setDepartamento(departamento);
        return new ModelAndView("redirect:/departamento/form");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("departamento") Departamento departamento, BindingResult bindigResult, RedirectAttributes attributes) {
        try {
            if (bindigResult.hasErrors()) {
                return "departamento/form";
            }

            departamentoRepository.save(departamento);
            attributes.addFlashAttribute("successMessage", "Salvo com sucesso!");
            return "redirect:/departamento/form";
        } catch (DataIntegrityViolationException e){
            bindigResult.rejectValue("nome", "error.departamento", "Já existe um departamento com este nome.");
            return "departamento/form";
        }
    }

    @GetMapping("/list")
    public ModelAndView listar(@RequestParam(value = "nome", required = false) String nome, ModelMap model) {
        List<Departamento> departamentos;
        if (nome != null && !nome.isEmpty()) {
            departamentos = departamentoRepository.findByNome(nome);
        } else {
            departamentos = departamentoRepository.departamentos();
        }
        model.addAttribute("departamentos", departamentos);
        return new ModelAndView("/departamento/list");
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("departamento", departamentoRepository.departamento(id));
        return new ModelAndView("/departamento/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Departamento departamento) {
        departamentoRepository.save(departamento);
        return new ModelAndView("redirect:/departamento/list");
    }

    //@PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        departamentoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Departamento excluído com sucesso!");
        return "redirect:/departamento/list";
    }
}
