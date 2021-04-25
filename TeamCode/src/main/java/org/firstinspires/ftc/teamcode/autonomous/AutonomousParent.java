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
    final double POWER = 0.7;
    final double POWER2 = 0.8;
    final double STRAIGHTEN_POWER = 0.30;

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

        if(position == RingPosition.ONE) {
            dropWobbleGoal(false);
        } else {
            dropWobbleGoal(true);
        }

        goToSecondWobbleGoal();

        grabSecondWobbleGoal();

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
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 155, POWER);
                drivetrain2.straighten(180);
                powerShot2();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                dropWobbleGoal(true);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 155, POWER);
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                break;
            case NONE: // A
//                drivetrain2.straighten(180, STRAIGHTEN_POWER);
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
//                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS,  200, POWER);
//                if(Mary.sensors.getBack() < 140) {
//                    drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY,  200, POWER);
//                }
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.6); //
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 155, POWER);
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                break;
        }
    }

    public void moveToDrop1() {
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 45, POWER2);//45
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 37, POWER);//40
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, POWER2);
                if(Mary.sensors.getFront() < 200) {
                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, POWER2);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER2, 0.2); //.4
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.49); //.43
                }
                Mary.claw.out();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.moveAndTurn(77, 0.59);//85  POWER
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 1.47); //1.42
//                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 195, POWER);
//                if(Mary.sensors.getFront() < 200) {
//                    telemetry.addData("WALL: ", Mary.sensors.getFront());
//                    telemetry.update();
//                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, POWER);
//                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.2); //
//                } else {
//                    telemetry.addData("NORMAL: ", Mary.sensors.getFront());
//                    telemetry.update();
//                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.22); //
//                }
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, POWER);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                break;
        }
    }

    public void dropWobbleGoal(boolean wait) {
        Mary.claw.out();
        if(wait) {
            sleep(1000);//800
        }

        Mary.claw.release();
        sleep(100);//350
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-77, 0.59);
        } else if(position == RingPosition.FOUR) {
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.48); //
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, POWER);
            drivetrain2.straighten(0, STRAIGHTEN_POWER);
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 1.15); //
        }

        drivetrain2.straighten(0, STRAIGHTEN_POWER);
        // make distance below greater
        drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 114, POWER);//100
        if(position == RingPosition.FOUR) {
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
        }
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
        drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1.05);
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
        sleep(100);
        Mary.claw.out();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 56, POWER); //58?
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);

        // Front = 25 in
        // Left = 26 in
    }

    public void grabSecondWobbleGoal() {
        Mary.claw.release();
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
        sleep(100);
        drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,45,0.2);//might go too far forward
        sleep(700);
        Mary.claw.grab();
        sleep(400);
        Mary.claw.in();
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
    }

    public void importantStuffToDoAtEnd() {
        switch(position) {
            case FOUR:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.85); //
                break;
            case ONE:
                powerShot2();
                Mary.intake.in();

                //doesn't pick up the second ring, turns weird
//                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, POWER, 0.5);
//                drivetrain2.straighten(0, STRAIGHTEN_POWER);
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER2, 0.3);
//                drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER2, 0.4);//.48
//                drivetrain.move(DriveAuto.MoveDirection.LEFT, POWER2, 0.15);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.42); //
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, POWER2, .60); //.55
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.12); //
                dropWobbleGoal(true);
//                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.4, 0.2);
                drivetrain2.straighten(90, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT,POWER,0.1); //
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.3); //
                break;
            case NONE:
                powerShot2();
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.30); //
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1);
                Mary.claw.out();
                dropWobbleGoal(true);
                sleep(700);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.45); //
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.2); //

                break;
        }
    }

    public void powerShot2(){
        Mary.launcher.power(0.7, .7);

        Mary.intake.inBelt();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 171, POWER);
        drivetrain2.straighten(180, 0.25);
        Mary.intake.stop();
        shoot(1);
        sleep(600);

        Mary.intake.inBelt();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 143, 0.45);
        drivetrain2.straighten(180, 0.25);
        Mary.intake.stop();
        shoot(1);
        sleep(600);

        Mary.intake.inBelt();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 115  , 0.45);
        drivetrain2.straighten(180, 0.25);
        Mary.intake.stop();
        shoot(1);

        Mary.launcher.power(0, 0);

    }

    public void powerShot(int times) {
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
        Mary.intake.inBelt();
//        if(position == RingPosition.FOUR) {
            Mary.launcher.power(0.7, 0.7);
//        } else {
//            Mary.launcher.power(0.7, .65);
//        }
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
        sleep(100);
        if(times > 1) {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.43, 0.17);//.23
        } else {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.43, 0.15);
        }
        for(int i = 0; i < times; i++) {
            if(i == 1) {
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.28, 0.10);//.26
            } else if(i == 2) {
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.26, 0.12);//.25
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