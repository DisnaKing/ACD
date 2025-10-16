package com.ieseljust.practicaunitat1;

import java.io.Serializable;

public class pilot implements Serializable {
    String type;
    String name;
    int SpeedGround;
    int SpeedWater;
    int SpeedAir;
    int SpeedAntiGravity;
    int Acceleration;
    int Weight;
    int HandlingGround;
    int HandlingWater;
    int HandlingAir;
    int HandlingAntiGravity;
    int Traction;
    int MiniTurbo;

    public pilot(String type, String name, int SpeedGround, int SpeedWater, int SpeedAir, int SpeedAntiGravity, int Acceleration, int Weight, int HandlingGround, int HandlingWater, int HandlingAir, int HandlingAntiGravity, int Traction, int MiniTurbo) {
        this.type = type;
        this.name = name;
        this.SpeedGround = SpeedGround;
        this.SpeedWater = SpeedWater;
        this.SpeedAir = SpeedAir;
        this.SpeedAntiGravity = SpeedAntiGravity;
        this.Acceleration = Acceleration;
        this.Weight = Weight;
        this.HandlingGround = HandlingGround;
        this.HandlingWater = HandlingWater;
        this.HandlingAir = HandlingAir;
        this.HandlingAntiGravity = HandlingAntiGravity;
        this.Traction = Traction;
        this.MiniTurbo = MiniTurbo;
    }

}
