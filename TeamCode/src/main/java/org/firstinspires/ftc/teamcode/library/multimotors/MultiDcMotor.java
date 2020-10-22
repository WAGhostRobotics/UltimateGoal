package org.firstinspires.ftc.teamcode.library.multimotors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.ArrayList;

public class MultiDcMotor implements DcMotor {

    DcMotor[] motors;

    public MultiDcMotor(DcMotor... motors) {
        this.motors = motors;
    }

    public DcMotor[] getMotorArray() {
        return motors;
    }

    @Override
    public void setDirection(Direction direction) {
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setPower(double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    @Override
    public double getPower() {
        return 0;
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return null;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        for (DcMotor motor : motors) {
            motor.resetDeviceConfigurationForOpMode();
        }
    }

    @Override
    public void close() {
        for (DcMotor motor : motors) {
            motor.close();
        }
    }

    @Override
    public MotorConfigurationType getMotorType() {
        return null;
    }

    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        for (DcMotor motor : motors) {
            motor.setMotorType(motorType);
        }
    }

    @Override
    public DcMotorController getController() {
        return null;
    }

    @Override
    public int getPortNumber() {
        return 0;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return null;
    }

    @Override
    public void setPowerFloat() {
        for (DcMotor motor : motors) {
            motor.setPowerFloat();
        }
    }

    @Override
    public boolean getPowerFloat() {
        return false;
    }

    @Override
    public void setTargetPosition(int position) {
        for (DcMotor motor : motors) {
            motor.setTargetPosition(position);
        }
    }

    @Override
    public int getTargetPosition() {
        return 0;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void setMode(RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    @Override
    public RunMode getMode() {
        return null;
    }
}