package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public abstract class Frame<T> {

    protected final Parent root;
    protected final T controller;

    protected Frame(Path path) {
        try {
            URL urlToXmlResource = path.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(urlToXmlResource);
            root = loader.load();
            controller = loader.getController();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Parent getRoot() {
        return root;
    }

}
