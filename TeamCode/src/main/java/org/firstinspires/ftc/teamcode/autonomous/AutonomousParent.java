package org.firstinspires.ftc.teamcode.autonomous;
//novathedog was here
import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.DriveAuto;

public class AutonomousParent extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;

    private DriveAuto drivetrain = new DriveAuto(Mary.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Mary.init(hardwareMap);

//        vuforiaInit();
        sensingInit();

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
            Mary.claw.grab();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                moveToDrop();
                break;
        }

        dropWobbleGoal();

        moveToShootingPos();



        Mary.launcher.power(1, 0.9);
        Mary.launcher.shoot();
        intake();
        Mary.launcher.shoot();
        intake();
        Mary.launcher.shoot();
        Mary.launcher.power(0,0);

        moveToPark();
    }

    public void moveToPark() {
        switch (position) {
            case FOUR:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.4);
                break;
            case ONE:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.21);
                break;
            case NONE:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.3);

                break;
        }
    }

    public void moveToDrop() {
        if (teamColor == TeamColor.BLUE) {
            drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, .6);
        } else {
            drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, .75);
        }
        sleep(100);
        drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.5, 0.1);
        sleep(100);
        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 4.2);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 0.6);
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 3);
                if (teamColor == TeamColor.BLUE) {
                    drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 1.5);
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1.5);
                }
                break;
            case NONE: // A
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 2.4);
                sleep(100);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.5, 0.8);
                break;
        }
    }

    public void dropWobbleGoal() {
        Mary.claw.out();
        sleep(1500);
        Mary.claw.release();
        sleep(500);
        Mary.claw.in();
    }

    public void moveToShootingPos() {
        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 2);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 0.5);
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 1.1);
                if (teamColor == TeamColor.BLUE) {
                    drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1);
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 1);
                }
                break;
            case NONE: // A
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
                drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 0.5);
                break;
        }
        sleep(200);
        drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 1, 1.6);
        sleep(200);
    }

    public void intake() {
        Mary.intake.in();
        sleep(200);
        Mary.intake.stop();
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