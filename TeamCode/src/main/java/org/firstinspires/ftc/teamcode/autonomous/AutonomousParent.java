package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.CVLinearOpMode;
import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveStyle;
import org.firstinspires.ftc.teamcode.library.MecanumTrigMath;

public class AutonomousParent extends CVLinearOpMode {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;

    private DriveAuto drivetrain = new DriveAuto(Kevin.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Kevin.init(hardwareMap);

        vuforiaInit();
        vuforiaActivate();


        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
            vuforiaScan();
            telemetry.addData("last location?: ", retrieveTranslation());
        }

//        vuforiaDeactivate();

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                break;
        }
    }

    enum StartLocation {
        INSIDE,
        OUTSIDE
    }

    enum TeamColor {
        RED,
        BLUE
    }
}