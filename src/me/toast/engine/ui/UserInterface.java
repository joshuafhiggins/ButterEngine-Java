package me.toast.engine.ui;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;

public class UserInterface extends Application {

    public static Scene FrameToCapture;

    private WritableImage webImage;
    private WebView webView;

    public int getWidth() {
        return (int)webView.getWidth();
    }

    public int getHeight() {
        return (int)webView.getHeight();
    }

    public void process(final int width, final int height, final ByteBuffer buffer, final int stride, final Semaphore signal) {
        // This method runs in the background rendering thread
        Platform.runLater(() -> {
            if ( webImage == null || webImage.getWidth() != width || webImage.getHeight() != height )
                webImage = new WritableImage(width, height);

            webView.snapshot(snapshotResult -> {
                snapshotResult.getImage().getPixelReader().getPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, stride);

                signal.release();
                return null;

            }, new SnapshotParameters(), webImage);
        });
    }

    public UserInterface() {
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Webview");
        stage.setResizable(false);

        stage.setHeight(720);
        stage.setWidth(1280);

        webView = new WebView();
        webView.setPrefSize(1280, 720);
        webView.setMaxSize(1280, 720);
        webView.setMinSize(1280, 720);

        webView.getEngine().load("https://higgy999.github.io/");

        VBox vBox = new VBox(webView);

        FrameToCapture = new Scene(vBox, 1280, 720);
        stage.setScene(FrameToCapture);
        stage.sizeToScene();
        stage.show();
    }
}
