import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Deklariert alle benötigten Elemente des Tabellen-Fensters
 */
public class DisplayTable extends JDialog{
    Starter starter;
    Connection con;
    PreparedStatement pst;
    private JPanel statisticsPanel;        //deklariert alle benötigten Elemente des Tabellen-Fensters
    private JTable table1;
    private JButton searchButton;
    private JTextField insertUserTxt;
    private JScrollPane table_1;
    private JButton showAllButton;
    private JButton highScoresButton;
    private JButton menuBtn;
    private boolean playerOnly = false;
    private String insertUserName;

    final String DB_URL = "jdbc:mysql://localhost:3306/spaceinvaders";     //Anmelde-Daten für SQL-Server werden als Variablen gespeichert
    final String USERNAME = "root";
    final String PASSWORD = "";

    /**
     * Hier wird der JFrame erzeugt und die actionListener werden hinzugefügt.
     * @param parent
     * @param starter
     */
    public DisplayTable(JFrame parent, Starter starter, Menu menu) { //bekommt starter übergeben

        super(parent);
        this.starter = starter;             //erzeugt ein neues JFrame mit folgenden Eigenschaften
        setTitle("Statistics");             //legt alle Angaben für das Fenster fest
        setContentPane(statisticsPanel);
        setMinimumSize(new Dimension(800, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        connect();      //verbindet sich mit dem SQL-Server
        table_load();       //lädt die Tabelle für die Statistiken


        searchButton.addActionListener(new ActionListener() { //wenn man auch den Search-Button drückt, dann:
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    insertUserName = insertUserTxt.getText();        //der gesuchte Username wird mit den Usernames in der SQL-Tabelle "statistics" verglichen und die mit dem selben Username werden ausgewählt
                    pst = con.prepareStatement("SELECT userName, score, survivedWaves, kills, shotsFired, heartsLost, date FROM statistics WHERE userName = ? ORDER BY roundNr DESC");
                    pst.setString(1, insertUserName);
                    ResultSet rs = pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));     //zeigt Tabelle an
                    playerOnly = true;
                    insertUserTxt.setText("");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        showAllButton.addActionListener(new ActionListener() {      //gibt die Tabelle, falls sie zuvor nach Usernames gefiltert war, wieder vollständig mit allen Werten (allen Usern) aus
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    pst = con.prepareStatement("select userName, score, survivedWaves, kills, shotsFired, heartsLost, date from statistics ORDER BY roundNr DESC");     //SQL-Statement für alle Werte
                    ResultSet rs = pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));  //zeigt Tabelle an
                    playerOnly = false;
                } catch (SQLException p) {
                    p.printStackTrace();
                }

            }
        });


        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(playerOnly == false){
                        pst = con.prepareStatement("SELECT userName, score, survivedWaves, kills, shotsFired, heartsLost, date FROM statistics ORDER BY score DESC"); }   //SQL-Statement für alle Werte
                    else{
                        pst = con.prepareStatement("SELECT userName, score, survivedWaves, kills, shotsFired, heartsLost, date FROM statistics WHERE userName = ? ORDER BY score DESC");
                        pst.setString(1, insertUserName);
                    }
                    ResultSet rs = pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));     //zeigt Tabelle an
                } catch (SQLException p) {
                    p.printStackTrace();
                }
            }
        });

        table_1.addMouseWheelListener(new MouseWheelListener() {      //Note
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

            }
        });

        menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuMusik.musicStop();
                dispose();
                starter.openMenu();
            }
        });

        setVisible(true);
    }

    /**
     * Versucht eine Verbindung zum SQl Server herzustellen.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);     //stellt mithilfe von unseren Zugangsdaten eine Verbindung zu unserem SQL-Server her

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Zeigt die gesamte Datenbank als Tabelle an
     */
    void table_load() {
        try {
            pst = con.prepareStatement("select userName, score, survivedWaves, kills, shotsFired, heartsLost, date from statistics ORDER BY roundNr DESC");         //SQL-Statement für alle Werte
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));         //zeigt Tabelle an
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


