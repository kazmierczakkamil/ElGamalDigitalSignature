package GUI;

import pl.elgamal.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;

public class elgamal {

    private JFrame fileChooser;
    private JButton createSign;
    private JPanel signPanel;
    private JPanel verifyPanel;
    private JButton verify;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField message1;
    private JTextField message2;
    private JTextField signatureA1;
    private JTextField signatureA2;
    private JTextField publicKeyP;
    private JTextField publicKeyG;
    private JTextField publicKeyY;
    private JTextField privateKeyX;
    private JLabel verificationStatus;
    private JTextField publicKeyP2;
    private JTextField publicKeyG2;
    private JTextField publicKeyY2;
    private JTextField signatureB1;
    private JTextField signatureB2;
    private JButton browseButton;
    private JButton browseButton1;
    private JTextField filePath;
    private JTextField filePath1;
    private JTextField publicKeyP3;
    private JTextField publicKeyG3;
    private JTextField publicKeyY3;
    private JTextField privateKeyX2;
    private JTextField signatureA3;
    private JTextField signatureB3;
    private JButton createSign2;
    private JButton verify2;
    private JTextField signatureA4;
    private JTextField signatureB4;
    private JTextField publicKeyP4;
    private JTextField publicKeyG4;
    private JTextField publicKeyY4;
    private JLabel verificationStatus2;


    private elgamal() {
        createSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger message = new BigInteger(message1.getText().getBytes());
                BigInteger[] values = Main.signMessage(message); // return p g x y k a b
                publicKeyP.setText(values[0].toString());
                publicKeyG.setText(values[1].toString());
                publicKeyY.setText(values[3].toString());
                privateKeyX.setText(values[2].toString());
                signatureA1.setText(values[5].toString());
                signatureB1.setText(values[6].toString());

                message2.setText(message1.getText());
                signatureA2.setText(signatureA1.getText());
                signatureB2.setText(signatureB1.getText());
                publicKeyP2.setText(publicKeyP.getText());
                publicKeyG2.setText(publicKeyG.getText());
                publicKeyY2.setText(publicKeyY.getText());
            }
        });


        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger message = new BigInteger(message2.getText().getBytes());
                BigInteger p = new BigInteger(publicKeyP2.getText());
                BigInteger g = new BigInteger(publicKeyG2.getText());
                BigInteger y = new BigInteger(publicKeyY2.getText());
                BigInteger a = new BigInteger(signatureA2.getText());
                BigInteger b = new BigInteger(signatureB2.getText());

                BigInteger v1 = Main.computeV1(g, message, p);
                BigInteger v2 = Main.computeV2(y, a, b, p);

                verificationStatus.setText(Main.checkCorrectness(v1, v2));
            }
        });

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                c.setCurrentDirectory(workingDirectory);
                int rVal = c.showOpenDialog(fileChooser);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String decryptedFilename = c.getSelectedFile().getPath();
                    filePath.setText(decryptedFilename);
                }
            }

        });

        browseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                c.setCurrentDirectory(workingDirectory);
                int rVal = c.showOpenDialog(fileChooser);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String decryptedFilename = c.getSelectedFile().getPath();
                    filePath1.setText(decryptedFilename);
                }
            }

        });

        createSign2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger message = new BigInteger(Main.convertFile(new File(filePath.getText()).toPath()));
                BigInteger[] values = Main.signMessage(message); // return p g x y k a b
                publicKeyP3.setText(values[0].toString());
                publicKeyG3.setText(values[1].toString());
                publicKeyY3.setText(values[3].toString());
                privateKeyX2.setText(values[2].toString());
                signatureA3.setText(values[5].toString());
                signatureB3.setText(values[6].toString());

                filePath1.setText(filePath.getText());
                signatureA4.setText(signatureA3.getText());
                signatureB4.setText(signatureB3.getText());
                publicKeyP4.setText(publicKeyP3.getText());
                publicKeyG4.setText(publicKeyG3.getText());
                publicKeyY4.setText(publicKeyY3.getText());
            }
        });

        verify2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger message = new BigInteger(Main.convertFile(new File(filePath1.getText()).toPath()));
                BigInteger p = new BigInteger(publicKeyP4.getText());
                BigInteger g = new BigInteger(publicKeyG4.getText());
                BigInteger y = new BigInteger(publicKeyY4.getText());
                BigInteger a = new BigInteger(signatureA4.getText());
                BigInteger b = new BigInteger(signatureB4.getText());

                BigInteger v1 = Main.computeV1(g, message, p);
                BigInteger v2 = Main.computeV2(y, a, b, p);

                verificationStatus2.setText(Main.checkCorrectness(v1, v2));
            }
        });

//        decryptFileButton.addActionListener(e -> {
//            String encryptedFilename = fileToDecryptPath.getText();
//            String decryptedFilename = elgamal.this.decryptedFilename.getText();
////            privateKeyP4.setText(key[1].toString());
////            privateKeyQ4.setText(key[2].toString());
//            if (encryptedFilename.equals("") || decryptedFilename.equals("")) {
//                JOptionPane.showMessageDialog(getPanel1(), "All fields must be set!");
//                return;
//            }
//
//           // decryptFile(new File(encryptedFilename).toPath(), decryptedFilename, key[1], key[2]);
//            JOptionPane.showMessageDialog(getPanel1(), "Decrypted file was saved successfully!");
//
//        });

    }


    private JPanel getPanel1() {
        return panel1;
    }

    public static void main(String[] args) {
        JFrame jframe = new JFrame("ElGamal - digital signature");
        jframe.setContentPane(new elgamal().panel1);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

    }



}
