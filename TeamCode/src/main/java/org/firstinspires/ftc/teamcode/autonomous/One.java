package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "One", group = "testing")
public class One extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.givenPos = RingPosition.ONE;
        super.scan = false;
        teamColor = TeamColor.RED;
        startLocation = StartLocation.OUTSIDE;
        super.runOpMode();
    }
}