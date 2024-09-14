package org.example.demoapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositTypeDTO  implements Serializable {
    private Long id;
    private String title;
    private String withdrawalTools;
    private Integer code;

    public DepositTypeDTO(Integer code, String title) {
        this.code = code;
        this.title = title;
    }
}
