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
    RingPosition givenPos = RingPosition.NONE;
    boolean scan = true;
    final double POWER = 0.7;
    final double POWER2 = 0.8;
    final double POWER3 = 0.9;
    final double STRAIGHTEN_POWER = 0.3;

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

        if(!scan) {
            position = givenPos;
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
                drivetrain2.straighten(180, 0.2);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 100, POWER);//163                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 0.4);
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                highGoal();
//                powerShot2();
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, 1, 0.8);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER3);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                dropWobbleGoal(true);
                break;
            case ONE: // B
                drivetrain2.straighten(180, 0.2);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 100, POWER);//163                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 0.35);
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                break;
            case NONE: // A
                drivetrain2.straighten(180, 0.2);
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
//                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS,  200, POWER);
//                if(Mary.sensors.getBack() < 140) {
//                    drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY,  200, POWER);
//                }
//                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.6); //
//                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);

                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 100, POWER);//163                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 0.30);
                drivetrain2.straighten(180, STRAIGHTEN_POWER);
                break;
        }
    }

    public void moveToDrop1() {
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER2);//45
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER3);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 45, POWER);//40
                drivetrain2.straighten(0);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, POWER2);
                if(Mary.sensors.getFront() < 200) {
                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, POWER2);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER2, 0.23); //.4
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.52); //.43
                }
                Mary.claw.out();
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, 0.4);
                drivetrain2.moveAndTurn(79, 0.59);//85  POWER
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);//50
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 1.8); //1.47
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 35, 0.35);
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
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                break;
        }
    }

    public void dropWobbleGoal(boolean wait) {
        Mary.claw.out();
        if(wait) {
            sleep(800);//800
        }

        Mary.claw.release();
        if(wait) {
            sleep(200);
        }
        sleep(100);//350
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-79, 0.59);
        } else if(position == RingPosition.FOUR) {
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.48); //
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, POWER);
            drivetrain2.straighten(0, STRAIGHTEN_POWER);
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER3, 1.07); //1.15
            telemetry.addData("Front distance: ", Mary.sensors.getFront());
            telemetry.update();
        }

        drivetrain2.straighten(0, STRAIGHTEN_POWER);
        // make distance below greater
        if(position==RingPosition.FOUR){
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 106, POWER);//103
        }else{
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 110, POWER);//100
        }
        if(position == RingPosition.FOUR) {
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
        }
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 29, POWER);
        drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1.05);
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
        sleep(100);
        Mary.claw.out();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 54, POWER); //55
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);

        // Front = 25 in
        // Left = 26 in
    }

    public void grabSecondWobbleGoal() {
        Mary.claw.release();
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
        sleep(100);
//        if(position == RingPosition.ONE) {
//            drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 45, 0.2); // might go too far forward
//        } else {
            drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 44, 0.25); // plz work
//        }
        sleep(700);
        Mary.claw.grab();
        sleep(500);
        Mary.claw.in();
        drivetrain2.straighten(180, STRAIGHTEN_POWER);
    }

    public void importantStuffToDoAtEnd() {
        switch(position) {
            case FOUR:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER3, 0.25); // 1.15
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER3, 0.25); // 1.15
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER3, 0.25); // 1.15
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER3, 0.25); // 1.15
                break;
            case ONE:
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY, 115, POWER2);
                highGoal();
//                powerShot2();
                Mary.intake.in();

//                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.42);
//                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, POWER2, .60); //.55
//                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER2, 0.12);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 83, POWER);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 112, POWER);
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 90, POWER);
//                drivetrain2.turn(180, 0.4);
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER2, 1);
                drivetrain.move(DriveAuto.MoveDirection.LEFT, POWER,0.1); //
                dropWobbleGoal(true);
//                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.4, 0.2);
                drivetrain2.straighten(90, STRAIGHTEN_POWER);
                sleep(300);
//                drivetrain.move(DriveAuto.MoveDirection.RIGHT,POWER,0.1); //
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.88);
                break;
            case NONE:
                highGoal();
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.30); //
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.35, 0.55);
                Mary.claw.out();
                dropWobbleGoal(true);
                sleep(700);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.25); //
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.1);
                drivetrain2.straighten(0, STRAIGHTEN_POWER);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.2); //
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.35, 0.25);
                break;
        }
    }

    public void powerShot2(){
        Mary.launcher.power(.77, .77);

//        drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.7, 0.25);
        if(position==RingPosition.FOUR){
            Mary.intake.inBelt();
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            sleep(100);
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 65, 0.6);
            Mary.intake.stop();
            shoot(1);
            sleep(400);

            Mary.intake.inBelt();
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 64, 0.6);
            Mary.intake.stop();
            shoot(1);
            sleep(400);

            Mary.intake.inBelt();
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            drivetrain2.straighten(180, STRAIGHTEN_POWER);//.1
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 82.5, 0.6);//82
            Mary.intake.stop();
            shoot(1);
        }else{
            Mary.intake.inBelt();
            drivetrain2.straighten(180, 0.14);//.1
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 65, 0.6);
            drivetrain2.straighten(180, 0.19);//.1
            sleep(200);
            Mary.intake.stop();
            shoot(1);
            sleep(600);

            Mary.intake.inBelt();
            drivetrain2.straighten(180, 0.14);//.1
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 64, 0.6);
            drivetrain2.straighten(180);
            sleep(200);
            Mary.intake.stop();
            shoot(1);
            sleep(600);

            Mary.intake.inBelt();
            drivetrain2.straighten(180, 0.14);//.1
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 82.5, 0.6);//88  115
            drivetrain2.straighten(180);
            sleep(200);
            Mary.intake.stop();
            shoot(1);
        }


        Mary.launcher.power(0, 0);

    }

    public void highGoal(){
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 69,0.3);
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 69.2,0.3);
        Mary.launcher.power(0.83, 0.83);

        Mary.intake.inBelt();
        drivetrain2.straighten(180);
        drivetrain2.straighten(180);
        sleep(400);
        Mary.intake.stop();

        Mary.intake.inBelt();
        drivetrain2.straighten(180);
        drivetrain2.straighten(180);
        sleep(400);
        Mary.intake.stop();
        shoot(1);

        Mary.intake.inBelt();
        drivetrain2.straighten(180);
        drivetrain2.straighten(180);
        sleep(400);
        Mary.intake.stop();
        shoot(1);

        Mary.intake.inBelt();
        drivetrain2.straighten(180);
        drivetrain2.straighten(180);
        sleep(400);
        Mary.intake.stop();
        shoot(1);

        Mary.launcher.power(0, 0);
    }

    public void powerShot(int times) {
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
        Mary.intake.inBelt();
//        if(position == RingPosition.FOUR) {
            Mary.launcher.power(0.75, 0.7);
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