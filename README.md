# ShelveMe: Automatic Shelving Plugin for JetBrains IDEs

![ShelveMe Logo](https://plugins.jetbrains.com/files/24702/581000/icon/pluginIcon.svg)

**ShelveMe** is a JetBrains IDE plugin that automatically shelves your changes at regular intervals, storing them as `.patch` files in your project's root directory. This plugin is perfect for developers who want to ensure their work is regularly backed up without manual effort.

## Features

- **Automated Shelving:** Automatically shelves all changes periodically.
- **Patch File Storage:** Saves shelved changes as `.patch` files in a folder named `ShelveMe Patch:<project name>:<branch name>` located in your project's root directory.
- **Timestamped Naming:** Patch files are named according to their creation time using the format `d:<date>-t:<time>.patch`.
- **Hands-Free Operation:** Runs seamlessly in the background, requiring no manual triggers once configured.

## Installation

1. Clone this repository or download the plugin package.
2. Open your JetBrains IDE (e.g., IntelliJ IDEA, PyCharm).
3. Go to `File > Settings > Plugins` (or `Preferences` on macOS).
4. Click on the gear icon and select `Install Plugin from Disk...`.
5. Navigate to the plugin package and select it.
6. Restart your IDE.

## Usage

Once installed, **ShelveMe** will begin shelving changes automatically at regular intervals. Patch files will be stored in the `ShelveMe Patch:<project name>:<branch name>` folder within your project's root directory.

### Example of Patch File Naming:

For a project named `MyProject` on the branch `main`:
- Folder: `ShelveMe Patch:MyProject:main`
- Patch File: `d:2024-08-28-t:15-30-00.patch`

## Contributing

We welcome contributions! Please feel free to submit a pull request or open an issue if you have any ideas or bugs to report.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/mahditavakoli1312) file for details.
