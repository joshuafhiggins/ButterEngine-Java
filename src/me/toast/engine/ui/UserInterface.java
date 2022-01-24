package me.toast.engine.ui;

import com.labymedia.ultralight.*;
import com.labymedia.ultralight.gpu.*;
import com.labymedia.ultralight.os.OperatingSystem;
import me.toast.engine.Main;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class UserInterface {

    public UserInterface() {
        try {
            extractResources();
            Path ultralightNativesDir = Paths.get("./bin");
            
            UltralightJava.extractNativeLibrary(ultralightNativesDir);
            UltralightGPUDriverNativeUtil.extractNativeLibrary(ultralightNativesDir);

            OperatingSystem operatingSystem = OperatingSystem.get();
            List<String> libs = List.of(
                    "glib-2.0-0",
                    "gobject-2.0-0",
                    "gmodule-2.0-0",
                    "gio-2.0-0",
                    "gstreamer-full-1.0",
                    "gthread-2.0-0"
            );

            for (String lib : libs) {
                System.load(ultralightNativesDir.resolve(operatingSystem.mapLibraryName(lib)).toAbsolutePath().toString());
            }

            UltralightJava.load(ultralightNativesDir);
            UltralightGPUDriverNativeUtil.load(ultralightNativesDir);

        } catch (UnsatisfiedLinkError | UltralightLoadException e) {
            throw new IllegalStateException("Unable to load Ultralight natives!", e);
        }
    }

    public void Update() {

    }

    public void Render() {

    }

    public void Cleanup() {

    }

    /**
     * Helper function to set up the run directory with jar resources.
     * TEMPORARY!
     */
    public static void extractResources() {
        try {
            Files.copy(
                    Main.class.getResourceAsStream("/example.html"),
                    Paths.get("./example.html"),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Files.copy(
                    Main.class.getResourceAsStream("/example.js"),
                    Paths.get("./example.js"),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Files.copy(
                    Main.class.getResourceAsStream("/style.css"),
                    Paths.get("./style.css"),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
