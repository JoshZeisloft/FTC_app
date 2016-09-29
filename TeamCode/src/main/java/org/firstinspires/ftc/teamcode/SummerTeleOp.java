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

@TeleOp(name = "SummerTeleOp", group = "TeleOp")
public class SummerTeleOp extends OpMode
{
    DcMotor backl;
    DcMotor backr;
    DcMotor frontl;
    DcMotor frontr;
    Servo servo;
    I2cDevice range;
    I2cDeviceReader rangeReader;
    byte rangeReadings[];
    boolean tog2 = false;
    double speedf = 1;
    double runspeed;
    @Override
    public void init()
    {
        servo = hardwareMap.servo.get("servo");
        backl = hardwareMap.dcMotor.get("backl");
        backr = hardwareMap.dcMotor.get("backr");
        frontl = hardwareMap.dcMotor.get("frontl");
        frontr = hardwareMap.dcMotor.get("frontr");
        frontr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backr.setDirection(DcMotor.Direction.REVERSE);
        frontr.setDirection(DcMotor.Direction.REVERSE);
        I2cDevice range;
        range = hardwareMap.i2cDevice.get("range");
        rangeReader = new I2cDeviceReader(range, new I2cAddr(0x28), 0x04, 2);
        byte rangeReadings[];
    }
    @Override
    public void loop()
    {
        //Drive Speed
        if(gamepad1.start&&!tog2)
        {
            if(speedf==3)
            {
                speedf = 1;
            }
            else
            {
                speedf++;
            }
            tog2 = true;
        }
        else if(!gamepad1.start&&tog2)
        {
            tog2 = false;
        }
        if(speedf == 1)
        {
            runspeed = 1;
        }
        else if(speedf == 2)
        {
            runspeed = 0.6;
        }
        else if(speedf == 3)
        {
            runspeed = 0.3;
        }
        //Drive
        if(gamepad1.dpad_right)
        {
            backl.setPower(-1*runspeed);
            backr.setPower(runspeed);
            frontl.setPower(runspeed);
            frontr.setPower(-1*runspeed);
        }
        else if(gamepad1.dpad_left)
        {
            backl.setPower(runspeed);
            backr.setPower(-1*runspeed);
            frontl.setPower(-1*runspeed);
            frontr.setPower(runspeed);
        }
        else if(gamepad1.dpad_down)
        {
            backl.setPower(runspeed);
            backr.setPower(runspeed);
            frontl.setPower(runspeed);
            frontr.setPower(runspeed);
        }
        else if(gamepad1.dpad_up)
        {
            backl.setPower(-1*runspeed);
            backr.setPower(-1*runspeed);
            frontl.setPower(-1*runspeed);
            frontr.setPower(-1*runspeed);
        }
        else
        {
            //Left Drive
            if (gamepad1.left_bumper)
            {
                backl.setPower(-1*runspeed);
                frontl.setPower(-1*runspeed);
            }
            else if (gamepad1.left_trigger > 0.5f)
            {
                backl.setPower(runspeed);
                frontl.setPower(runspeed);
            }
            else
            {
                if(java.lang.Math.abs(gamepad1.left_stick_y)>0.15)
                {
                    backl.setPower((gamepad1.left_stick_y)/speedf);
                    frontl.setPower((gamepad1.left_stick_y) / speedf);
                }
                else
                {
                    backl.setPower(0);
                    frontl.setPower(0);
                }
            }
            //Right Drive
            if (gamepad1.right_bumper)
            {
                backr.setPower(-1*runspeed);
                frontr.setPower(-1*runspeed);
            }
            else if (gamepad1.right_trigger > 0.5f)
            {
                backr.setPower(runspeed);
                frontr.setPower(runspeed);
            }
            else
            {
                if(java.lang.Math.abs(gamepad1.right_stick_y)>0.15)
                {
                    backr.setPower((gamepad1.right_stick_y)/speedf);
                    frontr.setPower((gamepad1.right_stick_y)/speedf);
                }
                else
                {
                    backr.setPower(0);
                    frontr.setPower(0);
                }
            }
        }
    }
    @Override
    public void stop()
    {
    }
}
