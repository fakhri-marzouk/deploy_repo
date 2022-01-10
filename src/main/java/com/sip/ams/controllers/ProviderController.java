package com.sip.ams.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sip.ams.entities.Provider;
import  com.sip.ams.repositories.ProviderRepository;


@Controller
@RequestMapping("/provider/")
public class ProviderController {

	private final ProviderRepository providerRepository;
	public static String providersDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";

	
	@Autowired
	public ProviderController(ProviderRepository providerRepository) {
	this.providerRepository = providerRepository;
	}
	
	@GetMapping("list")
	//@ResponseBody
	public String listProviders(Model model) {
		List<Provider> lp = (List<Provider>)providerRepository.findAll();
		if(lp.size()==0) lp=null ;
		model.addAttribute("providers",lp);
 	return "provider/listProviders";
	//System.out.println(lp);
	//return "Nombre de fournisseur = " + lp.size();
	}
	
	@GetMapping("add")
	public String showAddProviderForm(Model model) {
	Provider provider = new Provider();// object dont la valeur des attributs par defaut
	model.addAttribute("provider", provider);
	return "provider/addProvider";
	}
	
	@PostMapping("add")
	public String addProvider(@Valid Provider provider, BindingResult result,@RequestParam("files") MultipartFile[] files) 
	{
	if (result.hasErrors()) {
		return "provider/addProvider";
	}
	StringBuilder fileName = new StringBuilder();
	MultipartFile file = files[0];
	Path fileNameAndPath = Paths.get(providersDirectory, file.getOriginalFilename());
	fileName.append(file.getOriginalFilename());
	try {
	Files.write(fileNameAndPath, file.getBytes());
	} catch (IOException e) {
	e.printStackTrace();
	}
	provider.setLogo(fileName.toString());

	providerRepository.save(provider);
	return "redirect:list";
	}
	

	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") long id, Model model) {
	//long id2 = 100L;
	Provider provider = providerRepository.findById(id)
	.orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
	System.out.println("suite du programme...");
	providerRepository.delete(provider);
	/*model.addAttribute("providers", providerRepository.findAll());
	return "provider/listProviders";*/
	return "redirect:../list";
	}
	
	@GetMapping("edit/{id}")
	public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
		Provider provider = providerRepository.findById(id)
		.orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
		model.addAttribute("provider", provider);
		return "provider/updateProvider";
		}
	
	
	@PostMapping("update")
	public String updateProvider(@Valid Provider provider,
									BindingResult result,
									Model model,
									@RequestParam("files") MultipartFile[] files) {
		StringBuilder fileName = new StringBuilder();
		MultipartFile file = files[0];
		Path fileNameAndPath = Paths.get(providersDirectory, file.getOriginalFilename());
		fileName.append(file.getOriginalFilename());
		try {
		Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
		e.printStackTrace();
		}
		provider.setLogo(fileName.toString());

	providerRepository.save(provider);
	return"redirect:list";
	}
	
}
