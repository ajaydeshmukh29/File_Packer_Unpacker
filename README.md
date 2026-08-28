# 🔐 File Packer-Unpacker

A **Java Swing based desktop application** for securely packing multiple files into a single `.pak` file and unpacking them when required.

The application provides a modern graphical interface with **file/folder selection, progress tracking, password protection, encryption, error handling, and status updates**.

---

## 👨‍💻 Author

**Ajay Deshmukh**

GitHub:  
https://github.com/ajaydeshmukh29

---

## 📌 Project Overview

File Packer-Unpacker is a Java-based desktop application that allows users to:

- 📦 Pack multiple files into a single `.pak` file.
- 📂 Unpack files from a `.pak` file.
- 🔐 Protect packed files using a password.
- 🔒 Encrypt file contents using password-based encryption.
- 📊 Monitor packing and unpacking progress.
- 🖥️ Perform all operations through a graphical interface.

The project is designed to demonstrate practical concepts of **Java, File Handling, Swing GUI, Encryption, Exception Handling, and Multithreading**.

---

# 🚀 Features

## 📦 File Packing

- Select a source folder.
- Automatically detect files inside the folder.
- Select the destination `.pak` file.
- Store multiple files inside one packed file.
- Display number of files.
- Display total file size.
- Show packing progress.
- Handle existing packed files safely.

---

## 📂 File Unpacking

- Select an existing `.pak` file.
- Select the destination folder.
- Extract all stored files.
- Recreate the original files.
- Display unpacking progress.
- Handle invalid or corrupted packed files.

---

<!-- ## 🔐 Password Protection

The application supports password-protected packed files.

During packing:

```text
Source Folder
      ↓
Enter Password
      ↓
Generate Encryption Key
      ↓
Encrypt File Data
      ↓
Create .pak File
