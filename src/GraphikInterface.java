import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GraphikInterface extends JFrame {
    private JFrame bookList;
    private DefaultTableModel model;
    private JButton save, filter;
    private JToolBar toolBar;
    private JScrollPane scroll;
    private JTable books;
    private JComboBox author;
    private JTextField bookName;


    public GraphikInterface() {
        bookList = new JFrame();
        bookList = new JFrame("Список книг");
        bookList.setSize(500, 300);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание кнопок и прикрепление иконок
        save = new JButton(new ImageIcon("./img/save.png"));

        // Настройка подсказок для кнопок
        save.setToolTipText("Сохранить список книг");


        // Добавление кнопок на панель инструментов
        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(save);


        // Размещение панели инструментов
        bookList.setLayout(new BorderLayout());
        bookList.add(toolBar, BorderLayout.NORTH);


        String [] columns = {"Автор", "Книга", "Есть"};
        String [][] data = {{"Александр Дюма", "Три мушкетёра", "Есть"},
                {"Алексей Толстой", "Анна Каренина", "Нет"}};
        model=  new DefaultTableModel(data, columns);
        books = new JTable(model);
        scroll = new JScrollPane(books);

        // Размещение таблицы с данными
        bookList.add(scroll, BorderLayout.CENTER);

        // Подготовка компонентов поиска
        author = new JComboBox(new String[]{"Автор", "Александр Дюма", "Алексей Толстой"});
        bookName = new JTextField("Название книги");
        filter = new JButton("Поиск");
        // Добавление компонентов на панель
        JPanel filterPanel = new JPanel();
        filterPanel.add(author);
        filterPanel.add(bookName);
        filterPanel.add(filter);

        // Размещение  панели поиска внизу окна
        bookList.add(filterPanel, BorderLayout.SOUTH);

        // Визуализация экранной формы
        bookList.setVisible(true);
    }


}

