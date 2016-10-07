package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceReader;

@TeleOp(name = "RangeTest", group = "TeleOp")
public class RangeTest extends OpMode {

    DcMotor omnil;
    DcMotor omnir;
    DcMotor rubberl;
    DcMotor rubberr;
    Servo servo;
    I2cDevice range;
    I2cDeviceReader rangeReader;
    byte rangeReadings[];
    double degree = 0.8;
    boolean xpressed = false;
    double turnper = 0.024;

    @Override
    public void init()
    {
        servo = hardwareMap.servo.get("servo");
        omnil = hardwareMap.dcMotor.get("omnil");
        omnir = hardwareMap.dcMotor.get("omnir");
        rubberl = hardwareMap.dcMotor.get("rubberl");
        rubberr = hardwareMap.dcMotor.get("rubberr");
        rubberr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        range = hardwareMap.i2cDevice.get("range");
        rangeReader = new I2cDeviceReader(range, new I2cAddr(0x28), 0x04, 2);
        omnil.setDirection(DcMotor.Direction.REVERSE);
        servo.setPosition(0.8);
    }

    @Override
    public void loop()
    {
        rangeReadings = rangeReader.getReadBuffer();
        telemetry.addData("US", (rangeReadings[0] & 0xFF));
        telemetry.addData("ODS", (rangeReadings[1] & 0xFF));
        telemetry.addData("Degree", (degree));

        if(gamepad1.x && !xpressed)
        {
            degree = degree - turnper;
            xpressed = true;
        }
        else if(!gamepad1.x)
        {
            xpressed = false;
        }

        if(degree <= 0.176)
        {
            degree = 0.8;
        }

        if(gamepad1.a)
        {
            degree = 0.5;
        }

        if(gamepad1.b)
        {
            degree = 0.8;
        }

        if(gamepad1.y)
        {
            degree = 0.2;
        }

        servo.setPosition(degree);
    }

    @Override
    public void stop()
    {

    }
}

