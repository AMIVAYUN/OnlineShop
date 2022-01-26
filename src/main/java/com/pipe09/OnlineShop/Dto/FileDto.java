package com.pipe09.OnlineShop.Dto;

import lombok.Data;

@Data
public class FileDto {
    private String fileName;
    private String contentType;

    public FileDto(String fileName, String contentType) {
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
