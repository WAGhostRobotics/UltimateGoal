package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Inside", group = "competition")
public class BlueInsideAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        teamColor = TeamColor.BLUE;
        startLocation = StartLocation.INSIDE;
        super.runOpMode();
    }
}