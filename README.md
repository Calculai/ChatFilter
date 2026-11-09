# Chat Filter

A client-side Fabric mod for Minecraft: Java Edition 1.21.11. Hides incoming messages containing configured words or phrases.

## Build

Requires Java JDK 21 (64-bit) on Windows.

1. Download [Eclipse Temurin JDK 21 for Windows x64](https://adoptium.net/temurin/releases/?os=windows&arch=x64&package=jdk&version=21).
2. Select JDK and download the `.MSI` installer.
3. Run the installer. Enable **Add to PATH** and **Update JAVA_HOME** if those options are available.
4. Open a new terminal and verify both `java -version` and `javac -version` report version 21.
5. Run `build.bat` from the repository root.

The compiled mod will be placed in `output\chatfilter-1.0.0.jar`.

## Download

You can [download the mod JAR directly](output/chatfilter-1.0.0.jar) and place it in your Minecraft `mods` folder. 

## Install

Use Minecraft: Java Edition 1.21.11 with Fabric Loader and Fabric API installed. Copy `output\chatfilter-1.0.0.jar` into your Minecraft `mods` folder.

## Configuration

Launch Minecraft once to generate `.minecraft/config/chatfilter.txt`.

Add one blocked word or phrase per line. Matching ignores capitalization and blocks a message if it contains any listed entry. Lines beginning with `#` are ignored.

Restart Minecraft after editing the file. Filtering only affects your client; it does not stop the server from sending messages.
