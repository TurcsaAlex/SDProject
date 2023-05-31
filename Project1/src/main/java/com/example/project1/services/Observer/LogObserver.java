package com.example.project1.services.Observer;

import com.example.project1.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
@AllArgsConstructor
@Getter
@Setter
public class LogObserver implements Observer{
    private static Integer logNr=0;
    @Override
    public void notify(Object o) {
        Invoice invoice=(Invoice) o;
        try {
            FileWriter myWriter = new FileWriter("log"+logNr+".txt");
            logNr++;
            myWriter.write(invoice.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file."+logNr);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
