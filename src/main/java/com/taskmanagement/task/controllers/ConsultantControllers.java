package com.taskmanagement.task.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taskmanagement.task.models.ActivityEntry;
import com.taskmanagement.task.models.ActivityEntryDto;
import com.taskmanagement.task.models.Consultant;
import com.taskmanagement.task.models.ConsultantDto;
import com.taskmanagement.task.models.ConsultantProjectDetails;
import com.taskmanagement.task.models.Project;
import com.taskmanagement.task.services.ConsultantProjectDetailsRepository;
import com.taskmanagement.task.services.ConsultantRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/consultants")
public class ConsultantControllers {
    
    @Autowired
    private ConsultantRepository repo;
    
    @Autowired
    private final ConsultantProjectDetailsRepository detailsRepository;
    
    @Autowired
    public ConsultantControllers(ConsultantRepository repo, ConsultantProjectDetailsRepository detailsRepository) {
        this.repo = repo;
        this.detailsRepository = detailsRepository; 
    }

    
    @ModelAttribute("jobTitles")
    public List<ConsultantProjectDetails> getJobTitles() {
        return detailsRepository.findAll();
    }
    
	/*
	 * @GetMapping("/getAllConsultant") public List<ConsultantProjectDetails>
	 * showConsultantList1() { return detailsRepository.findAll(); }
	 */

    @GetMapping({"", "/"})
    public String showConsultantList(Model model) {
        List<Consultant> consultants = repo.findAll(Sort.by(Sort.Direction.DESC, "id")); // son eklenen danışman başa gelir
        model.addAttribute("consultants", consultants);
        return "consultants/index";
    }

	
	  @GetMapping("/create") 	   
	  public String showCreate(Model model) {
		  
		  ConsultantDto consultantDto = new ConsultantDto(); 
		  model.addAttribute("consultantDto", consultantDto);
		  
		  return "consultants/CreateConsultant"; 
		  
	  }
	 

	  @PostMapping("/create")
	  public String createConsultant(
	      @Valid @ModelAttribute ConsultantDto consultantDto,
	      BindingResult result
	  ) {
	      if (result.hasErrors()) {
	          return "consultants/CreateConsultant";
	      }
	      
	      Consultant consultant = new Consultant();
	      consultant.setFirstName(consultantDto.getFirstName());
	      consultant.setLastName(consultantDto.getLastName());
	      consultant.setAddress(consultantDto.getAddress());
	      consultant.setPhone(consultantDto.getPhone());
	      consultant.setEmail(consultantDto.getEmail());

	      // ConsultantDto'dan gelen listeyi Set'e dönüştür
	      Set<ConsultantProjectDetails> consultantDetails = new HashSet<>(consultantDto.getDetails());
	      
	      for (ConsultantProjectDetails detail : consultantDetails) {
	          // Her bir detail'e consultant'ı ata
	          detail.getConsultants().add(consultant);
	      }
	      consultant.setDetails(consultantDetails);
	      
	      consultant.setActive(consultantDto.isActive());
	      
	      repo.save(consultant);  // Veritabanına kaydet
	      
	      return "redirect:/consultants";
	  }
    // Güncelleme/Editleme işlemleri :

    @GetMapping("/edit")
    public String showEditPage(
        Model model,
        @RequestParam Long id // id parametresini Long türünde al
    ) {
        try {
            // Verilen id'ye göre danışmanı al
            Consultant consultant = repo.findById(id.intValue())
                .orElseThrow(() -> new Exception("Danışman bulunamadı"));

            // ConsultantDto nesnesi oluştur
            ConsultantDto consultantDto = new ConsultantDto();

            // Consultant bilgilerini ConsultantDto'ya aktar
            consultantDto.setId(consultant.getId());
            consultantDto.setFirstName(consultant.getFirstName());
            consultantDto.setLastName(consultant.getLastName());
            consultantDto.setAddress(consultant.getAddress());
            consultantDto.setEmail(consultant.getEmail());
            consultantDto.setPhone(consultant.getPhone());
            
            // Danışmanın detaylarını güncelle
            consultantDto.setDetails(consultant.getDetails());

            // ConsultantDto'yu model'e ekle
            model.addAttribute("consultantDto", consultantDto);

        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getLocalizedMessage());
            return "redirect:/consultants";
        }

        return "consultants/EditConsultant";
    }
    
    
    @PostMapping("/edit")
    public String updateConsultant(Model model, @Valid @ModelAttribute ConsultantDto consultantDto, BindingResult result) {
        if (result.hasErrors()) {
            return "consultants/edit";
        }

        // Veritabanından danışmanı al
        Optional<Consultant> optionalConsultant = repo.findById(consultantDto.getId().intValue());
        if (optionalConsultant.isPresent()) {
            Consultant consultant = optionalConsultant.get();
            consultant.setFirstName(consultantDto.getFirstName());
            consultant.setLastName(consultantDto.getLastName());
            consultant.setEmail(consultantDto.getEmail());
            consultant.setPhone(consultantDto.getPhone());
            consultant.setAddress(consultantDto.getAddress());

            // ConsultantProjectDetails güncelle
            Long detailsId = consultantDto.getDetailsId();
            if (detailsId != null) {
                // Bu kısımda detailsId'ye göre ConsultantProjectDetails'ı bulup atanmalı
                ConsultantProjectDetails consultantDetails = detailsRepository.findById(detailsId).orElse(null);
                if (consultantDetails != null) {
                    // Sadece yeni detayı ekleyin, detayları temizleme
                    consultant.setDetails(new HashSet<>(Arrays.asList(consultantDetails)));
                }
            }

            repo.save(consultant);
            model.addAttribute("consultant", consultant);
        } else {
            model.addAttribute("error", "Consultant not found.");
            return "redirect:/consultants";
        }

        return "redirect:/consultants";
    }
    
    @PostMapping("/delete")
    public String deleteConsultant(@RequestParam int id) {
        Consultant consultant = repo.findById(id).orElse(null);
        if (consultant != null) {
            List<ConsultantProjectDetails> notes = detailsRepository.findByConsultantId(consultant.getId());
            detailsRepository.deleteAll(notes); // Önceden notları siliyoruz
            repo.delete(consultant); // Şimdi konsültanı silebiliriz
        }

        return "redirect:/consultants";
    }
}
