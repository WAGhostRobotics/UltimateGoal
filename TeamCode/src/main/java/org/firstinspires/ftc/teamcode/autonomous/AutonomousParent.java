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
            Kevin.claw.release();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

//        ExtendWobbleClaw();
        Kevin.claw.grab();
        sleep(4000);

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                dropWobbleGoal();
                break;
        }

        RetractWobbleClaw();
    }

    public void dropWobbleGoal() {
        if (teamColor == TeamColor.BLUE) {
            drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, .75);
        } else {
            drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, .75);
        }
        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 6);
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 3);
                if (teamColor == TeamColor.BLUE) {
                    drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1.5);
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 1.5);
                }
                break;
            case NONE: // A
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 5);
                break;
        }
        Kevin.claw.release();
    }

    public void moveToShootingPos() {

    }

    public void ExtendWobbleClaw() {
//        Kevin.claw.grab();
//        sleep(100);
//        Kevin.claw.down();
        Kevin.claw.down();
        sleep(5000);
        Kevin.claw.grab();
        sleep(2000);
    }

    public void RetractWobbleClaw() {
        Kevin.claw.down();
        sleep(3000);
        Kevin.claw.release();
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