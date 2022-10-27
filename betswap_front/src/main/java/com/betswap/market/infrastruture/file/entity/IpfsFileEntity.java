package com.betswap.market.infrastruture.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ipfs_file")
public class IpfsFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fileHash;
    private String fileName;
    private String fileSuffix;
    private String fileType;
    private String uploadUserId;
    private Timestamp uploadTime;
}
