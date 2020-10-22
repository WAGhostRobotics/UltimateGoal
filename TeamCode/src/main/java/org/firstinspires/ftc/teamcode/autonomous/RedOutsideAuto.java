package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Depot", group = "competition")
public class RedOutsideAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        teamColor = TeamColor.BLUE;
        startLocation = StartLocation.DEPOT;
        super.runOpMode();
    }
}