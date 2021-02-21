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

        // test: how to move in 2 directions using DriveStyle
        // start with if x,y,turn values can affect power
        // check if clockwise is positive or negative
//        DriveStyle.MecanumArcade(Mary.driveMotors, 1, 0, -1, -1);
//
//        sleep(3000);
//
//        if(true) {
//            return;
//        }

        moveToDrop1(); // need to speed up this step

        dropWobbleGoal();

        goToSecondWobbleGoal();

        Mary.intake.in();
        Mary.launcher.power(.35, .35);

        grabSecondWobbleGoal();

        moveToDrop2();

        drivetrain2.straighten(-180);
        shoot(); // This doesn't really work, often only shoots one ring, rarely two, never three... not sure why

        drivetrain2.turn(-90, 0.5);

        if(true)return;

        dropWobbleGoal();

        Mary.launcher.power(0.3, 0.3);
        Mary.intake.in();
        park();

        shoot();
    }

    public void shoot() {
        sleep(2000);

        Mary.launcher.TeleShoot(1);
        sleep(1000);
        Mary.launcher.TeleShoot(0);
        sleep(1000);
        Mary.launcher.TeleShoot(1);
        sleep(1000);
        Mary.launcher.TeleShoot(0);
        sleep(1000);
        Mary.launcher.TeleShoot(1);
        sleep(1000);
        Mary.launcher.TeleShoot(0);

        Mary.launcher.power(0,0);
        Mary.intake.stop();
    }

    public void moveToDrop2() {
        switch(position) {
            case FOUR: // C
                drivetrain2.turn(-165, 1);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  30, 1);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  30, 1);
                drivetrain2.straighten(0);
                break;
            case ONE: // B
                drivetrain2.straighten(180);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 2.5);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 60, 1);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  200, 1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, 1);
                drivetrain2.straighten(0);
                break;
        }
    }

    public void moveToDrop1() {
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  30, 1);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  30, 1);
                drivetrain2.straighten(0);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 40, 1);
                drivetrain2.straighten(-0);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 2.5);
//                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, 1);
                drivetrain2.moveAndTurn(90, 1);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  200, 1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, 1);
                drivetrain2.straighten(0);
                break;
        }
    }

    public void dropWobbleGoal() {
        Mary.claw.out();
        sleep(1000);
        Mary.claw.release();
        sleep(500);
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-90, 1);
        }
        drivetrain2.straighten(0);
        drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 48, 1);
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 65, 1);
        // before turn
        // Front = 48cm
        // Left = 75
    }

    public void grabSecondWobbleGoal() {
        Mary.claw.release();
        Mary.claw.out();

        drivetrain2.turn(180, 0.5);

        sleep(700);

        Mary.claw.grab();
        sleep(400);
        Mary.claw.in();
    }

    public void park() {
        switch(position) {
            case FOUR:
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 180, 1);
                break;
            case ONE:
                drivetrain2.moveAndTurn(-90, 1);
                break;
            case NONE:
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 80, 1);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 180, 1);
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