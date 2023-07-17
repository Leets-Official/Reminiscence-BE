package com.online.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;


@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto일 경우 자동 스퀀시 생성됨. identity가 sql임

    private Integer Post_id;
    private String Photographer;
    private String Caption;
    private String filename;
    private String filepath;
}
