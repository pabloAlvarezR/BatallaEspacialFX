package com.example.batallaespacialpablo;

public class Lancha extends Barco{
    public Lancha(ControlJuego juego, Equipo equipo, double x, double y) {
        super(juego, equipo,"imgs/lancha" + (equipo.equals(Equipo.ROJO) ?"roja" : "azul") + ".png" ,800, 60, x, y,
                1000, 50, 100,80,200,"disparoLancha.mp3");
    }
}
