package com.example.pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javax.swing.*;
public class VistaJuego extends BorderPane {
    private float raquetaIzquierdaDesplazamiento = 0;
    private float raquetaDerechaDesplazamiento = 0;
    private Circle pelota;
    private Rectangle raquetaIzquierda;
    private Rectangle raquetaDerecha;
    private Rectangle paredSuperior;
    private Rectangle paredInferior;
    private Rectangle porteriaDerecha;
    private Rectangle porteriaIzquierda;
    private float velocidad = 1;
    private float desplazamientoPelotaX = 1;
    private float desplazamientoPelotaY = 1;
    private StackPane pista;
    private int jugadorDerecha = 0 ;
    private int jugadorIzquierda = 0;
    private Button botonInicio;
    private Label jugadorD;
    private Label jugadorI;
    private Label titulo;

    public VistaJuego()
    {
        crearVista();
    }
    private void crearVista()
    {
        pista = new StackPane();
        pista.setLayoutX(600);
        pista.setLayoutY(500);

        pelota = new Circle(7, Color.BLACK);

        botonInicio = new Button("Go");
        botonInicio.setOnMouseClicked(event -> {pulsarBoton(); botonInicio.setVisible(false);});

        raquetaIzquierda = new Rectangle();

        raquetaIzquierda.widthProperty().bind(this.widthProperty().divide(25));
        raquetaIzquierda.heightProperty().bind(this.heightProperty().divide(5));
        raquetaIzquierda.translateXProperty().bind(this.widthProperty().divide(-2.3));

        raquetaDerecha = new Rectangle();

        raquetaDerecha.widthProperty().bind(this.widthProperty().divide(25));
        raquetaDerecha.heightProperty().bind(this.heightProperty().divide(5));
        raquetaDerecha.translateXProperty().bind(this.widthProperty().divide(2.3));

        porteriaDerecha = new Rectangle();
        porteriaDerecha.setVisible(false);

        porteriaDerecha.widthProperty().bind(this.widthProperty().divide(30));
        porteriaDerecha.heightProperty().bind(this.heightProperty());
        porteriaDerecha.translateXProperty().bind(this.widthProperty().divide(2));

        porteriaIzquierda = new Rectangle();
        porteriaIzquierda.setVisible(false);

        porteriaIzquierda.widthProperty().bind(this.widthProperty().divide(30));
        porteriaIzquierda.heightProperty().bind(this.heightProperty());
        porteriaIzquierda.translateXProperty().bind(this.widthProperty().divide(-2));

        paredSuperior = new Rectangle();

        paredSuperior.widthProperty().bind(this.widthProperty().subtract(20));
        paredSuperior.heightProperty().bind(this.heightProperty().divide(25));
        paredSuperior.translateYProperty().bind(this.heightProperty().divide(-2.5));

        paredInferior = new Rectangle();

        paredInferior.widthProperty().bind(this.widthProperty().subtract(20));
        paredInferior.heightProperty().bind(this.heightProperty().divide(25));
        paredInferior.translateYProperty().bind(this.heightProperty().divide(2.5));

        pista.setFocusTraversable(true);
        pista.requestFocus();
        pista.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q)
                raquetaIzquierdaDesplazamiento = -1;
            else if (e.getCode() == KeyCode.A)
                raquetaIzquierdaDesplazamiento = 1;
            else if (e.getCode() == KeyCode.P)
                raquetaDerechaDesplazamiento = -1;
            else if (e.getCode() == KeyCode.L)
                raquetaDerechaDesplazamiento = +1;
        });
        pista.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q)
                raquetaIzquierdaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.A)
                raquetaIzquierdaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.P)
                raquetaDerechaDesplazamiento = 0;
            else if (e.getCode() == KeyCode.L)
                raquetaDerechaDesplazamiento = 0;
        });

        titulo = new Label();
        titulo.setFont(Font.font(22));
        titulo.setText("PONG");

        titulo.translateYProperty().bind(this.heightProperty().divide(-2.2));

        jugadorD = new Label();
        jugadorD.setFont(Font.font(22));
        jugadorD.setText("Jugador D: " + jugadorIzquierda);
        jugadorD.translateXProperty().bind(this.widthProperty().divide(3));
        jugadorD.translateYProperty().bind(this.heightProperty().divide(2.1));

        jugadorI = new Label();
        jugadorI.setFont(Font.font(22));
        jugadorI.setText("Jugador I: " + jugadorDerecha);
        jugadorI.translateXProperty().bind(this.widthProperty().divide(-3));
        jugadorI.translateYProperty().bind(this.heightProperty().divide(2.1));

        pista.getChildren().addAll(paredSuperior, paredInferior, raquetaIzquierda, raquetaDerecha,
                pelota, porteriaDerecha, porteriaIzquierda, botonInicio, jugadorD, jugadorI, titulo);
        this.setCenter(pista);
    }
    public void lanzarJuego()
    {
        Timeline animacion = new Timeline(new KeyFrame(Duration.seconds(0.017), t -> {
            moverPelota();
            moverRaqueta();
            comprobarColisiones();
        }));
        animacion.setCycleCount(Timeline.INDEFINITE);
        animacion.play();
    }
    private void comprobarColisiones()
    {
        var altoRaqueta= Math.round(raquetaDerecha.getHeight() / 2);
        var anchoRaqueta= Math.round(raquetaIzquierda.getWidth() / 2);

        if ((pelota.getTranslateX() == Math.round(raquetaDerecha.getTranslateX() - anchoRaqueta)) &&
                ((pelota.getTranslateY() <= raquetaDerecha.getTranslateY() + altoRaqueta)) &&
                pelota.getTranslateY() >= raquetaDerecha.getTranslateY() - altoRaqueta)
        {
            desplazamientoPelotaX = -1;
        }

        if ((pelota.getTranslateX() == Math.round(raquetaIzquierda.getTranslateX() + anchoRaqueta)) &&
                ((pelota.getTranslateY() <= raquetaIzquierda.getTranslateY() + altoRaqueta)) &&
                pelota.getTranslateY() >= raquetaIzquierda.getTranslateY() - altoRaqueta)
        {
            desplazamientoPelotaX = 1;
        }

        if (pelota.getBoundsInParent().intersects(paredInferior.getBoundsInParent()) && pelota.getBoundsInParent()
                .intersects(paredInferior.getBoundsInParent()))
        {
            desplazamientoPelotaY *= -1;
        }

        if (pelota.getBoundsInParent().intersects(paredSuperior.getBoundsInParent()) && pelota.getBoundsInParent()
                .intersects(paredSuperior.getBoundsInParent()))
        {
            desplazamientoPelotaY *= -1;
        }

        if (pelota.getBoundsInParent().intersects(porteriaDerecha.getBoundsInParent()))
        {
            jugadorDerecha++;
            crearVista();
            lanzarJuego();
        }

        if (pelota.getBoundsInParent().intersects(porteriaIzquierda.getBoundsInParent()))
        {
            jugadorIzquierda++;
            crearVista();
            lanzarJuego();
        }

        if (jugadorIzquierda==5)
        {
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.showMessageDialog(null, "JugadorI wins", "Game over",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("JugadorI gana");
            System.exit(0);
        }

        if (jugadorDerecha==5)
        {
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.showMessageDialog(null, "JugadorD wins", "Game over",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("JugadorD gana");
            System.exit(0);
        }
    }
    private void pulsarBoton()
    {
        crearVista();
        lanzarJuego();
    }
    private void moverPelota()
    {
        pelota.setTranslateX(pelota.getTranslateX() + desplazamientoPelotaX * velocidad);
        pelota.setTranslateY(pelota.getTranslateY() + desplazamientoPelotaY * velocidad);
    }
    private void moverRaqueta()
    {
        raquetaIzquierda.setTranslateY(raquetaIzquierda.getTranslateY() + raquetaIzquierdaDesplazamiento);
        raquetaDerecha.setTranslateY(raquetaDerecha.getTranslateY() + raquetaDerechaDesplazamiento);
    }
}