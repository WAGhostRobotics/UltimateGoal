package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Inside", group = "competition")
public class RedInsideAuto extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        teamColor = TeamColor.RED;
        startLocation = StartLocation.INSIDE;
        super.runOpMode();
    }
}