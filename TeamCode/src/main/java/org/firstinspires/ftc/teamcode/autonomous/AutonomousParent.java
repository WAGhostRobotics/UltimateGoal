package org.firstinspires.ftc.teamcode.autonomous;
//novathedog was here
import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.DriveAuto;

public class AutonomousParent extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;

    private DriveAuto drivetrain = new DriveAuto(Kevin.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Kevin.init(hardwareMap);

//        vuforiaInit();
        sensingInit();

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
//            Kevin.claw.grab();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                dropWobbleGoal();
                break;
        }
    }

    public void dropWobbleGoal() {
        drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 1.5);

        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 5);
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 3);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1.5);
                break;
            case NONE: // A
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 1.5);
                break;
        }
        Kevin.claw.release();
    }

    public void moveToShootingPos() {

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