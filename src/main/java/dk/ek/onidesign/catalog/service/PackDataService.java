package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataMapper;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PackDataService {

    private final PackDataRepository packDataRepository;

    public PackDataService(PackDataRepository packDataRepository) {
        this.packDataRepository = packDataRepository;
    }

}
