package com.book.bookcore.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TagCreateDTO {

    @NotBlank(message = "标签名称不能为空")
    private String name;

    private String color = "#409EFF";
}