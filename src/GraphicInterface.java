import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicInterface extends JDialog {
    private JFrame bookList;
    private DefaultTableModel model;
    private JButton saveBase, filterButton, setInfo, searchInfo, printButton;
    private JToolBar toolBar;
    private JScrollPane scroll_tab1, scroll;
    private JTable books;
    private JComboBox author;
    private JTextField bookName;
    private JTabbedPane dataPannel;
    private JPanel tab1, tab2;



    public GraphicInterface() {

        bookList = new JFrame();
        bookList = new JFrame("Почта");
        bookList.setSize(500, 300);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создание кнопок и прикрепление иконок
        {
            saveBase = new JButton(new ImageIcon("./resources/save_8780.png"));
            saveBase.setToolTipText("Сохранить список книг");
        } // Кнопка сохранения информации
        {
            setInfo = new JButton(new ImageIcon("./resources/add_6918.png"));
            setInfo.setToolTipText("Добавить новую информацию");
            setInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(bookList, "нужно сделать обавление информации");
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

        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(saveBase);
        toolBar.add(setInfo);
        toolBar.add(searchInfo);
        toolBar.add(printButton);




        // Размещение панели инструментов
        bookList.setLayout(new BorderLayout());
        bookList.add(toolBar, BorderLayout.NORTH);


        String [] columns = {"ФИО Клиента", "Дата рождения", "Письмо есть?"};
        String [][] data = {{"Десятников", "22.02.1999","Нет"},{"Карманов", "17.10.1998","Да"}};
        model=  new DefaultTableModel(data, columns);
        books = new JTable(model);

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

        bookList.setVisible(true);
    }



}

