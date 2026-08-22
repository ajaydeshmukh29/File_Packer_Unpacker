////////////////////////////////////////////////////////////////////
//
// Project     : Java File Packer / Unpacker (GUI)
// 
// Description : Packs all files from a folder into a single
//               archive file, and unpacks that archive back
//               into the original files, via a Swing GUI.
//
// Author      : Ajay Dnyaneshwar Deshmukh
// GitHub      : ajaydeshmukh29
// Date        : 20-Aug-2026
// Can Add feature    : Encrption for data security
////////////////////////////////////////////////////////////////////

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

class FilePackUnpack implements Runnable
{
    Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
    Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
    Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);

    Color bgColor = new Color(245, 247, 250);
    Color accentColor = new Color(60, 110, 220);
    Color statusOkColor = new Color(30, 140, 70);
    Color statusErrColor = new Color(190, 40, 40);

    public void run()
    {
        JFrame frame = new JFrame("Java File Packer / Unpacker");
        frame.setSize(360, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(bgColor);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(buttonFont);
        tabs.addTab("Pack", buildPackPanel());
        tabs.addTab("Unpack", buildUnpackPanel());

        frame.add(tabs);
        frame.setVisible(true);
    }

    private JPanel buildPackPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Pack a Folder");
        title.setFont(titleFont);
        title.setForeground(accentColor);
        title.setBounds(20, 20, 250, 30);

        JLabel folderLabel = new JLabel("Folder Name:");
        folderLabel.setFont(labelFont);
        folderLabel.setBounds(20, 70, 150, 25);

        JTextField folderText = new JTextField();
        folderText.setBounds(20, 95, 290, 32);
        folderText.setBorder(new CompoundBorder(new LineBorder(new Color(200, 205, 215), 1, true),
                new EmptyBorder(4, 8, 4, 8)));

        JLabel packfileLabel = new JLabel("Pack File Name:");
        packfileLabel.setFont(labelFont);
        packfileLabel.setBounds(20, 140, 150, 25);

        JTextField packfileText = new JTextField();
        packfileText.setBounds(20, 165, 290, 32);
        packfileText.setBorder(new CompoundBorder(new LineBorder(new Color(200, 205, 215), 1, true),
                new EmptyBorder(4, 8, 4, 8)));

        JButton packButton = new JButton("Pack");
        packButton.setFont(buttonFont);
        packButton.setBackground(accentColor);
        packButton.setForeground(Color.WHITE);
        packButton.setFocusPainted(false);
        packButton.setBounds(20, 215, 290, 38);

        JLabel statusLabel = new JLabel("");
        statusLabel.setFont(labelFont);
        statusLabel.setBounds(20, 270, 290, 60);

        packButton.addActionListener(e ->
        {
            String folderName = folderText.getText();
            String packFileName = packfileText.getText();

            try
            {
                filePacker(folderName, packFileName);
                statusLabel.setForeground(statusOkColor);
                statusLabel.setText("Packed successfully!");
            }
            catch(Exception iobj)
            {
                statusLabel.setForeground(statusErrColor);
                statusLabel.setText("Error: " + iobj.getMessage());
                System.out.println(iobj);
            }
        });

        panel.add(title);
        panel.add(folderLabel);
        panel.add(folderText);
        panel.add(packfileLabel);
        panel.add(packfileText);
        panel.add(packButton);
        panel.add(statusLabel);

        return panel;
    }

    private JPanel buildUnpackPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Unpack a File");
        title.setFont(titleFont);
        title.setForeground(accentColor);
        title.setBounds(20, 20, 250, 30);

        JLabel packfileLabel = new JLabel("Pack File Name:");
        packfileLabel.setFont(labelFont);
        packfileLabel.setBounds(20, 90, 150, 25);

        JTextField packfileText = new JTextField();
        packfileText.setBounds(20, 115, 290, 32);
        packfileText.setBorder(new CompoundBorder(new LineBorder(new Color(200, 205, 215), 1, true),
                new EmptyBorder(4, 8, 4, 8)));

        JButton unpackButton = new JButton("Unpack");
        unpackButton.setFont(buttonFont);
        unpackButton.setBackground(accentColor);
        unpackButton.setForeground(Color.WHITE);
        unpackButton.setFocusPainted(false);
        unpackButton.setBounds(20, 165, 290, 38);

        JLabel statusLabel = new JLabel("");
        statusLabel.setFont(labelFont);
        statusLabel.setBounds(20, 220, 290, 60);

        unpackButton.addActionListener(e ->
        {
            String packFileName = packfileText.getText();

            try
            {
                fileUnpacker(packFileName);
                statusLabel.setForeground(statusOkColor);
                statusLabel.setText("Unpacked successfully!");
            }
            catch(Exception iobj)
            {
                statusLabel.setForeground(statusErrColor);
                statusLabel.setText("Error: " + iobj.getMessage());
                System.out.println(iobj);
            }
        });

        panel.add(title);
        panel.add(packfileLabel);
        panel.add(packfileText);
        panel.add(unpackButton);
        panel.add(statusLabel);

        return panel;
    }

    // ------------------------------ Packing logic ---------------------------------

    public void filePacker(String folderName, String packFileName) throws Exception
    {
        int iRet = 0;
        int Size = 0;
        String header = "";

        FileOutputStream foobj = null;
        FileInputStream fiobj = null;

        byte Buffer[] = new byte[1024];
        byte bHeader[] = null;

        File fobjfolder = new File(folderName);

        if((fobjfolder.exists()) && (fobjfolder.isDirectory()))
        {
            System.out.println("Folder exists");

            File fobjpack = new File(packFileName);
            fobjpack.createNewFile();   // Pack file gets created

            foobj = new FileOutputStream(fobjpack);

            File fArr[] = fobjfolder.listFiles();

            System.out.println("Number of files in folder : " + fArr.length);

            for(int i = 0; i < fArr.length; i++)
            {
                fiobj = new FileInputStream(fArr[i]);

                header = header + fArr[i].getName();
                header = header + " ";
                header = header + fArr[i].length();

                Size = 100 - header.length();

                for(int j = 1; j <= Size; j++)
                {
                    header = header + " ";
                }

                bHeader = header.getBytes();

                // Write file name and size

                foobj.write(bHeader);

                // Loop to read from fiobj & write to foobj

                while((iRet = fiobj.read(Buffer)) != -1)
                {
                    foobj.write(Buffer, 0, iRet);
                }

                fiobj.close();
                header = "";
            }

            foobj.close();
        }
        else
        {
            throw new Exception("There is no such folder");
        }
    }

    // --------------------- Unpacking logic -----------------------------

    public void fileUnpacker(String packFileName) throws Exception
    {
        File fpackobj = null;
        FileInputStream fiobj = null;
        FileOutputStream foobj = null;
        byte Header[] = new byte[100];
        String strHeader = null;
        String Tokens[] = null;
        File NewFile = null;
        byte Buffer[] = null;
        int iRet = 0;

        fpackobj = new File(packFileName);

        if(fpackobj.exists())
        {
            fiobj = new FileInputStream(fpackobj);

            while ((iRet = fiobj.read(Header, 0, 100)) != -1)
            {
                strHeader = new String(Header);

                System.out.println("Header is :" + strHeader);

                strHeader = strHeader.trim();
                strHeader = strHeader.replaceAll("\\s+", " ");

                Tokens = strHeader.split(" ");

                System.out.println("File Name :" + Tokens[0]);
                System.out.println("File Size :" + Tokens[1]);

                NewFile = new File(Tokens[0]);
                NewFile.createNewFile();

                foobj = new FileOutputStream(NewFile);

                Buffer = new byte[Integer.parseInt(Tokens[1])];

                // read data
                fiobj.read(Buffer, 0, Integer.parseInt(Tokens[1]));

                // write
                foobj.write(Buffer, 0, Integer.parseInt(Tokens[1]));

                foobj.close();
            } // end of while

            fiobj.close();
        }
        else
        {
            throw new Exception("There is no such pack file");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new FilePackUnpack());
    }
}
