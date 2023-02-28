package com.example.batallaespacialpablo;

public class Destructor extends Barco{
    public Destructor(ControlJuego juego, Equipo equipo, double x, double y) {
        super(juego, equipo,"imgs/destructor" + (equipo.equals(Equipo.ROJO) ?"rojo" : "azul") + ".png" ,100, 4, x, y,
                800, 90, 120,100,1000,"disparoDestructor.mp3");
    }
}
