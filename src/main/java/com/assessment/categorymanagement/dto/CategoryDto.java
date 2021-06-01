package com.assessment.categorymanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @ApiModelProperty("Category unique id.")
    private Integer categoryId;
    @ApiModelProperty("Category name.")
    @NotEmpty
    private String name;
    @ApiModelProperty("Id of the parent category.")
    @Min(1)
    private Integer parentId;
    @ApiModelProperty("Child categories.")
    private List<CategoryDto> children;
}
