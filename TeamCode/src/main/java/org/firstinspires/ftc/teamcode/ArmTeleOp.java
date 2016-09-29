package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "ArmTeleOp", group = "TeleOp")
public class ArmTeleOp extends OpMode
{
    //Declares
    DcMotor spin;
    DcMotor shoulder;
    DcMotor elbow;
    DcMotor wrist;

    @Override
    public void init()
    {
        //Config
        spin = hardwareMap.dcMotor.get("spin");
        shoulder = hardwareMap.dcMotor.get("shoulder");
        elbow = hardwareMap.dcMotor.get("elbow");
        wrist = hardwareMap.dcMotor.get("wrist");
    }
    @Override
    public void loop()
    {
        //Spin
        if(gamepad1.left_bumper)
        {
            spin.setPower(-0.2);
        }
        else if(gamepad1.right_bumper)
        {
            spin.setPower(0.2);
        }
        else
        {
            spin.setPower(0);
        }

        //Shoulder
        if(gamepad1.dpad_up)
        {
            shoulder.setPower(0.2);
        }
        else if(gamepad1.dpad_down)
        {
            shoulder.setPower(-0.2);
        }
        else
        {
            shoulder.setPower(0);
        }

        //Elbow
        if(gamepad1.y)
        {
            elbow.setPower(0.2);
        }
        else if(gamepad1.a)
        {
            elbow.setPower(-0.2);
        }
        else
        {
            elbow.setPower(0);
        }

        //Wrist
        if(gamepad1.start)
        {
            wrist.setPower(0.2);
        }
        else if(gamepad1.back)
        {
            wrist.setPower(-0.2);
        }
        else
        {
            wrist.setPower(0);
        }
    }
    @Override
    public void stop()
    {
    }
}