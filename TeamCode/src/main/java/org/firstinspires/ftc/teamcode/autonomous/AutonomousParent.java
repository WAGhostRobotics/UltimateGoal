package org.firstinspires.ftc.teamcode.autonomous;
//novathedog was here
import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveStyle;

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

        driveSensorsRight(30, 0.05);

        if(true)
            return;

        switch (startLocation) {
            case INSIDE:
                break;
            case OUTSIDE:
                moveToDrop();
                break;
        }

        if(true) {
            return;
        }

        dropWobbleGoal();

        moveToShootingPos();



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
//        turn(180);
        sleep(2000);
        if (teamColor == TeamColor.BLUE) {
            drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, .6);
        } else {
            driveSensorsRight(60, .1);
//          drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 1.25);
        }
//        sleep(100);
//        drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.5, 0.1);
//        sleep(100);
        sleep(100);
        switch(position) {
            case FOUR: // C
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 4.2);
                driveSensorsForward(60, 1);
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 0.5);
                driveSensorsLeft(150, .1);
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

    public void driveSensorsForward(int distance, double power) {
        while(opModeIsActive() && (Mary.sensors.getFrontRight() > distance || Mary.sensors.getFrontLeft() > distance)) {
            double motorPower = power;
            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 0, 1, 0);
        }
    }

    public void driveSensorsLeft(int distance, double power) {
        while(opModeIsActive() && Mary.sensors.getLeft() > distance) {
            double motorPower = power;
            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, -1, 0, 0);
        }
    }

    public void driveSensorsRight(int distance, double power) {
        while(opModeIsActive() && Mary.sensors.getRight() > distance) {
            telemetry.addData("Distance: ", Mary.sensors.getRight());
            telemetry.update();
            double motorPower = power;
            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 1, 0, 0);
        }
    }

    public void straighten() {
        double right = Mary.sensors.getFrontRight();
        double left = Mary.sensors.getFrontLeft();
        while(opModeIsActive() && Math.abs(right-left) > 1.5) {
            right = Mary.sensors.getFrontRight();
            left = Mary.sensors.getFrontLeft();
            telemetry.addData("Right: ", right);
            telemetry.addData("Left: ", left);
            telemetry.update();
                if(right-left < 0) {
                    // left too far away, right too close
                    DriveStyle.MecanumArcade(Mary.driveMotors, 0.05, 0, 0, 0.2);
                } else {
                    DriveStyle.MecanumArcade(Mary.driveMotors, -0.05, 0, 0, 0.2);
                }
        }
    }

    public void turn(int degrees) {
        double curr_heading = Mary.imu.getHeading();
        double new_heading = curr_heading + degrees;

        while(opModeIsActive() && Math.abs(Mary.imu.getHeading() - new_heading) > 5) {
            telemetry.addData("Current Heading: ", Mary.imu.getHeading());
            telemetry.addData("New Heading: ", new_heading);
            telemetry.update();
            if(Mary.imu.getHeading() - new_heading < 0) {
                DriveStyle.MecanumArcade(Mary.driveMotors, -0.05, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(Mary.driveMotors, 0.05, 0,0,1);
            }
        }
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