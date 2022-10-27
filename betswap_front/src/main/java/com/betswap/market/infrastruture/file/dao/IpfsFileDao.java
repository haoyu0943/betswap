package com.betswap.market.infrastruture.file.dao;

import com.betswap.market.infrastruture.file.entity.IpfsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpfsFileDao extends JpaRepository<IpfsFileEntity,Long> {
    IpfsFileEntity findByFileHash(String fileHash);
    boolean existsByFileHash(String fileHash);
}
