package org.example.demoapplication.service;

import lombok.NoArgsConstructor;
import org.example.demoapplication.dto.DepositTypeDTO;
import org.example.demoapplication.entity.DepositType;
import org.example.demoapplication.repository.DepositTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@NoArgsConstructor
public class DepositTypeService {

    ModelMapper modelMapper = new ModelMapper();

    private DepositTypeRepository depositTypeRepository;

    @Autowired
    public DepositTypeService(DepositTypeRepository depositTypeRepository) {
        this.depositTypeRepository = depositTypeRepository;
    }

    public DepositTypeDTO createDepositType(DepositTypeDTO depositTypeDTO) {
        depositTypeDTO.setCode(generateUniqueCode());
        depositTypeRepository.save(modelMapper.map(depositTypeDTO, DepositType.class));
        return depositTypeDTO;
    }

    public Optional<DepositTypeDTO> getDepositTypeByCode(Integer code) {
        return depositTypeRepository.findByCode(code)
                .map(depositType -> modelMapper.map(depositType, DepositTypeDTO.class));
    }


    private int generateUniqueCode() {
        Random random = new Random();
        int code = random.nextInt(900) + 100; // Generates a code between 100 and 999
        while (depositTypeRepository.existsByCode(code)) {
            code = random.nextInt(900) + 100; // Ensure the code is unique
        }
        return code;
    }
}

