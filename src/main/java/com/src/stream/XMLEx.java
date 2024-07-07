package com.src.stream;

import com.src.model.Body;
import com.src.model.CIM;
import com.src.model.Control;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLEx {

    public static void main(String[] args) throws JAXBException, IOException {

        //Read xml
        Path path = Paths.get("src/main/resources/rcc.xml");
        Unmarshaller unmarshaller = JAXBContext.newInstance(CIM.class, Body.class, Control.class).createUnmarshaller();
        String payload = Files.readString(path);
        StringReader stringReader = new StringReader(payload);
        CIM cim = (CIM) unmarshaller.unmarshal(stringReader);
        System.out.println(cim.getBody().getAction());

        //Write xml
        CIM cim1 =new CIM();
        Body body1 = new Body();
        Control control1 = new Control();
        control1.setTransactionId("243232");
        control1.setTransactionType("play");
        body1.setAction("Running");
        cim1.setControl(control1);
        cim1.setBody(body1);
        Marshaller marshaller = JAXBContext.newInstance(CIM.class, Body.class, Control.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(cim1, new File("src/main/resources/rccOutput.xml"));
        System.out.println("xml created");


    }
}
