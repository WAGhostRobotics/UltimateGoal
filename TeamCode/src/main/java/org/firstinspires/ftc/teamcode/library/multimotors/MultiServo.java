package org.firstinspires.ftc.teamcode.library.multimotors;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class MultiServo implements Servo {
    Servo[] servos;

    public MultiServo(Servo... servos) {
        this.servos = servos;
    }

    public Servo[] getServoArray() {
        return servos;
    }

    @Override
    public ServoController getController() {
        return null;
    }

    @Override
    public int getPortNumber() {
        return 0;
    }

    @Override
    public void setDirection(Direction direction) {
        for (Servo servo : servos) {
            servo.setDirection(direction);
        }
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setPosition(double position) {
        for (Servo servo : servos) {
            servo.setPosition(position);
        }
    }

    @Override
    public double getPosition() {
        return 0;
    }

    @Override
    public void scaleRange(double min, double max) {
        for (Servo servo : servos) {
            servo.scaleRange(min, max);
        }
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
        for (Servo servo : servos) {
            servo.resetDeviceConfigurationForOpMode();
        }
    }

    @Override
    public void close() {
        for (Servo servo : servos) {
            servo.close();
        }
    }
}