package com.example.demo.Controller;

import com.example.demo.models.NhanVien;
import com.example.demo.services.NhanVienService;
import com.example.demo.services.PhongBanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/nhanviens")
public class NhanVienController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private PhongBanService phongBanService;

    @GetMapping
    public String listNhanViens(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NhanVien> nhanVienPage = nhanVienService.getAllNhanViens(pageable);
        model.addAttribute("nhanVienPage", nhanVienPage);
        return "/nhanviens/nhanvien-list";
    }

    @GetMapping("/add")
    public String addNhanVien(Model model) {
        model.addAttribute("nhanvien", new NhanVien());
        model.addAttribute("phongbans", phongBanService.getAllPhongBans());
        return "/nhanviens/add-nhanvien";
    }

    @PostMapping("/add")
    public String addNhanVien(@Valid NhanVien nhanVien, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("nhanvien", nhanVien);
            model.addAttribute("phongbans", phongBanService.getAllPhongBans());
            return "/nhanviens/add-nhanvien";
        }
        nhanVienService.addNhanVien(nhanVien);
        return "redirect:/nhanviens";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        NhanVien nhanVien = nhanVienService.getNhanVienById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid nhanvien Id:" + id));
        model.addAttribute("nhanvien", nhanVien);
        model.addAttribute("phongbans", phongBanService.getAllPhongBans());
        return "/nhanviens/update-nhanvien";
    }

    @PostMapping("/edit/{id}")
    public String updateNhanVien(@PathVariable String id, @Valid NhanVien nhanVien, BindingResult result, Model model) {
        if (result.hasErrors()) {
            nhanVien.setMaNV(id);
            model.addAttribute("phongbans", phongBanService.getAllPhongBans());
            return "/nhanviens/update-nhanvien";
        }
        nhanVienService.updateNhanVien(nhanVien);
        return "redirect:/nhanviens";
    }

    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable String id) {
        nhanVienService.deleteNhanVienById(id);
        return "redirect:/nhanviens";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("nhanviens", nhanVienService.search(keyword));
        return "nhanviens/nhanvien-list";
    }
}
