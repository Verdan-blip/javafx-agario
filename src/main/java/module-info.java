module ru.kpfu.itis.bagaviev.agario {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens ru.kpfu.itis.bagaviev.agario.client.fx.app to javafx.fxml;
    exports ru.kpfu.itis.bagaviev.agario.server.app;
    exports ru.kpfu.itis.bagaviev.agario.client.fx.app;
    opens ru.kpfu.itis.bagaviev.agario.client.fx.controllers;
}