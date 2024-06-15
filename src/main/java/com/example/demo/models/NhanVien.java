package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NHANVIEN")
public class NhanVien {
    @Id
    @Column(name = "MA_NV", length = 3, nullable = false)
    private String maNV;

    @Column(name = "TEN_NV", length = 100, nullable = false)
    private String tenNV;

    @Column(name = "PHAI", length = 3)
    private String phai;

    @Column(name = "NOI_SINH", length = 200)
    private String noiSinh;

    @ManyToOne
    @JoinColumn(name = "Ma_Phong")
    private PhongBan phongBan;

    @Column(name = "LUONG")
    private int luong;

    // Constructors, getters, setters
}
