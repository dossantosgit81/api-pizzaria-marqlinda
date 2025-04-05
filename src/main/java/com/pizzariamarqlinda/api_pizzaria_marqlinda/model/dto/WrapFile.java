package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrapFile {
    private String fileOriginalName;
    private String imgUrl;

    public WrapFile(String fileOriginalName, String imgUrl){
        this.fileOriginalName = fileOriginalName;
        this.imgUrl = imgUrl;
    }
}
