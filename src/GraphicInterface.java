import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;


import java.util.stream.Collectors;

public class GraphicInterface extends JDialog {
    private JFrame bookList;
    private DefaultTableModel model;
    private JButton saveBase, loadBase, filterButton, setInfo, searchInfo, printButton, deleteButton, cancelFilterButton;
    private JToolBar toolBar;
    private JScrollPane scroll_tab1, scroll;
    private JTable books;
    private JComboBox filterBox;
    private JTextField areaNameFilter;
    private JTabbedPane dataPannel;
    private JPanel tab1, tab2;
    private TableRowSorter<TableModel> sorter;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem menuItemNew, menuItemSave, menuItemLoad,menuItemSettings, menuItemExit;
    private static String[] typeField = {"fam", "data", "logic"};
    private File configFile;
    private Settings configDialog;


    private void loadFile(DefaultTableModel table, String fileName) throws Exception {

        File file = new File(fileName);
        System.out.println(file.exists() ? "Yes" : "No");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                table.addRow(Arrays.stream(scanner.nextLine().split(";")).map(x -> x.replaceAll("\"|(null)", "")).collect(Collectors.toList()).toArray());
            }
        } catch (Exception exc) {
            System.out.println("Error reading file");
        }
    }
    public void saveFile(DefaultTableModel table, String fileName) throws Exception {


        File file = new File(fileName);

        boolean flag = false, flagEnter = false;
        try (PrintWriter printFile = new PrintWriter(file)) {

            for (Vector<String> rowVector : table.getDataVector()) {
                if (flagEnter)
                    printFile.println();
                else
                    flagEnter = true;
                flag = false;
                for (String s : rowVector) {
                    if (flag)
                        printFile.print(";");
                    else
                        flag = true;
                    printFile.print("\"" + s + "\"");
                }

            }

        } catch (Exception exc) {
            System.out.println("Error writing file");
        }
    }
    private static void checkField(String field, String type) throws Exception {
        if ("".equals(field) && !"empty".equals(type)) {
            throw new EmptyFieldException(field);
        } else if ("data".equals(type) && !field.matches("(\\d\\d|\\d)\\.(1[0-2]|0[1-9]|\\*\\*)\\.\\d{4}")) {
            throw new FormatFilterException();
        } else if ("logic".equals(type) && (!"Да".equals(field) && !"Нет".equals(field))) {
            throw new FormatFilterException();
        }
    }

    private static void checkField(String field) throws Exception {
        checkField(field, "empty");
    }

    private void createXML(){
        try {
            DocumentBuilder builder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
        } catch (ParserConfigurationException e) { e.printStackTrace(); }


    }

    private void InitWindow() {

        bookList = new JFrame("Почта");
        bookList.setSize(500, 300);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && (e.getKeyCode() == 's' || e.getKeyCode() == 'S')) {
                    saveBase.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        bookList.addWindowListener(new WindowListener() {
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
                try{
                    //saveFile(model);\
                    configDialog.SaveSettings();
                }
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
        }); // Действия при закрытия приложения
        bookList.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                bookList.requestFocusInWindow();
            }
        });


        configFile = new File(".\\resources\\config");
        configDialog = new Settings(configFile, bookList);

        {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuItemNew = new JMenuItem("Новая Таблица");
        menuItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();
            }
        });
        menuItemSave = new JMenuItem("Сохранить");
        menuItemSave.addActionListener(e -> loadBase.doClick());
        menuItemLoad = new JMenuItem("Загрузить");
        menuItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBase.doClick();
            }
        });

        menuItemSettings = new JMenuItem("Настройки");
        menuItemSettings.addActionListener((e)->configDialog.setVisible(true));
        menuItemExit = new JMenuItem("Выход");

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        }// Создание меню кнопок

        // Создание кнопок и прикрепление иконок
        {

            loadBase = new JButton(new ImageIcon("./resources/load_3125.png"));
            loadBase.setToolTipText("Загрузить список книг");
            loadBase.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        model.getDataVector().removeAllElements();
                        model.fireTableDataChanged();
                        FileDialog fileDialog = new FileDialog(bookList, "Выберите файл загрузки", FileDialog.LOAD);
                        fileDialog.setVisible(true);

                        String fileName = fileDialog.getDirectory() + fileDialog.getFile();
                        System.out.println(fileName);
                        if (fileDialog.getFile() != null)
                            loadFile(model, fileName);
                        System.out.println("Loaded");
                    } catch (Exception exc) {
                        System.out.println("что-то плохо читает");
                    }
                }
            });
        } // Кнопка загрузки информации
        {
            saveBase = new JButton(new ImageIcon("./resources/save_8780.png"));
            saveBase.setToolTipText("Сохранить список книг");
            saveBase.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        FileDialog fileDialog = new FileDialog(bookList, "Выберите файл загрузки", FileDialog.SAVE);
                        fileDialog.setDirectory(".\\resources");
                        fileDialog.setVisible(true);
                        String fileName = fileDialog.getDirectory() + fileDialog.getFile();
                        saveFile(model, fileName);
                        System.out.println("Saved");
                    } catch (Exception exc) {
                        System.out.println("что-то плохо читает");
                    }

                }
            });

        } // Кнопка сохранения информации
        {
            setInfo = new JButton(new ImageIcon("./resources/add_6918.png"));
            setInfo.setToolTipText("Добавить строку для новой информации");
            setInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<JTextField> textfields = new ArrayList<>(3);

                    JPanel panel = new JPanel();
                    JTextField textfield = new JTextField(10);
                    textfields.add(textfield);
                    panel.add(new JLabel("Фамилия: "));
                    panel.add(textfield);

                    textfield = new JTextField(10);
                    textfields.add(textfield);
                    panel.add(new JLabel("Дата рождения: "));
                    panel.add(textfield);

                    textfield = new JTextField(3);
                    textfields.add(textfield);
                    panel.add(new JLabel("Письмо?: "));
                    panel.add(textfield);
                    int result = -1;
                    while (result != JOptionPane.CANCEL_OPTION) {
                        result = JOptionPane.showConfirmDialog(null, panel, "Пожалуйста, введите значения новой записи", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            java.util.List<String> newRecord = textfields.stream().map(x -> x.getText()).collect(Collectors.toList());
                            try {
                                checkField(newRecord.get(0), typeField[0]);
                                checkField(newRecord.get(1), typeField[1]);
                                checkField(newRecord.get(2), typeField[2]);
                                model.addRow(newRecord.toArray());
                                return;
                            } catch (EmptyFieldException exc) {
                                JOptionPane.showMessageDialog(bookList, "Не все поля введены!");
                            } catch (FormatFilterException exc) {
                                JOptionPane.showMessageDialog(bookList, "Неправильный формат введенных данных!");
                            } catch (Exception exc) {
                                JOptionPane.showMessageDialog(bookList, "Где-то ошибка. Косяк программиста)))");
                                System.out.println(exc.getMessage());
                            }
                        }
                    }

                }
            });
        } // Кнопка добавления информации
        {
            deleteButton = new JButton(new ImageIcon("./resources/delete_3097.png"));
            deleteButton.setToolTipText("Удалить определенную строку");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    int[] arrayRowsForDelete = books.getSelectedRows();
                    for (int i = arrayRowsForDelete.length-1;i>=0;i--)
                        //books.remove(arrayRowsForDelete[i]);
                        model.removeRow(arrayRowsForDelete[i]);
                    JOptionPane.showMessageDialog(bookList, "Строки удалены");

                   // System.out.println(res);
                }
            });
        } // Кнопка удаления информации
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
                    createXML();
                    JOptionPane.showMessageDialog(bookList, "Хаха))Принтера нет ! А если и есть не заработает)");
                }
            });
        } // Кнопка принтера

        // Размещение панели инструментов
        String[] columns = {"ФИО Клиента", "Дата рождения", "Письмо есть?"};
        String[][] data = {};


        model = new DefaultTableModel(data, columns);
        sorter = new TableRowSorter<TableModel>(model);
        books = new JTable(model);

        tab1 = new JPanel();
        tab1.setLayout(new BorderLayout());
        tab2 = new JPanel();
        dataPannel = new JTabbedPane();
        scroll_tab1 = new JScrollPane(books);

        filterBox = new JComboBox(new String[]{"ФИО", "Дата рождения"});

        // Подготовка компонентов фильтра
        areaNameFilter = new JTextField("");
        areaNameFilter.setPreferredSize(new Dimension(120, 25));

        filterButton = new JButton("Поиск");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkName = areaNameFilter.getText();
                System.out.println("filter request:-" + checkName + "-");

                JOptionPane.showMessageDialog(bookList, "Проверка");
                try {
                    checkField(checkName, typeField[filterBox.getSelectedIndex()]);

                    sorter.setRowFilter(RowFilter.regexFilter(checkName, filterBox.getSelectedIndex()));
                    cancelFilterButton.setVisible(true);
                    System.out.println("Cancel button has appeared");
                } catch (EmptyFieldException exc) {
                    JOptionPane.showMessageDialog(bookList, "Вы ничего не ввели!");
                } catch (FormatFilterException exc) {
                    JOptionPane.showMessageDialog(bookList, "Неправильный формат введенных данных!");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(bookList, "Где-то ошибка. Косяк программиста)))");
                    System.out.println(exc.getMessage());
                }
            }
        });

        cancelFilterButton = new JButton("Отмена");
        cancelFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(null);
                cancelFilterButton.setVisible(false);
                System.out.println("Cancel button has hid");
            }
        });


    }

    private void buildingWindow() {

        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(loadBase);
        toolBar.add(saveBase);
        toolBar.add(setInfo);
        toolBar.add(deleteButton);
        toolBar.add(searchInfo);
        toolBar.add(printButton);
        bookList.setLayout(new BorderLayout());
        bookList.add(toolBar, BorderLayout.NORTH);

        menu.add(menuItemNew);
        menu.add(menuItemSave);
        menu.add(menuItemLoad);
        menu.add(menuItemSettings);
        menu.addSeparator();
        menu.add(menuItemExit);
        menuBar.add(menu);
        bookList.setJMenuBar(menuBar);

        books.setRowSorter(sorter);

        tab1.add(scroll_tab1);
        dataPannel.addTab("tab_1", tab1);
        dataPannel.addTab("tab_2", tab2);
        bookList.add(dataPannel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.add(filterBox);
        filterPanel.add(areaNameFilter);
        filterPanel.add(filterButton);
        filterPanel.add(cancelFilterButton);

        // Размещение  панели поиска внизу окна
        bookList.add(filterPanel, BorderLayout.SOUTH);

    }

    public GraphicInterface() {
        InitWindow();
        buildingWindow();
        try {
            if (configDialog.isAutoLoad())
                loadFile(model, "D:\\данные с флешки\\oop1\\resources\\inFile");
        }
        catch (Exception exc){
            System.out.println("Выгрузка информации не вышла");
        }



        /*try {
            configDialog = new Settings(configFile, bookList);

        } catch (Exception exc) {
            System.out.println("что-то плохо читает");
        }*/










        bookList.setVisible(true);
        cancelFilterButton.setVisible(false);

    }


}

