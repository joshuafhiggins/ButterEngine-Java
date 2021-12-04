package me.toast.engine.ui;

import com.labymedia.ultralight.*;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;

import java.io.File;
import java.nio.file.*;

public class UserInterface {

    public UserInterface() {
        // Get a directory to put natives into
        Path nativesDir = Paths.get("bin/");
		
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

        try {
			UltralightJava.extractNativeLibrary(nativesDir);
            UltralightGPUDriverNativeUtil.extractNativeLibrary(nativesDir);

            UltralightJava.load(nativesDir);
            UltralightGPUDriverNativeUtil.load(nativesDir);
        } catch (UltralightLoadException e) {
            e.printStackTrace();
        }

    }
}
