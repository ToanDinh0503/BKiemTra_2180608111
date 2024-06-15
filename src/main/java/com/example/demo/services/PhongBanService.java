package com.example.demo.services;

import com.example.demo.Repository.PhongBanRepository;
import com.example.demo.models.PhongBan;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhongBanService {
    private final PhongBanRepository phongBanRepository;

    /**
     * Retrieve all phongBan from the database.
     * @return a list of phongBan
     */
    public List<PhongBan> getAllPhongBans() {
        return phongBanRepository.findAll();
    }

    /**
     * Retrieve a phongBan by its id.
     * @param id the id of the phongBan to retrieve
     * @return an Optional containing the found phongBan or empty if not found
     */
    public Optional<PhongBan> getPhongBanById(String id) {
        return phongBanRepository.findById(id);
    }

    /**
     * Add a new phongBan to the database.
     * @param phongBan the phongBan to add
     */
    public void addPhongBan(PhongBan phongBan) {
        phongBanRepository.save(phongBan);
    }

    /**
     * Update an existing phongBan.
     * @param phongBan the phongBan with updated information
     */
    public void updatePhongBan(@NotNull PhongBan phongBan) {
        PhongBan existingPhongBan = phongBanRepository.findById(phongBan.getMaPhong())
                .orElseThrow(() -> new IllegalStateException("PhongBan with ID " +
                        phongBan.getMaPhong() + " does not exist."));
        existingPhongBan.setTenPhong(phongBan.getTenPhong());
        phongBanRepository.save(existingPhongBan);
    }

    /**
     * Delete a phongBan by its id.
     * @param id the id of the phongBan to delete
     */
    public void deletePhongBanById(String id) {
        if (!phongBanRepository.existsById(id)) {
            throw new IllegalStateException("PhongBan with ID " + id + " does not exist.");
        }
        phongBanRepository.deleteById(id);
    }
}
