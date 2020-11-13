package org.firstinspires.ftc.teamcode.autonomous;
// taccat3 was here
import org.firstinspires.ftc.teamcode.core.CVLinearOpMode;
import org.firstinspires.ftc.teamcode.core.Sonic;
import org.firstinspires.ftc.teamcode.library.DriveAuto;

public class AutonomousParent extends CVLinearOpMode {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;

    private DriveAuto drivetrain = new DriveAuto(Sonic.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Sonic.init(hardwareMap);

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