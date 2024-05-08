package com.taskmanagement.task.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.taskmanagement.task.models.ProjectDto;
import com.taskmanagement.task.services.ActivityEntryRepository;
import com.taskmanagement.task.services.ConsultantRepository;
import com.taskmanagement.task.services.ConsultantService;
import com.taskmanagement.task.services.ProjectRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/activities")
public class ActivityControllers {

	   @Autowired
	    private ConsultantRepository consultantRepository;

	    @Autowired
	    private ProjectRepository projectRepository;

	    @Autowired
	    private ActivityEntryRepository activityEntryRepository;

	   
	        @GetMapping({"", "/"})
	        public String showActivityList(Model model) {
	            List<ActivityEntry> activities = activityEntryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // Tüm aktiviteleri al
	            model.addAttribute("activities", activities);

	            return "activities/index"; // activities/index.html sayfasına yönlendir
	        }
	    

	    @GetMapping("/create")
	    public String activityCreate(Model model) {
	        List<Consultant> consultants = consultantRepository.findAll();
	        List<Project> projects = projectRepository.findAll();
	        model.addAttribute("consultants", consultants);
	        model.addAttribute("projects", projects);
	        model.addAttribute("activityEntryDto", new ActivityEntryDto());
	        return "activities/CreateActivity"; // Örnek olarak "activities/create" sayfasına yönlendiriyoruz, gerçek sayfa adınıza göre değiştirin
	    }
	    
	    @PostMapping("/create")
	    public String createActivity(@Valid @ModelAttribute("activityEntryDto") ActivityEntryDto activityEntryDto, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            List<Consultant> consultants = consultantRepository.findAll();
	            List<Project> projects = projectRepository.findAll();
	            model.addAttribute("consultants", consultants);
	            model.addAttribute("projects", projects);
	            return "activities/CreateActivity";
	        }

	        Consultant consultant = consultantRepository.findById(activityEntryDto.getConsultantId()).orElse(null);
	        Project project = projectRepository.findById(activityEntryDto.getProjectId()).orElse(null);

	        if (consultant == null || project == null) {
	            return "redirect:/activities/CreateActivity"; 
	        }
	        
	        // Danışmanın durumunu kontrol et
	        if (!consultant.isActive()) {
	            // Danışman aktif değilse aktivite girişi yapmasına izin verme
	            return "redirect:/activities"; // veya başka bir hata sayfasına yönlendir
	        }
	        
	        // Etkinlik bitiş tarihini al
	        LocalDate activityEndDate = activityEntryDto.getActivityEndDate();


	        ActivityEntry activityEntry = new ActivityEntry();
	        activityEntry.setConsultant(consultant);
	        activityEntry.setProject(project);
	        activityEntry.setActivityDescription(activityEntryDto.getActivityDescription()); // DTO'dan gelen açıklamayı ayarla
	        activityEntry.setActivityEndDate(activityEndDate); // Etkinlik bitiş tarihini ayarla
	        activityEntryRepository.save(activityEntry);

	        return "redirect:/activities";
	        
	    }
	    
	    @GetMapping("/edit")
	    public String showActivityPage(Model model, @RequestParam String id) {
	        try {
	            int activityId = Integer.parseInt(id);
	            ActivityEntry activityEntry = activityEntryRepository.findById(activityId).orElse(null);
	            if (activityEntry == null) {
	                return "redirect:/activities";
	            }

	            ActivityEntryDto activityDto = new ActivityEntryDto();
	            activityDto.setId(activityEntry.getId()); // Aktivitenin ID'sini ayarla
	            activityDto.setActivityDescription(activityEntry.getActivityDescription());
	            activityDto.setActivityEndDate(activityEntry.getActivityEndDate());

	            // Edit sayfasında mevcut atamaları göstermek için
	            model.addAttribute("activityEntry", activityEntry);
	            model.addAttribute("activityDto", activityDto);

	            // Veritabanından tüm danışmanları getir
	            List<Consultant> consultants = consultantRepository.findAll(); 
	            model.addAttribute("consultants", consultants);

	            List<Project> projects = projectRepository.findAll(); 
	            model.addAttribute("projects", projects);

	            return "activities/EditActivity";
	        } catch (NumberFormatException e) {
	            // ID sayıya dönüştürülemediğinde
	            return "redirect:/activities";
	        }
	    }


	    @PostMapping("/edit")
	    public String updateActivity(Model model, @RequestParam String id, @Valid @ModelAttribute ActivityEntryDto activityDto, BindingResult result) {
	        try {
	            int activityId = Integer.parseInt(id);
	            if (result.hasErrors()) {
	                return "activities/EditActivity";
	            }

	            ActivityEntry activityEntry = activityEntryRepository.findById(activityId).orElse(null);
	            if (activityEntry != null) {
	                activityDto.setId(activityEntry.getId()); 
	                activityEntry.setActivityDescription(activityDto.getActivityDescription());
	                activityEntry.setActivityEndDate(activityDto.getActivityEndDate());

	                // Danışmanı güncelle
	                if (activityDto.getConsultantId() != null) {
	                    Consultant consultant = consultantRepository.findById(activityDto.getConsultantId()).orElse(null);
	                    if (consultant != null) {
	                        activityEntry.setConsultant(consultant);
	                    }
	                }

	                // Proje güncelle
	                if (activityDto.getProjectId() != null) {
	                    Project project = projectRepository.findById(activityDto.getProjectId()).orElse(null);
	                    if (project != null) {
	                        activityEntry.setProject(project);
	                    }
	                }

	                activityEntryRepository.save(activityEntry);

	                // Model'e activityEntry nesnesini "activity" olarak ekleyin
	                model.addAttribute("activity", activityEntry);
	            } else {
	                // Hata durumu veya boş activityEntry işlemleri
	                model.addAttribute("error", "Activity could not be found.");
	                return "redirect:/error";
	            }

	            return "redirect:/activities";
	        } catch (NumberFormatException e) {
	            // ID sayıya dönüştürülemediğinde
	            return "redirect:/activities";
	        }
	    }
	   
	    @GetMapping("/delete")
	    public String deleteActivity(@RequestParam int id) {
	    	ActivityEntry activityEntry = activityEntryRepository.findById(id).orElse(null);
	        if (activityEntry != null) {
	        	activityEntryRepository.delete(activityEntry);
	        }

	        return "redirect:/activities"; 
	    }
	    
}
