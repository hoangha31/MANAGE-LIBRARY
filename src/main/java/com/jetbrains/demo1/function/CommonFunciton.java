package com.jetbrains.demo1.function;

import com.jetbrains.demo1.dao.DocumentDao;
import com.jetbrains.demo1.dao.UserDao;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CommonFunciton {
    public static Image insertImage(String style) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String projectPath = System.getProperty("user.dir");
            File userFolder = new File(projectPath, "src/main/resources/image/" + style);
            File destinationFile = new File(userFolder, file.getName());
            String fileName = file.getName();

            int counter = 1;
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf("."));

            while (destinationFile.exists()) {
                String newFileName = baseName + counter + extension;
                destinationFile = new File(userFolder, newFileName);
                counter++;
            }

            try {
                DocumentDao.urlImageCurrent = "src/main/resources/image/" + style + "/" + destinationFile.getName();
                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return new Image("file:" + destinationFile, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

