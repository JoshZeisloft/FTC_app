package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceReader;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "Grabber", group = "TeleOp")
public class Grabber extends OpMode
{
    Servo left;
    Servo right;

    @Override
    public void init()
    {
        left = hardwareMap.servo.get("left");
        right = hardwareMap.servo.get("right");

        left.setPosition(0);
        right.setPosition(1);
    }
    @Override
    public void loop()
    {
        if(gamepad1.x)
        {
            left.setPosition(1);
        }
        else
        {
            left.setPosition(0);
        }

        if(gamepad1.b)
        {
            right.setPosition(0);
        }
        else
        {
            right.setPosition(1);
        }
    }
    @Override
    public void stop()
    {

    }
}
