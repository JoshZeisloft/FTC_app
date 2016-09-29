package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceReader;
import android.*;
import android.content.Context;
import com.*;
import java.*;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Scanner;

@Autonomous(name = "RangeAuto", group = "Autonomous")
public class RangeAuto extends LinearOpMode
{

    DcMotor omnil;
    DcMotor omnir;
    DcMotor rubberl;
    DcMotor rubberr;
    Servo servo;
    I2cDevice range;
    I2cDeviceReader rangeReader;
    byte rangeReadings[];
    double degree = 0;
    double turnper = 0.024;
    double[] array = new double[100];
    int index = 0;

    @Override
    public void runOpMode() throws InterruptedException
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
        waitForStart();

        while (opModeIsActive())
        {
            rangeReadings = rangeReader.getReadBuffer();
            telemetry.addData("00", array[0]);
            telemetry.addData("01", array[1]);
            telemetry.addData("02", array[2]);
            telemetry.addData("03", array[3]);
            telemetry.addData("04", array[4]);
            telemetry.addData("05", array[5]);
            telemetry.addData("06", array[6]);
            telemetry.addData("07", array[7]);
            telemetry.addData("08", array[8]);
            telemetry.addData("09", array[9]);
            telemetry.addData("10", array[10]);
            telemetry.addData("11", array[11]);
            telemetry.addData("12", array[12]);
            telemetry.addData("13", array[13]);
            telemetry.addData("14", array[14]);
            telemetry.addData("15", array[15]);
            telemetry.addData("16", array[16]);
            telemetry.addData("17", array[17]);
            telemetry.addData("18", array[18]);
            telemetry.addData("19", array[19]);
            telemetry.addData("20", array[20]);
            telemetry.addData("21", array[21]);
            telemetry.addData("22", array[22]);
            telemetry.addData("23", array[23]);
            telemetry.addData("24", array[24]);
            telemetry.addData("25", array[25]);
            telemetry.addData("26", array[26]);
            /*telemetry.addData("27", array[27]);
            telemetry.addData("28", array[28]);
            telemetry.addData("29", array[29]);
            telemetry.addData("30", array[30]);
            telemetry.addData("31", array[31]);
            telemetry.addData("32", array[32]);
            telemetry.addData("33", array[33]);
            telemetry.addData("34", array[34]);
            telemetry.addData("35", array[35]);
            telemetry.addData("36", array[36]);
            telemetry.addData("37", array[37]);
            telemetry.addData("38", array[38]);
            telemetry.addData("39", array[39]);
            telemetry.addData("40", array[40]);
            telemetry.addData("41", array[41]);
            telemetry.addData("42", array[42]);
            telemetry.addData("43", array[43]);
            telemetry.addData("44", array[44]);
            telemetry.addData("45", array[45]);
            telemetry.addData("46", array[46]);
            telemetry.addData("47", array[47]);
            telemetry.addData("48", array[48]);
            telemetry.addData("49", array[49]);
            telemetry.addData("50", array[50]);
            telemetry.addData("51", array[51]);
            telemetry.addData("52", array[52]);*/
            sleep(200);

            index ++;
            array[index] = rangeReadings[0] & 0xFF;
            degree = 0.8-(turnper*index);
            servo.setPosition(degree);

            if(index > 26)
            {
                index = 0;
            }
        }
    }
}
