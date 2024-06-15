package com.example.demo.services;

import com.example.demo.Repository.NhanVienRepository;
import com.example.demo.models.NhanVien;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NhanVienService {
    private final NhanVienRepository nhanVienRepository;

    public List<NhanVien> getAllNhanViens() {
        return nhanVienRepository.findAll();
    }
    public Page<NhanVien> getAllNhanViens(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    public Optional<NhanVien> getNhanVienById(String id) {
        return nhanVienRepository.findById(id);
    }

    public void addNhanVien(NhanVien nhanVien) {
        nhanVienRepository.save(nhanVien);
    }

    public void updateNhanVien(@NotNull NhanVien nhanVien) {
        NhanVien existingNhanVien = nhanVienRepository.findById(nhanVien.getMaNV())
                .orElseThrow(() -> new IllegalStateException("NhanVien with ID " +
                        nhanVien.getMaNV() + " does not exist."));
        existingNhanVien.setTenNV(nhanVien.getTenNV());
        existingNhanVien.setPhai(nhanVien.getPhai());
        existingNhanVien.setNoiSinh(nhanVien.getNoiSinh());
        existingNhanVien.setPhongBan(nhanVien.getPhongBan());
        existingNhanVien.setLuong(nhanVien.getLuong());
        nhanVienRepository.save(existingNhanVien);
    }

    public void deleteNhanVienById(String id) {
        if (!nhanVienRepository.existsById(id)) {
            throw new IllegalStateException("NhanVien with ID " + id + " does not exist.");
        }
        nhanVienRepository.deleteById(id);
    }

    public List<NhanVien> search(String keyword) {
        return getAllNhanViens().stream()
                .filter(nhanVien -> nhanVien.getTenNV().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
