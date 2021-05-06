package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "None", group = "testing")
public class None extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.givenPos = RingPosition.NONE;
        super.scan = false;
        teamColor = TeamColor.RED;
        startLocation = StartLocation.OUTSIDE;
        super.runOpMode();
    }
}