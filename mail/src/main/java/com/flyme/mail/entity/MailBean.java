package com.flyme.mail.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class MailBean implements Serializable {
    private  String [] to;
    private  List<String> cc;
    private  String from;
    private  String subject;
    private  String content;
    private  List<String> attachmentPath;
    private  String imagePath;
    private  String rscId;
}

