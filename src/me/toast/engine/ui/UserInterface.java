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

        // Get the existing native library path
        String libraryPath = System.getProperty("java.library.path");
        if (libraryPath != null) {
            // There is a path set already, append our natives dir
            libraryPath += File.pathSeparator + nativesDir.toAbsolutePath();
        } else {
            // There is no path set, make our natives dir the current path
            libraryPath = nativesDir.toAbsolutePath().toString();
        }

        System.out.println("libraryPath: "+libraryPath);

        System.setProperty("java.library.path", libraryPath);

        System.out.println("java.library.path: "+System.getProperty("java.library.path"));
        System.out.println("nativesDir: "+nativesDir.toAbsolutePath().toString());

        try {
            UltralightGPUDriverNativeUtil.extractNativeLibrary(nativesDir);
            UltralightJava.extractNativeLibrary(nativesDir);

            UltralightGPUDriverNativeUtil.load(nativesDir);
            UltralightJava.load(nativesDir);
        } catch (UltralightLoadException e) {
            e.printStackTrace();
        }

    }

    private static Path determineLibraryPath(
            Path nativesDir,
            String libraryName,
            OperatingSystem operatingSystem,
            Architecture architecture
    ) {
        // First try to find the library from ${nativesDir}/${prefix}${name}-${bits}${suffix}
        Path pathWithArchitecture = nativesDir.resolve(
                operatingSystem.mapLibraryName(libraryName + "-" + architecture.getBits()));
        if (Files.isRegularFile(pathWithArchitecture)) {
            // Found it
            return pathWithArchitecture;
        }

        // Then try to find the library from ${nativesDir}/${prefix}${name}${suffix}
        Path pathWithoutArchitecture = nativesDir.resolve(operatingSystem.mapLibraryName(libraryName));
        if (Files.isRegularFile(pathWithoutArchitecture)) {
            return pathWithoutArchitecture;
        }

        throw new RuntimeException("Failed to find library " + libraryName);
    }
}
