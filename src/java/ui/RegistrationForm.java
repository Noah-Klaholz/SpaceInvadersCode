package src.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import src.java.core.entities.*;
import src.java.app.Starter;

/**
 * Diese Klasse ist für das Öffnen und das Verwalten des Registrierungsfensters zuständig.
 * Sie speichert auch gleichzeitig die Runden Ergebnisse unter dem Neu angelegten Benutzer.
 * Wenn man fertig mit der Regestrierung ist, werden die statistics aufgerufen und angezeigt.
 * Auch kann man von dem Regestrierungsformular aus zum Loginformular kommen, falls man schon einen Benutzer und ein Password hat.
 */
public class RegistrationForm extends JDialog {
    Starter starter;
    private JTextField tfName;          //deklariert alle benötigten Elemente des dargestellten Registrations-Fensters
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JButton loginButton;

    private String userName;

    final String DB_URL = "jdbc:mysql://localhost:3306/spaceinvaders";      //Anmelde-Daten für SQL-Server werden als Variablen gespeichert
    final String USERNAME = "root";
    final String PASSWORD = "";

    /**
     * Hier wird der JFrame erzeugt und die actionListener werden hinzugefügt.
     * @param parent
     * @param starter
     * @param roundScore
     * @param roundWave
     * @param roundKills
     * @param roundShotsFired
     * @param roundHeartsLost
     */
    public RegistrationForm(JFrame parent, Starter starter, int roundScore, int roundWave, int roundKills, int roundShotsFired, int roundHeartsLost) {  // übergibt alle Informationen aus der gespielten Runde, die in den Statistiken gespeichert werden sollen
        super(parent);
        this.starter = starter;         //legt alle Angaben für das Fenster fest
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(550, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {        //wenn Register-Button gedrückt wird, dann:
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();    //src.java.core.entities.User wird in SQL-Tabelle "src.java.core.entities.User" gespeichert
                if(user != null){
                registerStatistics(userName, roundScore, roundWave, roundKills, roundShotsFired, roundHeartsLost);
                dispose();
                starter.showTable();}
            }
        });
        btnCancel.addActionListener(new ActionListener() {      //wenn Cancel-Button gedrückt wird, dann:
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();      //Registrations-Fenster wird geschlossen
                starter.openMenu();     //Spiel-Menü wird geöffnet
            }
        });
        loginButton.addActionListener(new ActionListener() {        //wenn Login-Button gedrückt wird, dann;
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //schließt fenster
                login(roundScore, roundWave, roundKills, roundShotsFired, roundHeartsLost);     //login-Methode wird gestartet

            }
        });
        setVisible(true);
    }

    /**
     * Diese Methode fragt die Felder ab und schaut, ob alle Eingaben getätigt wurden und ob die Passwörter gleich sind.
     */
    private void registerUser() {
        userName = tfName.getText();     //legt Variablen für die Werte aus den Textfeldern fest
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());


        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {      //prüft, ob eines der Textfelder leer ist, dann Fehlermeldung
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (!password.equals(confirmPassword)) {        //prüft, ob Passwort dasselbe ist, wie bei "confirm password". Wenn nicht, dann Fehlermeldung
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            user = addUserToDatabase(userName, email, password);    //gibt src.java.core.entities.User-Daten an Methode "addUserToDatabase" weiter
        }

        if(user == null) {      //sonstige Fehlermeldung
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Die Methode übergibt Runden-Daten an starter.login und schließt nach starter.login das Fenster
     * @param roundScore
     * @param roundWave
     * @param roundKills
     * @param roundShotsFired
     * @param roundHeartsLost
     */
    public void login(int roundScore, int roundWave, int roundKills, int roundShotsFired, int roundHeartsLost) {    //übergibt Runden-Daten an starter.login und schließt nach starter.login das Fenster
        starter.login(roundScore, roundWave, roundKills, roundShotsFired, roundHeartsLost);
        dispose();
    }

    /**
     * Die Methode übergibt Runden-Daten
     * @param userName
     * @param score
     * @param survivedWaves
     * @param kills
     * @param shotsFired
     * @param heartsLost
     */
    public void registerStatistics(String userName, int score, int survivedWaves, int kills, int shotsFired, int heartsLost){   //übergibt Runden-Daten

        statistics = addStatisticsToDatabase(userName, score, survivedWaves, kills, shotsFired, heartsLost);

    }

    /**
     * neuer src.java.core.entities.User wird erzeugt
     */
    public User user;

    /**
     * neue src.java.core.entities.Statistics werden erzeugt
     */
    public Statistics statistics;

    /**
     * Die Methode speichert alle src.java.core.entities.User-Daten der Registration in der SQL-Tabelle "src.java.core.entities.User"
     * @param name
     * @param email
     * @param password
     * @return
     */
    private User addUserToDatabase(String name, String email, String password) {      //Methode: speichert alle src.java.core.entities.User-Daten der Registration in der SQL-Tabelle "src.java.core.entities.User"
        User user = null;

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);      //Verbindung zu SQL-Server wird hergestellt

            Statement stmt = conn.createStatement();
            String sql1 = "SELECT name FROM users WHERE name = ?";
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement1.setString(1, name);//SQL-Befehl für das Einfügen der src.java.core.entities.User-Daten
            ResultSet rs = preparedStatement1.executeQuery();
            if(!rs.next()) {

                String sql2 = "INSERT INTO users (name, email, password) " +
                        "VALUES (?, ?, ?)";            //SQL-Befehl für das Einfügen der src.java.core.entities.User-Daten
                PreparedStatement preparedStatement = conn.prepareStatement(sql2);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);       //"Einfügen" der einzelnen Werte für die Tabelle bei "VALUES"

                //Insert row into the table
                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.name = name;
                    user.email = email;
                    user.password = password;           //initialisiert für alle Variablen des Objekts user den passenden Wert

                }
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Enter login, if you already have an account.",
                        "Existing src.java.core.entities.User",
                        JOptionPane.ERROR_MESSAGE);
            }



            stmt.close();
            conn.close();           //schließt Verbindung und SQL-Statement ab

        }catch(Exception e){
            e.printStackTrace();
        }

        return user;        //gibt user-Daten zurück
    }

    /**
     * Die Methode speichert alle Runden-Daten der letzten Runde in der SQL-Tabelle "src.java.core.entities.Statistics"
     * @param userName
     * @param score
     * @param survivedWaves
     * @param kills
     * @param shotsFired
     * @param heartsLost
     * @return
     */
    private Statistics addStatisticsToDatabase(String userName, int score, int survivedWaves, int kills, int shotsFired, int heartsLost){       //Methode: speichert alle Runden-Daten der letzten Runde in der SQL-Tabelle "src.java.core.entities.Statistics"
        Statistics statistics = null;

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);      //Verbindung zu SQL-Server wird hergestellti
            java.util.Date dt = new Date();
            SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy");
            String date = df1.format(dt);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO statistics (userName, score, survivedWaves, kills, shotsFired, heartsLost, date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";         //SQL-Befehl für das Einfügen der Runden-Werte
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, score);
            preparedStatement.setInt(3, survivedWaves);
            preparedStatement.setInt(4, kills);
            preparedStatement.setInt(5, shotsFired);
            preparedStatement.setInt(6, heartsLost);        //"Einfügen" der einzelnen Werte für die Tabelle bei "VALUES"
            preparedStatement.setString(7, date); //"Einfügen" der einzelnen Werte für die Tabelle bei "VALUES"

            //fügt Zeile in Tabelle ein
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                statistics = new Statistics();
                statistics.userName = userName;
                statistics.score = score;
                statistics.survivedWaves = survivedWaves;
                statistics.kills = kills;
                statistics.shotsFired = shotsFired;
                statistics.heartsLost = heartsLost;     //initialisiert für alle Variablen des Objekts statistics den passenden Wert
            }

            stmt.close();
            conn.close();       //schließt Verbindung und SQL-Statement ab

        }catch(Exception e){
            e.printStackTrace();
        }

        return statistics;       //gibt statistics-Daten zurück

    }

}