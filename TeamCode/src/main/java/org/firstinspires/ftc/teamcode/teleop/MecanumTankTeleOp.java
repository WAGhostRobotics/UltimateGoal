package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.DriveStyle;

@TeleOp(name = "MecanumTank TeleOp", group = "competition")
public class MecanumTankTeleOp extends TeleOpParent {

    @Override
    public void runOpMode() throws InterruptedException {
        super.type = DriveStyle.DriveType.MECANUMTANK;
        super.runOpMode();
    }
}
