package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.DriveStyle;

@TeleOp(name = "Slow Mode - 0.8", group = "competition")
public class MecanumArcadeTeleOpSlowMode extends TeleOpParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.type = DriveStyle.DriveType.MECANUMARCADE;
        super.slow = 0.8;
        super.kid = false;
        super.runOpMode();
    }
}
