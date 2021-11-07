package me.toast.engine.ui;

import com.labymedia.ultralight.*;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;
import com.labymedia.ultralight.os.Architecture;
import com.labymedia.ultralight.os.OperatingSystem;

import java.io.File;
import java.nio.file.*;

public class UserInterface {

    public UserInterface() {
        // Get a directory to put natives into
        Path nativesDir = Paths.get("bin/");

        //Windows PATH in Java can't be changed at runtime. Needs to be done ahead of time
        //TODO: Test this in linux if the new binaries work
        if (OperatingSystem.get() != OperatingSystem.WINDOWS) {
            // Get the existing native library path
            String libraryPath = System.getProperty("java.library.path");
            if (libraryPath != null) {
                // There is a path set already, append our natives dir
                libraryPath += File.pathSeparator + nativesDir.toAbsolutePath();
            } else {
                // There is no path set, make our natives dir the current path
                libraryPath = nativesDir.toAbsolutePath().toString();
            }
            System.setProperty("java.library.path", libraryPath);
        }

        try {
            UltralightGPUDriverNativeUtil.extractNativeLibrary(nativesDir);
            UltralightJava.extractNativeLibrary(nativesDir);

            UltralightGPUDriverNativeUtil.load(nativesDir);
            UltralightJava.load(nativesDir);
        } catch (UltralightLoadException e) {
            e.printStackTrace();
        }

    }
}
