import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Die Methode deklariert alle benötigten Elemente des dargestellten Login-Fensters
 */
public class LoginForm extends JDialog {

    private JTextField tfUserName;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;

    final String DB_URL = "jdbc:mysql://localhost:3306/spaceinvaders";     //Anmelde-Daten für SQL-Server werden als Variablen gespeichert
    final String USERNAME = "root";
    final String PASSWORD = "";

    /**
     * übergibt alle Informationen aus der gespielten Runde, die in den Statistiken gespeichert werden sollen
     * @param parent
     * @param starter
     * @param roundScore
     * @param roundWave
     * @param roundKills
     * @param roundShotsFired
     * @param roundHeartsLost
     */
    public LoginForm(JFrame parent, Starter starter, int roundScore, int roundWave, int roundKills, int roundShotsFired, int roundHeartsLost) {
        super(parent);
        setTitle("Login");      //legt alle Angaben für das Fenster fest
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {      //wenn Ok-Button gedrückt wird, dann:
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = tfUserName.getText();     //der Inhalt des UserName-Textfelds wird in der Variable gespeichert
                String password = String.valueOf(pfPassword.getPassword());     //der Inhalt des Passwort-Textfelds wird in der Variable gespeichert

                user = getAuthenticatedUser(userName, password); //der User wird auf korrekten Username und Passwort überprüft

                if (user != null) { //User ist zulässig, also werden die Statistiken an die SQL-Tabelle übergeben und das Login-Fenster geschlossen
                    registerStatistics(userName, roundScore, roundWave, roundKills, roundShotsFired, roundHeartsLost);
                    dispose();
                    starter.showTable();        //die Tabelle wird aufgerufen
                }
                else {      //User ist unzulässig, also wird eine Fehlermeldung ausgegeben
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Username or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {      //wenn, der Cancel-Button gedrückt wird, schließt sich das Login-Fenster und man wird zum Menü weitergeleitet
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                starter.openMenu();
            }
        });

        setVisible(true);
    }

    /**
     * Neuer User wird erzeugt.
     */
    public User user;

    /**
     * Die Methode vergleicht die User-Angaben mit dem Inhalt der Tabelle
     * @param userName
     * @param password
     * @return
     */
    private User getAuthenticatedUser(String userName, String password) {
        User user = null;

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);      //Verbindung zu SQL-Server wird hergestellt

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE name=? AND password=?";     //zu überprüfende User-Angaben werden in SQL-Tabelle "User" ausgewählt
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {     //wenn User vorhanden/zulässig, legt für die Variablen der Klasse User die Werte aus der User-Tabelle fest
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();   //schließt Verbindung und SQL-Statement ab

        }catch(Exception e){
            e.printStackTrace();
        }


        return user;       //gibt (wenn zu zulässig) User-Daten zurück, ansonsten null (Userdaten "leer")
    }

    /**
     * Erzeugt neues Objekt der Klasse Statistics.
     */
    public Statistics statistics;

    /**
     * Fügt die Rundenstatistiken in eine Instanz von Statistics ein.
     * @param userName
     * @param score
     * @param survivedWaves
     * @param kills
     * @param shotsFired
     * @param heartsLost
     */
    public void registerStatistics(String userName, int score, int survivedWaves, int kills, int shotsFired, int heartsLost){

        statistics = addStatisticsToDatabase(userName, score, survivedWaves, kills, shotsFired, heartsLost);

    }

    /**
     * Die Methode speichert alle Runden-Daten der letzten Runde in der SQL-Tabelle "Statistics"
     * @param userName
     * @param score
     * @param survivedWaves
     * @param kills
     * @param shotsFired
     * @param heartsLost
     * @return
     */
private Statistics addStatisticsToDatabase(String userName, int score, int survivedWaves, int kills, int shotsFired, int heartsLost){
        Statistics statistics = null;

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);  //Verbindung zu SQL-Server wird hergestellt

            Statement stmt = conn.createStatement();

            Date dt = new Date();
            SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy");
            String date = df1.format(dt);
            String sql = "INSERT INTO statistics (userName, score, survivedWaves, kills, shotsFired, heartsLost, date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";        //SQL-Befehl für das Einfügen der Runden-Werte
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, score);
            preparedStatement.setInt(3, survivedWaves);
            preparedStatement.setInt(4, kills);
            preparedStatement.setInt(5, shotsFired);
            preparedStatement.setInt(6, heartsLost);
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

        return statistics;      //gibt statistics-Daten zurück

    }



}
