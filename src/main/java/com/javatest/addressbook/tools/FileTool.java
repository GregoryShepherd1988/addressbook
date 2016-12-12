package com.javatest.addressbook.tools;

import com.javatest.addressbook.App;
import com.javatest.addressbook.exceptions.FileReadException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class FileTool {

    public List<String> loadFileFromResources(String fileName) {
        List<String> result = new ArrayList<>();
        ClassLoader classLoader = App.class.getClassLoader();
        try {
            File file = new File(classLoader.getResource(fileName).getFile());

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
            scanner.close();

        } catch (Exception e) {
            throw new FileReadException(e);
        }
        return result;
    }
}
