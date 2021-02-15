package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveSensor;

public class AutonomousParent2 extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;

    private final DriveAuto drivetrain = new DriveAuto(Mary.driveMotors);
    private final DriveSensor drivetrain2 = new DriveSensor(Mary.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Mary.init(hardwareMap);

        sensingInit();

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            Mary.claw.grab();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

        moveToDrop();

        dropWobbleGoal();

        moveToShootingPos();

        shoot();

        moveToPark();
    }

    public void shoot() {
        intake();
        sleep(200);
        Mary.launcher.shoot();
        intake();
        sleep(500);
        Mary.launcher.shoot();
        intake();
        sleep(500);
        Mary.launcher.shoot();
        sleep(200);
        Mary.launcher.power(0,0);
    }

    public void moveToPark() {
        drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.2);
    }

    public void moveToDrop() {
        drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 180);
        sleep(200);

        drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 90, .1);
        sleep(100);

        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.MoveDirection.FORWARD, null, 80, 1);
                sleep(100);
                drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, .1);
                sleep(100);
                drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 135);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.MoveDirection.FORWARD, null, 115, 1);
                sleep(100);
                drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, .5);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.MoveDirection.FORWARD, null, 170, 1);
                sleep(100);
                drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, .1);
                sleep(100);
                drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 135);
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
            case NONE: // A
                drivetrain2.turn(DriveSensor.TurnDirection.LEFT, 135);
                sleep(100);
                break;
            case ONE: // B
                break;
        }
        drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, null, 200, 1);
        sleep(100);
        Mary.launcher.power(1, 0.9);
        drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, 0.5);
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