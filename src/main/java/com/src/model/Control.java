package com.src.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Control {
	@XmlElement(name = "transactionId")
	private String transactionId;
	@XmlElement(name = "transactionType")
	private String transactionType;
	
}
