package org.firstinspires.ftc.teamcode.autonomous;
// taccat was here
import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Sonic;
import org.firstinspires.ftc.teamcode.library.DriveAuto;

public class AutonomousParent extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;

    private DriveAuto drivetrain = new DriveAuto(Sonic.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Sonic.init(hardwareMap);

//        vuforiaInit();
        sensingInit();

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
//            vuforiaScan();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

//        vuforiaDeactivate();

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                break;
        }
    }

    public void dropWobbleGoal(RingPosition position) {
        switch(position) {
            case FOUR:
                break;
            case ONE:
                break;
            case NONE:
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