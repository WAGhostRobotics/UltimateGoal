package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.DriveStyle;

@TeleOp(name = "Presentation Code", group = "competition")
public class KidCode extends TeleOpParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.type = DriveStyle.DriveType.MECANUMARCADE;
        super.kid = true;
        super.slow = 0.5;
        super.runOpMode();
    }
}
