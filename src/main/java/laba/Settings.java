package laba;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Settings extends JDialog {
    private JPanel contentPane, panel1,panel2;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox autoLoadSettings;
    private File configFile;


    public void LoadSettings(){
        //System.out.println(configFile.exists() ? "Yes" : "No");
        try (Scanner scanner = new Scanner(configFile)) {
            String s = Arrays.stream(scanner.nextLine().split(";")).map(x -> x.replaceAll("\"|(null)", "")).collect(Collectors.toList()).get(0);
            autoLoadSettings.setSelected("Yes".equals(s));
            //scanner.nextLine().split(";"))
            //Arrays.stream(scanner.nextLine().split(";")).map(x -> x.replaceAll("\"|(null)", "")).collect(Collectors.toList()).toArray());

        } catch (Exception exc) {
            System.out.println("Error reading config file");
        }
    }
    public void SaveSettings(){
        try (PrintWriter printFile = new PrintWriter(configFile)) {
            printFile.println(autoLoadSettings.isSelected()?"Yes":"No");
        } catch (Exception exc) {
            System.out.println("Error writing config file");
        }

    }

    public boolean isAutoLoad(){return autoLoadSettings.isSelected();};

    public Settings(File configFile, Frame frame) {
        super(frame, "Настройки", true);
        this.configFile = configFile;
        $$$setupUI$$$();

        setSize(300, 200);
        setLocation(150, 200);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        LoadSettings();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }



    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        contentPane.add(panel1, BorderLayout.CENTER);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

        autoLoadSettings = new JCheckBox("Афтоматически загружать последний файл");
        autoLoadSettings.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(autoLoadSettings.isSelected());
            }
        });
        panel1.add(autoLoadSettings);


        panel2 = new JPanel();
        contentPane.add(panel2, BorderLayout.SOUTH);
        buttonOK = new JButton("OK");
        panel2.add(buttonOK);

        buttonCancel = new JButton("Cancel");
        panel2.add(buttonCancel);

    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
