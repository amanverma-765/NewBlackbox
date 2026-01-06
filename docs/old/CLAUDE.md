# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

BlackBox is an Android virtual engine that enables cloning, running, and managing virtual applications without installing APK files. It creates sandboxed environments for running apps with features like Xposed module support, GPS spoofing, and multi-user isolation.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build only the Bcore library module
./gradlew :Bcore:assembleRelease

# Build only the app module
./gradlew :app:assembleDebug

# Clean build
./gradlew clean

# Build with specific ABI (arm64-v8a or armeabi-v7a)
./gradlew assembleDebug -Pabi=arm64-v8a
```

## Project Architecture

### Module Structure

- **app/** - User-facing Android application (Kotlin)
  - UI activities, fragments, and view models
  - Package: `top.niunaijun.blackboxa`

- **Bcore/** - Core virtual engine library (Java)
  - Package: `top.niunaijun.blackbox`
  - Main entry point: `BlackBoxCore.java`

### Bcore Core Components

```
Bcore/src/main/java/top/niunaijun/blackbox/
├── BlackBoxCore.java          # Main API entry point
├── app/
│   ├── BActivityThread.java   # Virtual activity thread management
│   └── LauncherActivity.java  # Virtual app launcher
├── core/
│   ├── system/                # Virtual system services
│   │   ├── BlackBoxSystem.java
│   │   ├── BProcessManagerService.java
│   │   ├── pm/                # Package management
│   │   ├── am/                # Activity management
│   │   └── accounts/          # Account management
│   ├── NativeCore.java        # JNI bridge to native layer
│   ├── IOCore.java            # I/O redirection
│   ├── GmsCore.java           # Google Play Services handling
│   └── JarManager.java        # JAR file management with caching
├── proxy/                     # Android component proxies
│   ├── ProxyActivity.java
│   ├── ProxyService.java
│   ├── ProxyContentProvider.java
│   └── ProxyJobService.java
├── fake/                      # Fake/mock implementations
├── entity/                    # Data models
└── utils/                     # Utility classes
```

### Native Layer (C++)

```
Bcore/src/main/cpp/
├── BoxCore.cpp               # Main native entry point
├── Hook/                     # Function hooking
│   ├── FileSystemHook.cpp    # File system interception
│   ├── DexFileHook.cpp       # DEX loading hooks
│   └── BinderHook.cpp        # Binder IPC hooks
├── Utils/
│   ├── AntiDetection.cpp     # Anti-detection mechanisms
│   └── VirtualSpoof.cpp      # Device spoofing
├── Dobby/                    # ARM/ARM64 hooking library (prebuilt)
└── xdl/                      # Dynamic linker utilities
```

### Key Libraries

- **Dobby** - Native function hooking (ARM/ARM64/x86)
- **xDL** - Android dynamic linker utilities
- **BlackReflection** - Enhanced Java reflection for hidden APIs
- **FreeReflection** - Hidden API bypass

## SDK Configuration

- Compile SDK: 35
- Target SDK: 34
- Min SDK: 21 (but 24 in app module)
- NDK Version: 29.0.13846066
- Java/Kotlin: 17

## Architecture Support

Supported ABIs: `arm64-v8a`, `armeabi-v7a`

The native library `libblackbox.so` is built separately for each architecture.

## AIDL Interfaces

Located in `Bcore/src/main/aidl/` - defines IPC interfaces for:
- Package management
- Account management
- Activity/Service management
- Job scheduling

## Key Classes for Virtual App Lifecycle

1. `BlackBoxCore.get()` - Main API for installing/launching virtual apps
2. `BActivityThread` - Manages virtual app's main thread
3. `BProcessManagerService` - Virtual process management
4. `ProxyActivity/ProxyService` - Stub components for virtual apps

## ProGuard Configuration

Both modules use ProGuard with rules to keep:
- All classes in `top.niunaijun.blackbox.**`
- BlackReflection annotations and annotated classes
- Mirror classes and Android framework classes
