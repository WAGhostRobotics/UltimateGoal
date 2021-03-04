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

        moveToDrop1(); // need to speed up this step

        dropWobbleGoal();

        goToSecondWobbleGoal(true);

        Mary.launcher.power(.3, .3);

        grabSecondWobbleGoal(true);

        moveToDrop2();

        switch(position) {
            case FOUR:
                dropWobbleGoal();
                park();
                break;
            case ONE:
                shoot();
                Mary.claw.out();
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.5, 2);
//                drivetrain2.turn(-45, 0.7); // gets stuck here
//                drivetrain2.turn(-45, 0.7); // gets stuck here
                dropWobbleGoal();
                sleep(500);
                break;
            case NONE:
                shoot();
                Mary.claw.out();
//                drivetrain2.turn(-180,1);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, 1);
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, 1, 1.5);
                dropWobbleGoal();
                sleep(1000);
                drivetrain.move(DriveAuto.MoveDirection.LEFT, 1, 0.5);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 1);
                break;
        }

    }

    public void shoot() {
        Mary.intake.inBelt();
        sleep(500);

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
//                drivetrain2.straighten(180);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 2.45);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, 1);
                drivetrain2.straighten(180);
// straighten if needed later
                break;
            case NONE: // A
//                drivetrain2.straighten(-180);
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS,  200, 1);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 0.3);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY,  58, 1);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS,  63, 1);
                break;
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 60, 1);

//                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
//                drivetrain2.straighten(-0);
//                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  200, 1);
//                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
//                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, 1);
//                drivetrain2.straighten(0);
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
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, 1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 2.5);
//                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, 1);
                Mary.claw.out();
                drivetrain2.moveAndTurn(90, 1);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,  195, 1);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.3);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, 1);
//                drivetrain2.straighten(0); // doesnt work very well fix later
                break;
        }
    }

    public void dropWobbleGoal() {
        if(position != RingPosition.ONE){
            Mary.claw.out();
            sleep(1000);
        }
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
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 70, 1);
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

    public void goToSecondWobbleGoal(boolean nakulsMethod) {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-90, 1);
        }
        drivetrain2.straighten(0);
        // make distance below greater
        drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 85, 1);
        Mary.claw.out();
        drivetrain2.turn(180, 1);
        sleep(400);
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 69, 1);
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 75, 1);
        Mary.sleep(700);

        // Front = 25 in
        // Left = 26 in
    }

    public void grabSecondWobbleGoal(boolean nakulsMethod) {
        Mary.claw.release();
        drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,54,0.5);
        sleep(700);

        Mary.claw.grab();
        sleep(400);
        drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5,0.5);
        sleep(400);
        Mary.claw.in();
        sleep(400);
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