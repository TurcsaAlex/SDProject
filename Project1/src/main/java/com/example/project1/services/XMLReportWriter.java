package com.example.project1.services;

import com.example.project1.model.Invoice;
import com.example.project1.model.Report;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class XMLReportWriter {
    private static Integer xmlNr =0;
    public void writeXml(List<Report> reports) {
        System.out.println(reports);
        try {
            FileWriter myWriter = new FileWriter("xml"+ xmlNr +".xml");
            xmlNr++;
            StringBuilder resultString= new StringBuilder("id,name,description,price,stock,sold\n");
            for (Report r : reports) {
                resultString.append(r.toXML());
            }
            myWriter.write(convertToXML(resultString.toString()));
            myWriter.close();
            System.out.println("Successfully wrote to the file."+ xmlNr);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private String convertToXML(String csvString) {
        String[] lines = csvString.split("\n");
        String[] headers = lines[0].split(",");

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<data>\n");

        for (int i = 1; i < lines.length; i++) {
            String[] values = lines[i].split(",");
            xmlBuilder.append("  <record>\n");

            for (int j = 0; j < headers.length; j++) {
                String header = headers[j];
                String value = values[j];
                xmlBuilder.append("    <").append(header.trim()).append(">")
                        .append(value.trim()).append("</").append(header.trim()).append(">\n");
            }

            xmlBuilder.append("  </record>\n");
        }

        xmlBuilder.append("</data>");
        return xmlBuilder.toString();
    }
}
