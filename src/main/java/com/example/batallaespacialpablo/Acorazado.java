package com.example.batallaespacialpablo;

public class Acorazado extends Barco{
    public Acorazado(ControlJuego juego, Equipo equipo, double x, double y) {
        super(juego, equipo,"imgs/acorazado" + (equipo.equals(Equipo.ROJO) ?"rojo" : "azul") + ".png" ,150, 3, x, y,
                1000, 180, 80,90,1500,"disparoAcorazado.wav");
    }
}

