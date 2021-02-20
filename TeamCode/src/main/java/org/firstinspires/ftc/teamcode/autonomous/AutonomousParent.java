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
//        DriveStyle.MecanumArcade(Mary.driveMotors, 1, .5, 0, 0);
//
//        if(true) {
//            return;
//        }

        moveToDrop(); // need to speed up this step

        dropWobbleGoal();

        goToSecondWobbleGoal(); // needs to be very accurate for second step... slow down?

        grabSecondWobbleGoal(); // inaccurate, maybe slow turn to make heading more accurate? would also depend on beginning heading

        shoot(); // This doesn't really work, often only shoots one ring, rarely two, never three... not sure why

        drivetrain2.turn(-180, 1);
        drivetrain2.straighten(0);

        moveToDrop();

        dropWobbleGoal();

        park();
    }

    public void shoot() {
        Mary.launcher.power(0.9, 1);
        Mary.intake.in();
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

    public void moveToDrop() {
        drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 40, 1);
        drivetrain2.straighten(-0);
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.TOWARDS,  30, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.AWAY,  50, 1);
                break;
            case ONE: // B
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.TOWARDS,  90, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.AWAY,   90, 1);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.TOWARDS,  150, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.AWAY,  50, 1);
                break;
        }
        drivetrain2.straighten(0);
    }

    public void dropWobbleGoal() {
        Mary.claw.out();
        sleep(1000);
        Mary.claw.release();
        sleep(500);
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        switch (position) {
            case FOUR:
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.AWAY, 60, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, 1);
                break;
            case ONE:
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.AWAY, 150, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, 1);
                break;
            case NONE:
                drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.AWAY, 200, 1);
                drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, 1);
                break;
        }
       drivetrain2.straighten(0);
        drivetrain2.move(DriveSensor.MoveDirection.FORWARD, DriveSensor.ReferenceDirection.TOWARDS, 48, 1);
        drivetrain2.move(DriveSensor.MoveDirection.LEFT, DriveSensor.ReferenceDirection.AWAY, 75, 1);
        // before turn
        // Front = 48cm
        // Left = 75
    }

    public void grabSecondWobbleGoal() {
        Mary.intake.in();
        Mary.launcher.power(0.9, 1);

        Mary.claw.release();
        Mary.claw.out();

        drivetrain2.turn(180, 0.5);

        sleep(800);
        Mary.claw.grab();
        sleep(500);
        Mary.claw.in();
    }

    public void park() {
        drivetrain2.move(DriveSensor.MoveDirection.BACKWARD, DriveSensor.ReferenceDirection.AWAY, 190, 1);
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