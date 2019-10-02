import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import java.util.stream.Collectors;

public class GraphicInterface extends JDialog {
    private JFrame bookList;
    private DefaultTableModel model;
    private JButton saveBase, filterButton, setInfo, searchInfo, printButton,deleteButton;
    private JToolBar toolBar;
    private JScrollPane scroll_tab1, scroll;
    private JTable books;
    private JComboBox author;
    private JTextField bookName;
    private JTabbedPane dataPannel;
    private JPanel tab1, tab2;

    public static void loadFile(DefaultTableModel table) throws Exception{
        File file = new File("./resources/inFile");
        System.out.println(file.exists()?"Yes":"No");
        try(Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine()) {
                table.addRow(Arrays.stream(scanner.nextLine().split(";")).map(x->x.replaceAll("\"|(null)","")).collect(Collectors.toList()).toArray());
            }
        }
        catch(Exception exc){
            System.out.println("Error reading file");
        }
    }

    public static void saveFile(DefaultTableModel table) throws Exception{

        File file = new File("./resources/saveFile.csv");
        boolean flag = false, flagEnter = false;
        try(PrintWriter printFile = new PrintWriter(file)){

            for(Vector<String> rowVector:table.getDataVector()){
                if(flagEnter)
                    printFile.println();
                else
                    flagEnter = true;
                flag = false;
                for(String s: rowVector){
                    if(flag)
                        printFile.print(";");
                    else
                        flag = true;
                    printFile.print("\""+s+"\"");
                }

            }

        }
        catch(Exception exc){
            System.out.println("Error writing file");
        }
    }

    public GraphicInterface() {

        bookList = new JFrame();
        bookList = new JFrame("Почта");
        bookList.setSize(500, 300);
        bookList.setLocation(100, 100);
        //bookList.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // Создание кнопок и прикрепление иконок
        {
            saveBase = new JButton(new ImageIcon("./resources/save_8780.png"));
            saveBase.setToolTipText("Сохранить список книг");
            saveBase.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{saveFile(model);}
                    catch(Exception exc){
                        System.out.println("что-то плохо читает");
                    }
                }
            });
        } // Кнопка сохранения информации
        {
            setInfo = new JButton(new ImageIcon("./resources/add_6918.png"));
            setInfo.setToolTipText("Добавить новую информацию");
            setInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addRow(new String[0]);
                }
            });
        } // Кнопка добавления информации
        {
            searchInfo = new JButton(new ImageIcon("./resources/search_5588.png"));
            searchInfo.setToolTipText("Просмотреть и найти информацию");
        } // Кнопка поиска
        {
            printButton = new JButton(new ImageIcon("./resources/print_7018.png"));
            printButton.setToolTipText("Не думаю, что эта кнопка что-то сделает :3");
            printButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog (bookList, "Хаха))Принтера нет ! А если и есть не заработает)");
                }
            });
        } // Кнопка принтера
        {
            deleteButton = new JButton(new ImageIcon("./resources/delete_3097.png"));
            deleteButton.setToolTipText("Добавить новую информацию");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object[] options = { "Да", "Нет!" };

                    String res = JOptionPane
                            .showInputDialog(bookList, "Введите номер строки",
                                    "Окно ввода", JOptionPane.QUESTION_MESSAGE);

                    try {
                        int n = Integer.parseInt(res);
                        model.removeRow(n);
                    } catch(ArrayIndexOutOfBoundsException exc){
                        JOptionPane.showMessageDialog (bookList, "Incorrect index row",
                                "Input Error", JOptionPane.WARNING_MESSAGE);
                    } catch(NumberFormatException exc){
                        JOptionPane.showMessageDialog (bookList, "Incorrect number - incorrect string",
                                "Input Error", JOptionPane.WARNING_MESSAGE);
                    }

                    System.out.println(res);
                }
            });
        }
        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(saveBase);
        toolBar.add(setInfo);
        toolBar.add(deleteButton);
        toolBar.add(searchInfo);
        toolBar.add(printButton);



        // Размещение панели инструментов
        bookList.setLayout(new BorderLayout());
        bookList.add(toolBar, BorderLayout.NORTH);


        String [] columns = {"ФИО Клиента", "Дата рождения", "Письмо есть?"};
        String [][] data = {};

        model=  new DefaultTableModel(data, columns);
        books = new JTable(model);

        try{loadFile(model);}
        catch(Exception exc){
            System.out.println("что-то плохо читает");
        }
     //   books.setValueAt("hello", 2, 2);

        tab1 = new JPanel();
        tab1.setLayout(new BorderLayout());
        tab2 = new JPanel();
        dataPannel = new JTabbedPane();
        scroll_tab1 = new JScrollPane(books);
        tab1.add(scroll_tab1);
        dataPannel.addTab("tab_1",tab1);
        dataPannel.addTab("tab_2",tab2);


        bookList.add(dataPannel, BorderLayout.CENTER);

        // Подготовка компонентов поиска
        author = new JComboBox(new String[]{"ФИО", "Дата рождения"});

        bookName = new JTextField("Название книги");
        JButton filterButton = new JButton("Поиск");
        filterButton.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               JOptionPane.showMessageDialog (bookList, "Проверка");
                                           }
                                       });


        // Добавление компонентов на панель

        JPanel filterPanel = new JPanel();
        filterPanel.add(author);
        filterPanel.add(bookName);
        filterPanel.add(filterButton);

        // Размещение  панели поиска внизу окна
        bookList.add(filterPanel, BorderLayout.SOUTH);

        /*bookList.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent event) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Закрыть окно?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                try{saveFile(model);}
                catch(Exception exc){
                    System.out.println("что-то плохо читает");
                }
                System.out.println("Сохранено");
                if (n == 0) {
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });*/

        bookList.setVisible(true);
    }



}

