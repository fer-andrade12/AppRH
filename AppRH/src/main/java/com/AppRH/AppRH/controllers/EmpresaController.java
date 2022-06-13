package com.AppRH.AppRH.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Empresa;
import com.AppRH.AppRH.repository.EmpresaRepository;



@Controller
public class EmpresaController {
	

	@Autowired
	private EmpresaRepository er;
	
	// GET que chama o FORM que cadastra empresa
	@RequestMapping("/cadastrarEmpresa")
	public String form() {
		return "empresa/form-empresa";
	}
	
	// POST que cadastra a empresa
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.POST)
	public String form(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarEmpresa";
		} 

		er.save(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso!");
		return "redirect:/cadastrarEmpresa";
	}

	// GET que lista as empresas
		@RequestMapping("/empresas")
		public ModelAndView listaEmpresas() {
		ModelAndView mv = new ModelAndView("empresa/lista-empresa");
		Iterable<Empresa> empresas = er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
		
		
	// GET que lista dependentes e detalhes dos funcionário
	@RequestMapping("/empresa/{id}")
	public ModelAndView detalhesEmpresa(@PathVariable("id") int id) {
		Empresa empresa = er.findById(id);
		ModelAndView mv = new ModelAndView("empresa/detalhes-empresa");
		 mv.addObject("empresa", empresa);
		 return mv;
	}
		
		
		
	// GET que deleta a empresa
		@RequestMapping("/deletarEmpresa")
		public String deletarEmpresa(int id) {
			Empresa empresa = er.findById(id);
			er.delete(empresa);
			return "redirect:/empresas";
		}
	
		
	// Métodos que atualizam EMPRESA
	// GET que chama o formulário de edição da EMPRESA
	@RequestMapping("/editar-empresa")
	public ModelAndView editarEmpresa(int id) {
		Empresa empresa = er.findById(id);
		ModelAndView mv = new ModelAndView("empresa/update-empresa");
		mv.addObject("empresa", empresa);
		return mv;
	}
		
	// POST do FORM que atualiza a empresa 
	@RequestMapping(value = "/editar-empresa", method = RequestMethod.POST)
	public String updateEmpresa(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		er.save(empresa);
		attributes.addFlashAttribute("success", "Empresa alterada com sucesso!");
	// **** ATENÇÃO **** //
		int idInteger =  empresa.getId();
		String id = "" + idInteger;
		return "redirect:/empresa/" + id;
	}
	
	
}
