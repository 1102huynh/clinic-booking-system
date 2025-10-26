package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.dto.DoctorResponse;
import huynh.tdt.clinicbookingsystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/search")
    public String searchPage(@RequestParam(value = "q", required = false) String q,
                             @RequestParam(value = "specialization", required = false) String specialization,
                             Model model) {
        List<DoctorResponse> doctors;
        if ((q == null || q.isEmpty()) && (specialization == null || specialization.isEmpty())) {
            doctors = doctorService.getAllAvailableDoctors();
        } else if (specialization != null && !specialization.isEmpty()) {
            doctors = doctorService.getDoctorsBySpecialization(specialization);
        } else {
            // fallback to all available for now
            doctors = doctorService.getAllAvailableDoctors();
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("q", q);
        model.addAttribute("specialization", specialization);
        model.addAttribute("specializations", doctorService.getAllSpecializations());
        return "doctor_search";
    }
}

