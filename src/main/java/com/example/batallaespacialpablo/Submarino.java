package com.example.batallaespacialpablo;

public class Submarino extends Barco{
    public Submarino(ControlJuego juego, Equipo equipo, double x, double y) {
        super(juego, equipo,"imgs/submarino" + (equipo.equals(Equipo.ROJO) ?"rojo" : "azul") + ".png" ,700, 2, x, y,
                300, 300, 120,90,5000,"disparoSubmarino.wav");
    }
}
