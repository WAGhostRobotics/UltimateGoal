package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Four", group = "testing")
public class Four extends AutonomousParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.givenPos = RingPosition.FOUR;
        super.scan = false;
        teamColor = TeamColor.RED;
        startLocation = StartLocation.OUTSIDE;
        super.runOpMode();
    }
}