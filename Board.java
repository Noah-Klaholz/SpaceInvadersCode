import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Diese Klasse verwaltet das gesamte Spiel an sich und übernimmt sowohl die Funktion als GUI und die grafische Darstellung,
 * als auch das Verändern von wichtigen Variablen wie beispielsweise die Anzahl der Spielerleben.
 */

public class Board extends JPanel implements Runnable, MouseListener {

    Toolkit t = Toolkit.getDefaultToolkit();

    //Vorbereiten der Sprites
    Image AlienImg = t.getImage("PNG/SpriteAlien1.png");
    Image AlienImg1 = t.getImage("PNG/SpriteAlien1.png");
    Image AlienImg2 = t.getImage("PNG/SpriteAlien2.png");
    Image Explosion1 = t.getImage("PNG/Explosion1.png");
    Image Explosion2 = t.getImage("PNG/Explosion2.png");
    Image Explosion3 = t.getImage("PNG/Explosion3.png");
    Image Explosion4 = t.getImage("PNG/Explosion4.png");
    Image Explosion5 = t.getImage("PNG/Explosion5.png");
    Image Explosion6 = t.getImage("PNG/Explosion6.png");
    Image Explosion7 = t.getImage("PNG/Explosion7.png");
    Image PlayerImg = t.getImage("PNG/SpaceShip.png");
    Image HeartImg = t.getImage("PNG/Herz.png");
    Image HeartWings = t.getImage("PNG/HerzFlügel.png");
    Image ShotSpeedUpImage = t.getImage("PNG/ShotSpeedPowerUp.png");
    Image MoveSpeedUpImage = t.getImage("PNG/MoveSpeedPowerUp.png");
    Image MultiShotUpImage = t.getImage("PNG/MultiShotPowerUp.png");
    Image BackgroundImg = t.getImage("PNG/background.jpg");
    Image PlayerHitImg = t.getImage("PNG/SpaceShipHit.png");
    Image PowerUpMinusPfeil = t.getImage("PNG/PowerUpMinusPfeil.png");
    Image PowerUpPlusPfeil = t.getImage("PNG/PowerUpPlusPfeil.png");
    Image ExitButton = t.getImage("PNG/ExitButton.png");
    Image KeyA = t.getImage("PNG/KeyA.png");
    Image KeyD = t.getImage("PNG/KeyD.png");
    Image KeyArrowLeft = t.getImage("PNG/KeyArrowLeft.png");
    Image KeyArrowRight = t.getImage("PNG/KeyArrowRight.png");
    Image MouseButton = t.getImage("PNG/MouseButton.png");

    Image PowerUpSymbolEin = t.getImage("PNG/PowerUpSymbolEin.png");
    Image PowerUpSymbolAus = t.getImage("PNG/PowerUpSymbolAus.png");

    //Vorbereiten der Fonts

    Font big = new Font("Bitstream Vera Sans", Font.BOLD, 100);
    Font small = new Font("Bitstream Vera Sans", Font.BOLD, 22);
    Font middle = new Font("Bitstream Vera Sans", Font.BOLD, 35);

    //Initialisierung und Deklarierung der globalen Variablen

    Starter starter;
    private final Dimension d;
    Player p;
    ArrayList<Alien> Aliens = new ArrayList<>();
    ArrayList<Shot> PShots = new ArrayList<>();
    ArrayList<Shot> AShots = new ArrayList<>();
    ArrayList<Explosion> Explosions = new ArrayList<>();
    ArrayList<Item> Items = new ArrayList<>();

    int mouseX;
    int mouseY;
    private Thread animator;
    boolean ingame = true;
    boolean controls = true;
    boolean playerHit = false;
    boolean Shot = false;

    //Mit den longs werden später mit System.currentTimeMillis() Abfragen in Bezug auf die Zeit erstellt

    long hitTime;
    long lastShotTime;
    long aLastShotTime;
    long aLastMove;
    long waveClearTime;
    long pExplosionTime;


    //Hier wird ein Double verwendet, weil der Wert in nextWave() dividiert wird

    double aMoveCooldown = 1000;

    //Die folgenden Variablen sind die Startwerte für das Spiel

    int shotCooldown = 500;
    int aShotCooldown = 1500;
    int BOARD_WIDTH = 1500;
    int BOARD_HEIGHT = 1000;
    int MAX_HP = 5;
    int AlienDown = 45;
    int pSpeed = 2;
    int pMaxSpeed = 8;
    int aSpeed = 30;
    int shotSpeed = 10;
    int aShotSpeed = 5;
    int alienWidth = 90;
    int alienCount = 40;
    int shotWidth = 10;
    int shotHeight = 25;
    int playerWidth = 90;
    int HP = 3;
    int alienworth = 10;

    //Chancen, dass Items droppen (neben anderen Bedingungen)
    double HeartDropChance = 0.01;
    double ShotSpeedUpChance = 0.01;
    double MoveSpeedUpChance = 0.01;
    double MultiShotUpChance = 0.01;
    double MultiShotChance = 0; //Chance, dass ein normaler Schuss zu einem Multishot wird

    //Die tolerance Variablen geben an, um wie viel Schüsse daneben liegen können und trotzdem als "hit" zählen

    int alienTolerance = 7; //So weit vom Alien darf ein Schuss weg sein, um dennoch zu zählen → Hiermit kann man das Spiel einfacher machen
    int playerTolerance = 0; //So weit vom Spieler darf ein Schuss liegen, um dennoch zu zählen → Hiermit kann man das Spiel schwerer machen
    int playerItemTolerance = 7; //So weit vom Spieler können Items sein, um dennoch aufgesammelt zu werden

    //Diese Variablen werden genutzt, um die Statistiken des Spielers ingame und im Game-Over-Screen darzustellen und in der Datenbank abzuspeichern

    int score;
    int ShotSpeedUpAnzahl;
    int MoveSpeedUpAnzahl;
    int MultiShotUpAnzahl;
    int shotsFired;
    int heartsLost;
    int wave; //Gibt an wie viele Wellen überlebt wurden
    int kills;
    int aMove;

    boolean sandBox;
    boolean hearts = true;
    boolean powerUps = true;
    boolean shooting = false;
    float setBackgroundVolume;
    float setSoundVolume;

    Menu menu;

    /**
     * Im Konstruktor von Board wird das Spiel initialisiert, d.h. zum Beispiel die Hintergrundmusik gestartet und ihre Llutstärke geregelt, der Mouse- und KeyListener hinzugefügt und die Animationsschleife gestartet.
     * @param starter
     * @param sandBox
     * @param setBackgroundVolume
     * @param setSoundVolume
     */
    public Board(Starter starter, boolean sandBox, float setBackgroundVolume, float setSoundVolume, Menu menu) {
        //Verweist auf Starter
        this.starter = starter;
        this.setBackgroundVolume = setBackgroundVolume;
        this.setSoundVolume = setSoundVolume;
        this.sandBox = sandBox;
        this.menu = menu;
        //Startet die Hintergrundmusik (s. Musik.java Klasse)
        BackgroundMusic.musicStart(setBackgroundVolume);
        //Fügt einen Key und Mouse Listener hinzu, um im Spiel inputs vom Spieler zu erkennen
        addMouseListener(this);
        //addKeyListener(new TAdapter(this));
        addKeyListener(new TAdapter(this));
        //die Dimension d spart viel Schreibarbeit, weil BOARD_WIDTH und BOARD_HEIGHT einige Male verwendet werden
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        p = new Player((d.width - playerWidth) / 2, d.height - 220, pSpeed);

        spawnAliens();

        //Startet das eigentliche Spiel, indem ein neuer Thread erstellt wird
        if (animator == null || !ingame) {
            animator = new Thread(this);
            //Lässt den neuen Thread die run()-Methode aufrufen
            animator.start();
        }
        //Verhindert, dass die Aliens sofort schießen
        aLastShotTime = System.currentTimeMillis() + 500;
    }

    /**
     * Diese Methode erstellt die Aliens und setzt ihre Koordinaten so, dass jeweils 10 in einer Reihe sind.
     */
    //Die spawnAliens()-Methode erstellt die Aliens erstmalig und wieder für jede neue Gegner-Welle
    public void spawnAliens() {
        //Startwerte für die Position des Aliens links oben
        int ax = 15;
        int ay = 95;
        //Schleife geht durch jedes Alien auf dem Bildschirm durch und berechnet die neue Position
        for (int i = 1; i < (alienCount + 1); i++) {
            Aliens.add(new Alien(ax, ay, aSpeed));
            ax += alienWidth + 15;
            //Wenn bereits 10 Aliens in einer Reihe sind, soll eine neue angefangen werden
            if (i % 10 == 0) {
                ax = 15;
                ay += alienWidth + 5;
            }
        }
    }

    /**
     * Die paint-Methode zeichnet alle Spielelemente im Fenster und ruft, falls nötig, andere Methoden auf, um diese zu bewegen.
     * Dies gilt beispielsweise für die Schüsse der Spieler und Gegner, die bevor sie gezeichnet werden können noch bewegt werden müssen.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g) {
        //Setzt den Hintergrund
        g.drawImage(BackgroundImg, 0, 0, this);
        g.drawImage(ExitButton, 650, 15, this);

        if(controls && ingame && starter.tutorials){
            g.drawImage(KeyA, 200, 600, this);
            g.drawImage(KeyD, 260, 600, this);
            g.drawImage(KeyArrowLeft, 200, 650, this);
            g.drawImage(KeyArrowRight, 260, 650, this);
            g.drawImage(MouseButton, 335, 620, this);

            g.setFont(small);
            g.setColor(Color.lightGray);
            g.drawString("shoot", 395, 660);
        }

        //Zeichnet das Raumschiff, wenn er getroffen wurde und startet das Aufblinken des Raumschiffs
        if (p.isVisible && playerHit) {
            movePlayer();
            //Zeichnet den Spieler in Weiß
            g.drawImage(PlayerHitImg, p.x, p.y, this);
            //Nach 150ms wird das Raumschiff wieder normal angezeigt, aber nach 300 soll es nochmals weiß sein
            if (System.currentTimeMillis() - hitTime >= 150 && System.currentTimeMillis() - hitTime <= 300) {
                playerHit = false;
            }
            //Nach 450ms wird das Raumschiff wieder normal angezeigt
            if (System.currentTimeMillis() - hitTime >= 450) {
                playerHit = false;
            }
        }

        //Zeichnet den Spieler normal
        if (p.isVisible && !playerHit) {
            movePlayer();
            //Zeichnet das Raumschiff normal
            g.drawImage(PlayerImg, p.x, p.y, this);
            //Zwischen 300ms und 450ms nach einem Treffer wird das Raumschiff normal gezeichnet
            if (System.currentTimeMillis() - hitTime >= 300 && System.currentTimeMillis() - hitTime <= 450) {
                playerHit = true;
            }
        }

        //Zeigt für einen kurzen Zeitraum nach dem Überleben einer Welle einen wave Cleared Schriftzug an, und um wie viel der Score erhöht wurde
        if (System.currentTimeMillis() - waveClearTime >= 100 && System.currentTimeMillis() - waveClearTime <= 1500) {
            g.setFont(middle);
            g.setColor(Color.white);
            g.drawString("Wave cleared! ", d.width / 2 - 130, 620);
            g.drawString("Score: + " + (alienworth * 10), d.width / 2 - 110, 680);
        }

        //Die Schüsse sollen rot gezeichnet werden, daher wird hier die Farbe auf Rot gesetzt
        g.setColor(Color.red);

        movePShots();

        //Zeichnet jeden Schuss des Spielers im Fenster
        for (int i = 0; i < PShots.size(); i++) {
            g.fillRect(PShots.get(i).x, PShots.get(i).y, shotWidth, shotHeight);
        }

        //Zeichnet jeden Schuss des Gegners im Fenster
        moveAShots(g);
        for (int i = 0; i < AShots.size(); i++) {
            g.fillRect(AShots.get(i).x, AShots.get(i).y, shotWidth, shotHeight);
        }

        moveAliens();
        //Wählt zwischen den beiden Sprites für Aliens aus, um eine Animation zu erzeugen (Bei jedem "Schritt" der Gegner wird ein anderes Sprite verwendet)
        if (aMove % 2 == 0) {
            AlienImg = AlienImg1;
        } else {
            AlienImg = AlienImg2;
        }

        //Zählt durch alle Aliens durch
        for (int i = 0; i < Aliens.size(); i++) {
            //Wenn die Aliens eine bestimmte Linie am unteren Bildschirmrand überschreiten, hat der Spieler verloren und es wird der Game-Over-Screen aufgerufen
            if (Aliens.get(i).y > d.height - 300) {
                gameOver(g);
            }
            //Zeichnet das Alien an der Stelle i in der ArrayListe
            g.drawImage(AlienImg, Aliens.get(i).x, Aliens.get(i).y, this);
        }

        drawExplosions(g);

        drawItems(g);

        updateVars(g);

        //Überprüft, ob der Spieler keine Leben mehr hat, und somit verloren hat und die Zeit der Explosionsanimation abgelaufen ist
        if (HP < 1 && (System.currentTimeMillis() - pExplosionTime) >= 500) {
            gameOver(g);
        }
        //Überprüft ob via dem space-key geschossen wird und ruft die shoot Methode auf, die die Schüsse erzeugt
        if(shooting) {
            shoot();
        }
    }

    /**
     * Die Methode updateVars() erneuert die Werte der gesamten UI (also die Herzen und den Score).
     * @param g
     */
    public void updateVars(Graphics g) {
        if(hearts) {
            for (int i = 1; i <= HP; i++) {
                g.drawImage(HeartImg, d.width - 40 - i * 45, 10, this);
            }
            if(sandBox && ingame){
                g.drawImage(PowerUpMinusPfeil, d.width - 105, 70, this);
                g.drawImage(PowerUpPlusPfeil, d.width - 75, 70, this);
            }
        }
        else{
            g.drawImage(HeartWings, d.width - 150, 10, this);
        }
        //Zeichnet die PowerUp Icons an den oberen Rand des Fensters
        g.drawImage(ShotSpeedUpImage, 20, 10, this);
        g.drawImage(MoveSpeedUpImage, 170, 10, this);
        g.drawImage(MultiShotUpImage, 320, 10, this);
        //Setzt die Schriftart und Farbe
        g.setFont(small);
        g.setColor(Color.white);
        //Gibt für jedes Item die eingesammelte Anzahl an (zeichnet diese)
        g.drawString("x " + ShotSpeedUpAnzahl, 75, 40);
        g.drawString("x " + MoveSpeedUpAnzahl, 225, 40);
        g.drawString("x " + MultiShotUpAnzahl, 375, 40);
        g.drawString("Score: " + score, d.width / 2 - 20, 40);

        if(sandBox) {
            if(ingame) {
                for (int i = 0; i <= 2; i++) {
                    g.drawImage(PowerUpMinusPfeil, 20 + i * 150, 70, this);
                    g.drawImage(PowerUpPlusPfeil, 50 + i * 150, 70, this);
                }
            }
            if(powerUps) g.drawImage(PowerUpSymbolEin, 450, 10, this);
            else g.drawImage(PowerUpSymbolAus, 450, 10, this);
        }
    }

    /**
     * Die drawItems Methode wird in paint() aufgerufen und zeichnet alle Items
     * @param g
     */
    public void drawItems(Graphics g) {
        //Überprüft, ob es Items zu zeichnen gibt
        if (Items.size() != 0) {
            //Zählt durch alle Items, die aktuell da sind durch
            for (int i = 0; i < Items.size(); i++) {
                //Überprüft welcher Item-Typ gegeben ist und zeichnet diesen
                if (Items.get(i).type == "Heart") {
                    g.drawImage(HeartImg, Items.get(i).x, Items.get(i).y, this);
                }
                if (Items.get(i).type == "ShotSpeedUp") {
                    g.drawImage(ShotSpeedUpImage, Items.get(i).x, Items.get(i).y, this);
                }
                if (Items.get(i).type == "MoveSpeedUp") {
                    g.drawImage(MoveSpeedUpImage, Items.get(i).x, Items.get(i).y, this);
                }
                if (Items.get(i).type == "MultiShotUp") {
                    g.drawImage(MultiShotUpImage, Items.get(i).x, Items.get(i).y, this);
                }
                //Setzt das Item abhängig von seinem Speed weiter nach unten
                Items.get(i).y += Items.get(i).speed;
                //Überprüft, ob das Item vom Spieler eingesammelt wurde und verändert die Werte dementsprechend
                if (Items.get(i).x >= p.x - playerItemTolerance && Items.get(i).x <= p.x + playerWidth + playerItemTolerance && Items.get(i).x + shotWidth >= p.x - playerItemTolerance && Items.get(i).x + shotWidth <= p.x + playerWidth + playerItemTolerance && Items.get(i).y >= p.y && Items.get(i).y <= p.y + playerWidth) {
                    if (Items.get(i).type == "Heart" && ((!sandBox && HP < MAX_HP) || (sandBox && HP < 10))){
                        HP++;
                    }
                    if (Items.get(i).type == "ShotSpeedUp" && ((!sandBox && shotCooldown > 250) || (sandBox && shotCooldown >= 50))) {
                        shotCooldown -= 50;
                        ShotSpeedUpAnzahl++;
                    }
                    if (Items.get(i).type == "MoveSpeedUp" && p.speed < pMaxSpeed) {
                        p.speed++;
                        MoveSpeedUpAnzahl++;
                    }
                    if (Items.get(i).type == "MultiShotUp" && MultiShotChance < 0.9) {
                        MultiShotChance += 0.1;
                        MultiShotUpAnzahl++;
                    }
                    Soundeffects.music(Items.get(i).audioTrack, setSoundVolume);
                    Items.remove(i);
                } else if (Items.get(i).y >= BOARD_HEIGHT)
                    Items.remove(i); //Entfernt Items, die unter dem Bildschirmrand sind
            }
        }
    }


    /**
     * Die drawExplosions Methode zeichnet alle Explosionen, die aktuell auf dem Bildschirm stattfinden anhand der Phase in der sie sich befinden.
     * Auf diese Weise ist es möglich animierte Explosionen darzustellen.
     * @param g
     */
    public void drawExplosions(Graphics g) {
        //Zeichnet und animiert die Explosionen
        for (int i = 0; i < Explosions.size(); i++) {
            int frame = Explosions.get(i).frame;
            //Überprüft für jede Explosion welches frame der Animation gezeichnet werden soll und zeichnet dieses
            if (frame == 1) g.drawImage(Explosion1, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 2) g.drawImage(Explosion2, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 3) g.drawImage(Explosion3, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 4) g.drawImage(Explosion4, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 5) g.drawImage(Explosion5, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 6) g.drawImage(Explosion6, Explosions.get(i).x, Explosions.get(i).y, this);
            if (frame == 7) g.drawImage(Explosion7, Explosions.get(i).x, Explosions.get(i).y, this);
            //Die Animation soll jedes frame für einen bestimmten Zeitwert zeigen
            if (frame <= 7 && System.currentTimeMillis() - Explosions.get(i).lastFrameTime >= 65) {
                //Erhöht frame, somit wird im nächsten repaint() das nächste frame angezeigt
                Explosions.get(i).frame += 1;
                //Setzt den Cooldown der Animation zurück
                Explosions.get(i).lastFrameTime = System.currentTimeMillis();
            } else if (frame > 7)
                Explosions.remove(i); //Wenn alle frames abgespielt wurden, soll die Explosion wieder entfernt werden
        }
    }

    /**
     * Die gameOver()-Methode wird aufgerufen, wenn der Spieler verloren hat.
     * In ihr werden zuerst alle unwichtigen Bildschirmelemente entfernt und dann die labels()-Methode aufgerufen
     * @param g
     */
    public void gameOver(Graphics g) {
        ingame = false;
        HP = 0;
        AShots.clear();
        PShots.clear();
        Aliens.clear();
        p.isVisible = false;
        labels(g, small);
        repaint();
    }

    /**
     * Gibt einen zufälligen (ca. 1/6 Chance für jeden) GameOver SoundEffect (als String, also Dateipfad) zurück
     * @return
     */
    public String getGameOverSoundEffect() {
        double r = Math.random();
        if (r <= 0.17) return "audio/GameOver0.wav";
        else if (r <= 0.33) return "audio/GameOver1.wav";
        else if (r <= 0.5) return "audio/GameOver2.wav";
        else if (r <= 0.67) return "audio/GameOver3.wav";
        else if (r <= 0.83) return "audio/GameOver4.wav";
        else return "audio/GameOver5.wav";
    }

    /**
     * Wird in gameOver() aufgerufen und zeichnet alle Schriftzüge für den Game-Over-Screen.
     * @param g
     * @param small
     */
    public void labels(Graphics g, Font small) {
        g.setColor(Color.white);
        g.setFont(big);
        g.drawString("GAME OVER!", 450, 250);
        g.setFont(small);
        g.drawString("Score: " + score, 685, 350);
        g.drawString("Survived waves: " + wave, 685, 400);
        g.drawString("Kills: " + kills, 685, 450);
        g.drawString("Shots fired: " + shotsFired, 685, 500);
        g.drawString("Hearts lost: " + heartsLost, 685, 550);
        g.setColor(Color.gray);
        g.drawString("Press 1 to restart", 635, 700);
        g.drawString("Press Escape to return to the Menu", 580, 750);
        if(!sandBox) g.drawString("Press Shift to register this runs statistics in the leaderboard", 450, 800);
    }


    /**
     * Die nextWave()-Methode wird aufgerufen, wenn der Spieler eine Welle an Aliens besiegt hat und sie erstellt eine neue Welle an Gegnern.
     */
    public void nextWave() {
        //Diese Zeile sorgt dafür, dass es möglich ist in paint() für einen gewissen Zeitraum die Score-Erhöhung durch das Schaffen der Welle anzuzeigen
        waveClearTime = System.currentTimeMillis();
        //erhöht den Score und die wave Variable (die für die Statistiken wichtig ist)
        score += alienworth * 10;
        wave++;
        //Es werden einige Werte geändert, um die nächste Welle schwerer zu machen
        alienworth += 5;
        aMoveCooldown = aMoveCooldown / 1.1;
        aShotCooldown -= 50;
        spawnAliens();
    }

    /**
     * Fügt mit einer bestimmten Chance eines von vier möglichen, einsammelbaren PowerUps ein, wenn ein Gegner mit einem Schuss besiegt wurde.
     * @param alien
     */
    public void dropChance(Alien alien) {
        boolean itemDropped = false;
        //Erzeugt Zufallszahl zwischen 0 und 1
        double r = Math.random();
        //Überprüft, ob die Zufallszahl kleiner der Chance auf Herzen ist. Damit hat die If-Abfrage eine Wahrscheinlichkeit ausgeführt zu werden, die der HeartDropChance entspricht.
        //so funktionieren die anderen Abfragen ebenfalls
        if (wave >= 0 && r <= HeartDropChance && hearts) {
            Items.add(new Heart(alien.x, alien.y));
            itemDropped = true;
        }
        r = Math.random();
        if (wave >= 0 && r <= ShotSpeedUpChance && !itemDropped  && ((sandBox && ShotSpeedUpAnzahl < 10) || (!sandBox && ShotSpeedUpAnzahl < 5))) {
            Items.add(new ShotSpeedUp(alien.x, alien.y));
            itemDropped = true;
        }
        r = Math.random();
        if (wave >= 0 && r <= MoveSpeedUpChance && !itemDropped && MoveSpeedUpAnzahl < 6) {
            Items.add(new MoveSpeedUp(alien.x, alien.y));
            itemDropped = true;
        }
        r = Math.random();
        if (wave >= 0 && r <= MultiShotUpChance && !itemDropped && MultiShotUpAnzahl < 10) {
            Items.add(new MultiShot(alien.x, alien.y));
        }
    }

    /**
     * Wird in paint() aufgerufen und bewegt das Spieler-Raumschiff anhand der Inputs des Spieles, die im KeyListener ausgelesen werden.
     */
    public void movePlayer() {
        //Bewegt das Raumschiff entweder nach rechts oder links, je nach Inputs des Spielers
        if (p.moveRight == true) {
            p.x += p.speed;
        }
        if (p.moveLeft == true) {
            p.x -= p.speed;
        }

        //Verhindert das Herausfliegen am linken Bildschirmrand
        if (p.x <= 0) {
            p.x = 0;
        }
        //Verhindert das Herausfliegen am rechten Bildschirmrand
        if (p.x >= (d.width - (playerWidth + 15))) {
            p.x = (d.width - (playerWidth + 15));
        }
    }

    /**
     * Wird in paint() aufgerufen, bewegt alle Aliens und erzeugt deren Schüsse,
     * wobei nach einem festgelegten Schusscooldown immer ein zufälliges der verbleibenden Aliens schießt.
     */
    public void moveAliens() {
        //Nur, wenn der Bewegungs-Cooldown abgelaufen ist, werden die Aliens bewegt. So wird die teleportationsartige Bewegung der Gegner erzeugt
        if (System.currentTimeMillis() - aLastMove >= aMoveCooldown) {
            aMove++;
            for (int i = 0; i < Aliens.size(); i++) {
                //Entscheidet, ob die Aliens nach links oder Rechts bewegt werden müssen und bewegt sie dementsprechend
                if (Aliens.get(i).moveLeft == true) {
                    Aliens.get(i).x -= Aliens.get(i).speed;
                }
                if (Aliens.get(i).moveRight == true) {
                    Aliens.get(i).x += Aliens.get(i).speed;
                }
            }
            //Erneuert den Bewegungs-Cooldown
            aLastMove = System.currentTimeMillis();
        }

        //Wenn ein Alien einen gewissen Abstand von den Rändern des Spielfensters erreicht hat,
        //werden alle Aliens um einen bestimmten Wert (AlienDown) nach unten versetzt und ihre Bewegungsrichtung gespiegelt
        for (int i = 0; i < Aliens.size(); i++) {
            //Linker Rand wird überprüft
            if (Aliens.get(i).x > d.width - 120) {
                //Zählt durch alle Aliens durch
                for (int j = 0; j < Aliens.size(); j++) {
                    //kehrt die Bewegungsrichtung um
                    Aliens.get(j).moveLeft = true;
                    Aliens.get(j).moveRight = false;
                    //Setzt die Aliens nach unten und macht sofort einen Schritt in die neue Richtung
                    Aliens.get(j).y += AlienDown;
                    Aliens.get(j).x -= Aliens.get(j).speed;
                }
                if (wave <= 12) { //Nach jeder Änderung der Bewegungsrichtung (bis Wave = 12) werden die Aliens schneller
                    aMoveCooldown -= 20;
                }
            }
            //Rechter Rand wird überprüft
            if (Aliens.get(i).x < 0) {
                //Zählt durch alle Aliens durch
                for (int j = 0; j < Aliens.size(); j++) {
                    //kehrt die Bewegungsrichtung um
                    Aliens.get(j).moveRight = true;
                    Aliens.get(j).moveLeft = false;
                    //Setzt die Aliens nach unten und macht sofort einen Schritt in die neue Richtung
                    Aliens.get(j).y += AlienDown;
                    Aliens.get(j).x += Aliens.get(j).speed;
                }
                //s.o.
                if (wave <= 12) { //Nach jeder Änderung der Bewegungsrichtung (bis Wave = 12) werden die Aliens schneller
                    aMoveCooldown -= 20;
                }
            }
        }

        //Fragt ab, ob der Schuss-Cooldown abgelaufen ist
        if ((System.currentTimeMillis() - aLastShotTime) >= aShotCooldown && Aliens.size() != 0) {
            //Wählt ein zufälliges Alien aus
            int r = (int) Math.round(Math.random() * (Aliens.size() - 1));
            //Erzeugt einen Schuss in diesem Alien und setzt die "hostile" Variable für diesen auf true, damit er nur den Spieler treffen kann und sich nach unten bewegt
            AShots.add(new Shot(Aliens.get(r).x + AlienImg.getWidth(this) / 2, Aliens.get(r).y, aShotSpeed));
            //Erneuert den Schuss-Cooldown, damit die Aliens nicht durchgängig schießen können
            aLastShotTime = System.currentTimeMillis();
            //Spielt den Schuss-Soundeffekt ab
            Soundeffects.music("audio/Shot.wav", setSoundVolume);
        }
    }

    /**
     * Wird in paint() aufgerufen, bewegt die Schüsse der Gegner und überprüft, ob der Spieler getroffen wurde.
     * Ist dies der Fall, wird ein Soundeffect abgespielt und der Spieler verliert ein Leben.
     * Wenn die Spielerleben auf Null sinken, wird eine Explosion inklusive zugehörigem Soundeffect erstellt.
     * @param g
     */
    public void moveAShots(Graphics g) {
        //Ruft alle Elemente der ArrayList auf
        for (int i = 0; i < AShots.size(); i++) {
            //Bewegt die Schüsse nach unten
            AShots.get(i).y += aShotSpeed;
            //Entfernt die Schüsse, sobald sie außerhalb des Fensters sind
            if (AShots.get(i).y >= d.height) AShots.remove(i);
            //Wenn Schüsse auf dem Bildschirm sind, wird überprüft, ob diese den Spieler berühren
            if (!AShots.isEmpty()) {
                if (AShots.get(i).x >= p.x - playerTolerance && AShots.get(i).x <= p.x + playerWidth + playerTolerance && AShots.get(i).x + shotWidth >= p.x - playerTolerance && AShots.get(i).x + shotWidth <= p.x + playerWidth + playerTolerance && AShots.get(i).y >= p.y && AShots.get(i).y <= p.y + playerWidth) {
                    //In paint() muss abgefragt werden, ob der Spieler gerade getroffen wurde, damit das Aufleuchten gezeichnet wird, das wird mit folgendem boolean überprüft
                    playerHit = true;
                    //Hiermit wird das weiße Aufleuchten des Raumschiffs erzeugt (dafür ist der Zeitpunkt wichtig, zu dem der Spieler getroffen wurde)
                    hitTime = System.currentTimeMillis();
                    //Wird der Spieler getroffen wird der Schuss entfernt, und die Herzen-Anzahl verringert
                    AShots.remove(i);
                    if(hearts) {
                        heartsLost++;
                        Soundeffects.music("audio/player_gethit.wav", setSoundVolume);
                        if (HP > 0) {
                            HP--;
                            if (HP == 0) {
                                pExplosionTime = System.currentTimeMillis();
                                p.isVisible = false;
                                BackgroundMusic.musicStop();
                                Explosions.add(new Explosion(p.x, p.y, 1));
                                Soundeffects.gameOver("audio/explosionSound.wav", getGameOverSoundEffect(), setSoundVolume);
                                drawExplosions(g);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Wird in paint() aufgerufen und bewegt die Schüsse des Spielers und überprüft ob ein Alien getroffen wurde.
     * Wird ein Alien getroffen, wird an dessen Stelle eine Explosion mit zugehörigem Soundeffekt erzeugt.
     */
    public void movePShots() {
        //Geht alle Elemente der Liste PShots durch
        for (int i = 0; i < PShots.size(); i++) {
            Shot = true;
            //Bewegt die Schüsse nach oben
            PShots.get(i).y -= shotSpeed;
            //Wenn die Schüsse über den oberen Fensterrand hinausgehen, werden sie entfernt
            if (PShots.get(i).y <= 0) {
                PShots.remove(i);
                Shot = false;
            }
            //Überprüft für jedes Alien, ob es von einem Schuss getroffen wurde
            for (int j = 0; j < Aliens.size(); j++) {
                if (!PShots.isEmpty() && Shot && i < PShots.size()) {
                    if (PShots.get(i).x >= Aliens.get(j).x - alienTolerance && PShots.get(i).x <= Aliens.get(j).x + alienWidth + alienTolerance && PShots.get(i).x + shotWidth >= Aliens.get(j).x - alienTolerance && PShots.get(i).x + shotWidth <= Aliens.get(j).x + alienWidth + alienTolerance && PShots.get(i).y >= Aliens.get(j).y && PShots.get(i).y <= Aliens.get(j).y + alienWidth) {
                        //Bei jedem erledigten Alien gibt es eine Chance, dass ein PowerUp erzeugt wird, dies ist in der Methode dropChance() erklärt
                        if(powerUps){
                            dropChance(Aliens.get(j));
                        }
                        //Wird ein Alien getroffen, wird sowohl das Alien, wie auch der entsprechende Schuss entfernt und die Anzahl der Kills, sowie der Score erhöht
                        PShots.remove(i);
                        //Eine Explosion wird bei den Alien-Koordinaten erzeugt
                        Explosions.add(new Explosion(Aliens.get(j).x, Aliens.get(j).y, 1));
                        Soundeffects.music("audio/explosionSound.wav", setSoundVolume);
                        Aliens.remove(j);
                        kills++;
                        score += alienworth;
                        //Wenn alle Aliens besiegt wurden, wird die nächste Welle an Gegnern erzeugt, indem die nextWave()-Methode aufgerufen wird
                        if (Aliens.isEmpty() && ingame) {
                            PShots.clear();
                            nextWave();
                        }
                    }
                }
            }
        }
    }

    /**
     * Wenn der Cooldown abgelaufen ist, wird mit dieser Methode ein Schuss erzeugt. Sie wird entweder über den MouseListener mit der linken Maustaste aufgerufen oder über den KeyListener mit
     */
    public void shoot() {
        // Überprüft ob der Schuss-Cooldown für Spieler bereits abgelaufen ist (indem die aktuelle System-Zeit mit der des letzten Schusses verglichen wird und dann überprüft wird,
        // ob die Differenz dieser größer als der Cooldown ist
        if ((System.currentTimeMillis() - lastShotTime) >= shotCooldown && ingame) {
            double r = Math.random(); //Erstellt eine Zufallszahl zwischen (inklusive) 0 und 1
            if (r <= MultiShotChance) { //Überprüft, ob die Zufallszahl kleiner als die MultiShotChance ist (somit hat die if-Abfrage eine Chance aufgerufen zu werden, die der MultiShotChance entspricht
                PShots.add(new Shot(p.x + playerWidth / 2 - 38, p.y + 50, shotSpeed)); //Fügt zwei Schüsse hinzu (s. unten)
                PShots.add(new Shot(p.x + playerWidth / 2 + 22, p.y + 50, shotSpeed));
                shotsFired += 2; //Addiert zwei zu den bisher abgefeuerten Schüssen (für die Statistiken)
            }

            PShots.add(new Shot(p.x + playerWidth / 2 - 8, p.y, shotSpeed));  //Erzeugt einen neuen Schuss in der Mitte des Spieler-Sprites und setzt seinen "hostile"-Wert auf false
            lastShotTime = System.currentTimeMillis(); //Damit kann der Schuss Cooldown abgefragt werden
            shotsFired++;
            Soundeffects.music("audio/Shot.wav", setSoundVolume);
        }
    }


    /**
     * Wenn die linke Maustaste betätigt wird, wird mouseClicked() aufgerufen.
     * Die Methode wird sowohl dafür genutzt, um zu überprüfen, ob einer der Buttons im Fenster betätigt wurde und dafür,
     * dass der Spieler mit der linken Maustaste mit einem Cooldown Schüsse abfeuern kann.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        controls = false;

        if(sandBox && mouseX >= 450 && mouseX <= 490 && mouseY >= 10 && mouseY <= 50){ //erlaubt im Sandbox-Modus das Droppen von PowerUps aus- und wieder einzuschalten
            powerUps= !powerUps;
        }

        else if(sandBox && mouseX >= 50 && mouseX <= 80 && mouseY >= 60 && mouseY <= 90 && shotCooldown >= 50) { //erlaubt im Sandbox-Modus die Anzahl eingesammelter ShotSpeed-Powerups zu erhöhen (siehe oberes) und zu verringern (siehe unteres)
            shotCooldown -= 50;
            ShotSpeedUpAnzahl++;}
        else if(sandBox && mouseX >= 20 && mouseX <= 50 && mouseY >= 60 && mouseY <= 90 && shotCooldown < 1000) {
            shotCooldown += 50;
            ShotSpeedUpAnzahl--;
        }

        else if(sandBox && mouseX >= 200 && mouseX <= 230 && mouseY >= 60 && mouseY <= 90 && p.speed < pMaxSpeed) { //erlaubt im Sandbox-Modus die Anzahl eingesammelter MoveSpeed-Powerups zu erhöhen (siehe oberes) und zu verringern (siehe unteres)
            p.speed++;
            MoveSpeedUpAnzahl++;}
        else if(sandBox && mouseX >= 170 && mouseX <= 200 && mouseY >= 60 && mouseY <= 90 && p.speed > 1) {
            p.speed--;
            MoveSpeedUpAnzahl--;
        }

        else if(sandBox && mouseX >= 350 && mouseX <= 380 && mouseY >= 60 && mouseY <= 90 && MultiShotChance < 0.9){ //erlaubt im Sandbox-Modus die Anzahl eingesammelter MultiShot-Powerups zu erhöhen (siehe oberes) und zu verringern (siehe unteres)
            MultiShotChance += 0.1;
            MultiShotUpAnzahl++;}
        else if(sandBox && mouseX >= 320 && mouseX <= 350 && mouseY >= 60 && mouseY <= 90 && MultiShotChance > 0.1){
            MultiShotChance -= 0.1;
            MultiShotUpAnzahl--;
        }

        else if(ingame && sandBox && mouseX >= d.width - 75 && mouseX <= d.width - 45 && mouseY >= 70 && mouseY <= 100 && HP < 10 && hearts){ //erlaubt im Sandbox-Modus die Anzahl Herzen zu erhöhen (siehe oberes) und zu verringern (siehe unteres)
            HP++;}
        else if(ingame && sandBox && mouseX >= d.width -105 && mouseX <= d.width - 75 && mouseY >= 70 && mouseY <= 100 && HP > 1 && hearts){
            HP--;
        }

        else if(sandBox && mouseX >= d.width - 175 && mouseX <= d.width - 40 && mouseY >= 10 && mouseY <= 50){ // //erlaubt im Sandbox-Modus die Herzen und den Schaden auszuschalten (siehe oberes) und wieder einzuschalten (siehe unteres)
            hearts = !hearts;
        }
        else if (ingame && mouseX >= 650 && mouseX <= 686 && mouseY >= 15 && mouseY <= 51){
            BackgroundMusic.musicStop();
            remove(menu);
            starter.openMenu();  //öffnet das Menu
            starter.remove(this);   //Entfernt das board
        }

        shoot();
    }

    //Die restlichen @Overrides werden nicht verwendet, müssen aber implementiert werden

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Implementiert einen KeyAdapter, um die Inputs auf der Tastatur zu erkennen, mit denen beispielsweise die Spielfigur bewegt werden kann.
     */
    private class TAdapter extends KeyAdapter {
        //Board wird später gebraucht, um es in Starter über einen hier eingelesenen Knopfdruck zu entfernen
        Board board;

        public TAdapter(Board board) {
            this.board = board;
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            //Überprüft, ob der gedrückte Key "d" oder die rechte Pfeiltaste ist
            if (key == 39 || key == 68) {
                //Sorgt dafür, dass der Spieler sich in movePlayer() nach rechts bewegt
                p.moveRight = true;
                controls = false;
            }
            //Überprüft, ob der gedrückte Key "a" oder die linke Pfeiltaste ist
            if (key == 37 || key == 65) {
                //Sorgt dafür, dass der Spieler sich in movePlayer() nach links bewegt
                p.moveLeft = true;
                controls = false;
            }
            //Überprüft, ob die Leertaste gedrückt wird und das Spiel gerade nicht läuft
            if (key == 49 && !ingame) {
                //Stoppt die aktuelle Musik
                BackgroundMusic.musicStop();
                //Entfernt das board
                starter.remove(board);
                //Startet das Spiel neu (fügt wieder ein neues Board hinzu)
                starter.startGame(sandBox);
            }
            //Überprüft, ob die Escape-Taste gedrückt wird un das Spiel gerade nicht läuft
            if (key == 27 && !ingame) {
                //Stoppt die aktuelle Musik
                BackgroundMusic.musicStop();
                //öffnet das Menu
                starter.openMenu();
                //Entfernt das board
                starter.remove(board);
            }
            //Überprüft ob der enter-Key gedrückt wurde und das Spiel vorbei ist
            if (key == 16 && !ingame && !sandBox) {
                starter.enterName(score, wave, kills, shotsFired, heartsLost);
                starter.remove(board);
            }
            //Überprüft ob der space-Key gedrückt wurde und das Spiel läuft
            if (key == 32 && ingame) {
                shooting = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            //Überprüft, ob der losgelassene Key "d" oder die rechte Pfeiltaste ist
            if (key == 39 || key == 68) {
                //Sorgt dafür, dass der Spieler sich in movePlayer() nicht mehr nach rechts bewegt
                p.moveRight = false;
            }
            //Überprüft, ob der losgelassene Key "a" oder die linke Pfeiltaste ist
            if (key == 37 || key == 65) {
                //Sorgt dafür, dass der Spieler sich in movePlayer() nicht mehr nach links bewegt
                p.moveLeft = false;
            }
            //Überprüft ob der space-Key gedrückt wurde und das Spiel läuft
            if (key == 32 && ingame) {
                shooting = false;
            }
        }
    }

    /**
     * Wird vom Thread animator aufgerufen und ruft in jedem "Spieletick" die paint() Methode auf.
     */
    public void run() {
        int animationDelay = 5;
        long time = System.currentTimeMillis();
        //Infinite loop; ruft paint()-auf
        while (true) {
            if (ingame) repaint();
            //Wartet das animationDelay ab
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}