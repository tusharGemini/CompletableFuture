package com.tushar.completablefuture.model;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeProjectMapping {
    private Integer id;
    private Integer empId;
    private Integer projectId;
    private Boolean isActive;
    private Timestamp lastModifiedOn;
    private Integer lastModifiedBy;
}
