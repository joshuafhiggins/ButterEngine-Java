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

        System.out.println(libraryPath);

        // Set the path back
        //System.setProperty("java.library.path", libraryPath);
        System.setProperty("java.library.path", nativesDir.toAbsolutePath().toString());

        System.out.println(System.getProperty("java.library.path"));

        /*System.loadLibrary("./bin/AppCore");
        System.loadLibrary("./bin/UltralightCore");
        System.loadLibrary("./bin/Ultralight");
        System.loadLibrary("./bin/WebCore");*/

        System.loadLibrary("AppCore");
        System.loadLibrary("UltralightCore");
        System.loadLibrary("Ultralight");
        System.loadLibrary("WebCore");


        // Extract the natives
        //
        // This only extracts the native library for ultralight-java-base, but not the other Ultralight libraries.
        // It is your task to get them into the run directory, possibly by extracting them on your own.
        try {
            UltralightGPUDriverNativeUtil.extractNativeLibrary(nativesDir);
            UltralightJava.extractNativeLibrary(nativesDir);
        } catch (UltralightLoadException e) {
            e.printStackTrace();
        }

        // Load the native libraries from the given directory. This method makes sure everything is loaded in the
        // correct order. If you want to manually load all natives, either don't use this function or pass 'false' as
        // the second parameter.
        try {
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
