package org.firstinspires.ftc.teamcode.autonomous;
//novathedog was here

import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveSensor;

public class AutonomousParent extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;

    private DriveAuto drivetrain = new DriveAuto(Mary.driveMotors);
    private DriveSensor drivetrain2 = new DriveSensor(Mary.driveMotors);

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

        getSecondWobbleGoal();

        if(true) {
            return;
        }


        moveToShootingPos();

        shoot();

        moveToPark();
    }

    public void shoot() {
        Mary.launcher.power(1, 0.9);
        sleep(500);
        Mary.launcher.shoot();
        intake();
        sleep(500);
        Mary.launcher.shoot();
        intake();
        sleep(500);
        Mary.launcher.shoot();
        Mary.launcher.power(0,0);
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
        drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 180);
        sleep(100);
        drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 40, 1);
        sleep(200);
        switch(position) {
            case FOUR: // C
                drivetrain2.straighten(-180);
                drivetrain2.move(DriveSensor.MoveDirection.FORWARD, null,  30, 1);
                sleep(100);
                drivetrain2.move(DriveSensor.MoveDirection.RIGHT, DriveSensor.ReferenceDirection.AWAY,  50, 1);
                sleep(100);
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 3);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1.5);
                break;
            case NONE: // A
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 2.4);
                sleep(100);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.5, 0.8);
                break;
        }
    }

    public void dropWobbleGoal() {
        if(position == RingPosition.FOUR || position == RingPosition.NONE) {
            telemetry.addData("Current Heading: ", Mary.imu.getHeading());
            telemetry.addData("New Heading: ", Mary.imu.getHeading() + 180);
            telemetry.update();
            drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 180, .5);
        }
        Mary.claw.out();
        sleep(1500);
        Mary.claw.release();
        sleep(500);
        Mary.claw.in();

        if(position == RingPosition.ONE) {
            drivetrain2.turn(DriveSensor.TurnDirection.RIGHT, 180);
        }

        telemetry.addData("THE HEADING WE WANT: ", Mary.imu.getHeading());
        telemetry.update();

        drivetrain2.straighten(180);
    }

    public void getSecondWobbleGoal() {
        drivetrain2.move(DriveSensor.MoveDirection.FORWARD, null, 60, 1);
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