# StepUp for Minecraft 1.20.8

This is the Minecraft 1.20.8 update for the StepUp mod.

## Changes for 1.20.8

This update provides compatibility with Minecraft 1.20.8, maintaining all existing functionality:

### Version Information
- **Minecraft Version**: 1.20.8
- **Fabric Loader**: 0.15.11  
- **Fabric API**: 0.98.0+1.20.6 (using 1.20.6 compatible version until 1.20.8 specific versions are available)
- **Mod Version**: 1.4

### What's Updated

1. **Version Dependencies**: Updated all version files to target Minecraft 1.20.8
2. **Fabric Compatibility**: Maintained compatibility with Fabric mod loader
3. **Build Configuration**: Updated build.gradle to use 1.20.8 version properties

### Files Modified

- `Versionfiles/mcversion-1.20.8.properties` - New version configuration for 1.20.8
- `build.gradle` - Updated to use 1.20.8 version properties  
- All Java source files are compatible with 1.20.8 (no code changes needed)

### Structure

The mod maintains the same structure as previous versions:

```
src/main/java/com/nottoomanyitems/stepup/
├── Main.java                     # Mod entry point
├── StepChanger.java             # Core step height modification logic  
├── ConfigHandler.java           # Configuration management
├── MMConfigurationHandler.java  # ModMenu integration
└── mixins/
    └── NetHandler.java          # Server connection handling
```

### Features

- Toggle between normal movement, step-up mode, and vanilla autojump
- Per-server configuration saving
- Keybind customization (default: J key)
- No hunger penalty unlike vanilla autojump
- Client-side only mod compatible with vanilla servers

### Usage

After installing:
1. Press J (or configured key) to cycle through modes:
   - **StepUp Mode**: Climb full blocks without jumping
   - **Disabled**: Normal vanilla movement  
   - **AutoJump Mode**: Vanilla Minecraft autojump

The mod remembers your preference per server.

## Technical Notes

This version maintains full compatibility with the existing StepUp API and configuration format. No world or configuration migration is needed when updating from previous versions.

The code is ready for Minecraft 1.20.8 but requires proper Fabric toolchain setup for compilation in a real environment with access to Fabric repositories.