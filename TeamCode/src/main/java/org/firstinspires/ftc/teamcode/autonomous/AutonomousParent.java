package org.firstinspires.ftc.teamcode.autonomous;

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

        goToSecondWobbleGoal();

        grabSecondWobbleGoal();

        Mary.launcher.power(0.7, .65);
        moveToDrop2();

        importantStuffToDoAtEnd();

    }

    public void shoot(int times) {
        int sleepTime = 700;

        for(int i = 0; i < times; i++) {
            if (i != 0) {
                sleep(sleepTime);
            }
            Mary.launcher.TeleShoot(1);
            sleep(sleepTime);
            Mary.launcher.TeleShoot(0);
        }
    }

    public void moveToDrop2() {
        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 1.6);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY,55,1);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS,70,1);
                powerShot(1);
                drivetrain2.turn(170, 1);
                drivetrain2.straighten(0, 0.6);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 40, 1);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, 1);
                dropWobbleGoal();
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 1.6);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, 1);
                break;
            case NONE: // A
                drivetrain2.straighten(180);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, 1);
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS,  200, 1);
                if(Mary.sensors.getBack() < 140) {
                    drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY,  200, 1);
                }
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 0.8);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, 1);
                break;
        }
    }

    public void moveToDrop1() {
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, 1);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 30, 1);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, 1);
                drivetrain2.straighten(0);
                break;
            case ONE: // B
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 40, 1);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, 1);
                if(Mary.sensors.getFront() < 200) {
                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, 1);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 0.45);
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.5);
                }
                Mary.claw.out();
                drivetrain2.straighten(0);
                drivetrain2.moveAndTurn(95, 1);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, 1);
                drivetrain2.straighten(-0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 195, 1);
                if(Mary.sensors.getFront() < 200) {
                    telemetry.addData("WALL: ", Mary.sensors.getFront());
                    telemetry.update();
                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, 1);
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.2);
                } else {
                    telemetry.addData("NORMAL: ", Mary.sensors.getFront());
                    telemetry.update();
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.4);
                }
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, 1);
                drivetrain2.straighten(0);
                break;
        }
    }

    public void dropWobbleGoal() {
        Mary.claw.out();
        sleep(750);

        Mary.claw.release();
        sleep(350);
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-90, 1);
        } else if(position == RingPosition.FOUR) {
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 1);
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, 1);
            drivetrain2.straighten(0);
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 2.3);
        }

        drivetrain2.straighten(0);
        // make distance below greater
        drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 115, 1);
        if(position == RingPosition.FOUR) {
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 60, 1);
        }
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, 1);
        drivetrain.turn(DriveAuto.TurnDirection.LEFT, 1, 1.65);
        drivetrain2.straighten(180);
        sleep(100);
        Mary.claw.out();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 58, 1); //60?
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, 1);

        // Front = 25 in
        // Left = 26 in
    }

    public void grabSecondWobbleGoal() {
        Mary.claw.release();
        drivetrain2.straighten(180);
        sleep(100);
        drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,50,0.7);//might go too far forward
        sleep(700);
        Mary.claw.grab();
        sleep(400);
        Mary.claw.in();
    }

    public void importantStuffToDoAtEnd() {
        switch(position) {
            case FOUR:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.9);
                break;
            case ONE:
                powerShot(3);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.8);
                Mary.claw.out();
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 1, 1.05);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.25);
                dropWobbleGoal();
                sleep(500);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT,1,0.2);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 1, 0.6);
                break;
            case NONE:
                powerShot(3);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.55);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, 1);
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.5, 2.7);
                Mary.claw.out();
                sleep(400);
                dropWobbleGoal();
                sleep(700);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 0.7);
                drivetrain2.straighten(0);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, 1, 0.4);

                break;
        }
    }

    public void powerShot(int times) {
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, 1);
        Mary.intake.inBelt();
        if(position == RingPosition.FOUR) {
            Mary.launcher.power(0.7, 0.7);
        } else {
            Mary.launcher.power(0.7, .65);
        }
        drivetrain2.straighten(180);
        sleep(100);
        if(times > 1) {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.65, 0.39);
        } else {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.65, 0.3);
        }
        for(int i = 0; i < times; i++) {
            if(i != 0) {
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.5, 0.08);
            }
            shoot(1);
            if(times != 1)
                sleep(800);
        }
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