package com.src.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RenderClaimCorrespondence")
public class CIM {
	@XmlElement(name = "Control")
	private Control control;
	@XmlElement(name = "Body")
	private Body body;
}
