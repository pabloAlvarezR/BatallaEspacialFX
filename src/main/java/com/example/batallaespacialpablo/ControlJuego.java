package com.example.batallaespacialpablo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static javafx.animation.Animation.INDEFINITE;


public class ControlJuego extends Application {


    public ArrayList<Barco> barcos;

    private Equipo ganador;
    private AnchorPane root;
    private final static String SEP = System.getProperty("file.separator");
    private final static String AUDIOS = (SEP +"audio" +SEP+"musica.wav");

    public static void main(String[] args) {launch(args);
    }

    public synchronized ArrayList<Barco> getBarcos(){
        return barcos;
    }

    public synchronized Equipo ganador(){
        return ganador;
    }
    public synchronized void setGanador(Equipo equipo) {
        ganador = equipo;
        Platform.runLater(() -> mostrarGanador(equipo));
    }


    @Override
    public void start(Stage primaryStage) {
        root = new AnchorPane();
        Image image = new Image(ControlJuego.class.getResource("imgs/fondoespacio.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(0, 0, true, true, true, true));
        root.setBackground(new Background(backgroundImage));
        Button button = new Button("Comenzar batalla");
        button.setPrefWidth(20);
        button.setPrefHeight(50);
        button.setFont(Font.font("Agency FB", 30));
        AnchorPane.setBottomAnchor(button, 400d);
        AnchorPane.setLeftAnchor(button,300d);
        AnchorPane.setRightAnchor(button,300d);
        Stop[] stop = {new Stop(0, Color.RED),
                new Stop(0.5, Color.GREEN),
                new Stop(1, Color.BLUE)};
        LinearGradient gradient = new LinearGradient(0, 0,
                1, 0, true, CycleMethod.NO_CYCLE, stop);
        button.setBackground(new Background(new BackgroundFill(gradient, new CornerRadii(20), Insets.EMPTY)));
        button.setCursor(Cursor.HAND);
        button.setOnMouseEntered(e -> button.setCursor(Cursor.HAND));

        barcos = new ArrayList<>();
        ganador = null;

        Destructor destructorRojo = new Destructor(this, Equipo.ROJO, 960, 200);
        Destructor destructorAzul = new Destructor(this, Equipo.AZUL, 320, 760);

        Lancha lanchaRoja = new Lancha(this, Equipo.ROJO, 900, 200);
        Lancha lanchaAzul = new Lancha(this, Equipo.AZUL, 380, 760);

        Lancha lanchaRoja2 = new Lancha(this, Equipo.ROJO, 540, 200);
        Lancha lanchaAzul2 = new Lancha(this, Equipo.AZUL, 540, 760);

        Acorazado acorazadoRojo = new Acorazado(this, Equipo.ROJO, 500, 200);
        Acorazado acorazadoAzul = new Acorazado(this, Equipo.AZUL, 780, 760);

        Submarino submarinoRojo = new Submarino(this, Equipo.ROJO, 160, 120);
        Submarino submarinoAzul = new Submarino(this, Equipo.AZUL, 960, 700);


        barcos.add(acorazadoRojo);
        barcos.add(acorazadoAzul);

        barcos.add(lanchaRoja);
        barcos.add(lanchaAzul);
        barcos.add(lanchaRoja2);
        barcos.add(lanchaAzul2);

        barcos.add(destructorAzul);
        barcos.add(destructorRojo);

        barcos.add(submarinoAzul);
        barcos.add(submarinoRojo);
        class AudioPlayer implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        FileInputStream fileInputStream = new FileInputStream("audio/musica.mp3");
                        Player player = new Player(fileInputStream);
                        player.play();
                    }
                } catch (FileNotFoundException | JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }

        button.setOnAction(
                event -> {
            for (Barco b : barcos) {
                b.start();
            }
            root.getChildren().remove(button);
        });

        AudioPlayer audioPlayer = new AudioPlayer();
        Thread audioThread = new Thread(audioPlayer);
        audioThread.start();

        root.getChildren().addAll(button);




        primaryStage.setTitle("Batalla espacial");
        primaryStage.setScene(new Scene(root, 1280, 960));
        primaryStage.show();
        new Thread(new Runnable() {
            int barcosRojos, barcosAzules;
            @Override
            public void run() {

                while(ganador() == null){
                    barcosRojos= 0;
                    barcosAzules = 0;
                    for(Barco b: getBarcos()){
                        if(b.equipo.equals(Equipo.ROJO) && b.sigueAFlote()){
                            barcosRojos++;
                        }
                        if(b.equipo.equals(Equipo.AZUL) && b.sigueAFlote()){
                            barcosAzules++;
                        }
                    }
                    if(barcosRojos ==0){
                        setGanador(Equipo.AZUL);
                    }
                    if(barcosAzules == 0){
                        setGanador(Equipo.ROJO);
                    }
                }

            }
        }).start();

    }

    private void mostrarGanador(Equipo equipo) {
        Text ganadorText = new Text("El equipo " + equipo + " ha ganado la batalla!");
        ganadorText.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        ganadorText.setFill(Color.WHITE);
        ganadorText.setTextAlignment(TextAlignment.CENTER);
        ganadorText.setLayoutX(100);
        ganadorText.setLayoutY(430);

// Creamos un objeto FadeTransition para hacer desaparecer el texto
        FadeTransition ft = new FadeTransition(Duration.millis(30000), ganadorText);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);

// Creamos un objeto Timeline para cambiar el color del texto
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ganadorText.fillProperty(), Color.WHITE)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ganadorText.fillProperty(), Color.RED)),
                new KeyFrame(Duration.seconds(2), new KeyValue(ganadorText.fillProperty(), Color.YELLOW)),
                new KeyFrame(Duration.seconds(3), new KeyValue(ganadorText.fillProperty(), Color.GREEN))
        );
        timeline.setCycleCount(INDEFINITE);

// Agregamos los objetos a la raíz del Scene
        root.getChildren().add(ganadorText);

// Reproducimos la animación
        timeline.play();
        ft.play();

        File file = new File("audio/goofy.mp3");
        String absolutePath = file.getAbsolutePath();
        Media media = new Media(new File(absolutePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();


    }

    public AnchorPane getRoot(){
        return root;
    }

}