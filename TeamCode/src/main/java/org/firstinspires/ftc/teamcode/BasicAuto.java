package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceReader;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import steelserpents.autolibraries.PathSeg;
import steelserpents.autolibraries.RobotStates;

@Autonomous(name = "BasicAuto", group = "Autonomous")
public class BasicAuto extends OpMode
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

    // Define driving paths as pairs of relative wheel movements in inches (left,right) plus speed %
    // Note: this is a dummy path, and is NOT likely to actually work with YOUR robot.
    private static RobotStates robotState;
    final PathSeg[] mBeaconPath = {
            new PathSeg(  5.0,  5.0, 0.4),
            new PathSeg(  5.0,  -5.0, 0.4),
            new PathSeg(  -5.0,  5.0, 0.4)
    };

    private PathSeg[]           mCurrentPath;     // Array to hold current path`
    private int                 mCurrentSeg;      // Index of the current leg in the current path
    private int                 COUNTS_PER_INCH = 135; // Determined by trial and error measurements.
    private int                 mFrontLeftEncoderTarget;
    private int                 mFrontRightEncoderTarget;
    private int                 mBackLeftEncoderTarget;
    private int                 mBackRightEncoderTarget;

    //DcMotor debris_motor;
//    DcMotorController   driveController;
//    DcMotor             mRightMotor;
//    DcMotor             mLeftMotor;
//    DcMotor             tread_drive;



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
        robotState = RobotStates.START;
    }
    @Override
    public void loop()
    {
        stateMachine();
    }
    public void stateMachine() {
        switch (robotState) {
            case START:
                if (encodersAtZero()) {
                    startPath(mBeaconPath);
                    robotState = RobotStates.TRAVERSE_TOWARD_BEACON;
                }
                break;
            case TRAVERSE_TOWARD_BEACON:
                if (pathComplete()) {
                    robotState = RobotStates.LOOKING_FOR_BEACON;
                } else {
                    // Display Diagnostic data for this state.

                }
                break;
        }
    }
    @Override
    public void stop()
    {
    }



    //--------------------------------------------------------------------------
    // syncEncoders()
    // Load the current encoder values into the Target Values
    // Essentially sync's the software with the hardware
    //--------------------------------------------------------------------------
    void syncEncoders()
    {
        //	get and set the encoder targets
        mFrontLeftEncoderTarget = frontl.getCurrentPosition();
        mFrontRightEncoderTarget = frontr.getCurrentPosition();
        mBackLeftEncoderTarget = backl.getCurrentPosition();
        mBackRightEncoderTarget = backr.getCurrentPosition();
    }
    //--------------------------------------------------------------------------
    // runToPosition ()
    // Set both drive motors to encoder servo mode (requires encoders)
    //--------------------------------------------------------------------------
    public void runToPosition()
    {
        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //--------------------------------------------------------------------------
    // encodersAtZero()
    // Return true if both encoders read zero (or close)
    //--------------------------------------------------------------------------
    boolean encodersAtZero()
    {
        return (
                (Math.abs(getFrontLeftPosition()) < 5) &&
                (Math.abs(getFrontRightPosition()) < 5) &&
                (Math.abs(getBackLeftPosition()) < 5) &&
                (Math.abs(getBackRightPosition()) < 5)
        );
    }
    //--------------------------------------------------------------------------
    // getLeftPosition ()
    // Return Left Encoder count
    //--------------------------------------------------------------------------
    int getFrontLeftPosition()
    {
        return frontl.getCurrentPosition();
    }

    //--------------------------------------------------------------------------
    // getLeftPosition ()
    // Return Left Encoder count
    //--------------------------------------------------------------------------
    int getBackLeftPosition()
    {
        return backl.getCurrentPosition();
    }

    //--------------------------------------------------------------------------
    // getRightPosition ()
    // Return Right Encoder count
    //--------------------------------------------------------------------------
    int getFrontRightPosition()
    {
        return frontr.getCurrentPosition();
    }
    //--------------------------------------------------------------------------
    // getRightPosition ()
    // Return Right Encoder count
    //--------------------------------------------------------------------------
    int getBackRightPosition()
    {
        return backr.getCurrentPosition();
    }
    //--------------------------------------------------------------------------
    // setDriveMode ()
    // Set both drive motors to new mode if they need changing.
    //--------------------------------------------------------------------------
    public void setDriveMode(DcMotor.RunMode mode)
    {
        // Ensure the motors are in the correct mode.
        if (frontl.getMode() != mode)
            frontl.setMode(mode);

        if (frontr.getMode() != mode)
            frontr.setMode(mode);

        if (backl.getMode() != mode)
            backl.setMode(mode);

        if (backr.getMode() != mode)
            backr.setMode(mode);
    }
    //--------------------------------------------------------------------------
    // resetDriveEncoders()
    // Reset both drive motor encoders, and clear current encoder targets.
    //--------------------------------------------------------------------------
    public void resetDriveEncoders()
    {
        setEncoderTarget(0, 0);
        setDriveMode(DcMotor.RunMode.RESET_ENCODERS);
    }
    //--------------------------------------------------------------------------
    // setEncoderTarget( LeftEncoder, RightEncoder);
    // Sets Absolute Encoder Position
    //--------------------------------------------------------------------------
    void setEncoderTarget(int leftEncoder, int rightEncoder)
    {
        frontl.setTargetPosition(mFrontLeftEncoderTarget = leftEncoder);
        frontr.setTargetPosition(mFrontRightEncoderTarget = rightEncoder);
        backl.setTargetPosition(mBackLeftEncoderTarget = leftEncoder);
        backr.setTargetPosition(mBackRightEncoderTarget = rightEncoder);
    }
    /*
            Begin the first leg of the path array that is passed in.
            Calls startSeg() to actually load the encoder targets.
         */
    private void startPath(PathSeg[] path)
    {
        mCurrentPath = path;    // Initialize path array
        mCurrentSeg = 0;
        syncEncoders();        // Lock in the current position
        runToPosition();        // Enable RunToPosition mode
        startSeg();             // Execute the current (first) Leg
    }

    /*
        Starts the current leg of the current path.
        Must call startPath() once before calling this
        Each leg adds the new relative movement onto the running encoder totals.
        By not reading and using the actual encoder values, this avoids accumulating errors.
        Increments the leg number after loading the current encoder targets
     */
    private void startSeg()
    {
        int Left;
        int Right;

        if (mCurrentPath != null)
        {
            // Load up the next motion based on the current segemnt.
            Left  = (int)(mCurrentPath[mCurrentSeg].mLeft * COUNTS_PER_INCH);
            Right = (int)(mCurrentPath[mCurrentSeg].mRight * COUNTS_PER_INCH);
            addEncoderTarget(Left, Right);
            setDriveSpeed(mCurrentPath[mCurrentSeg].mSpeed, mCurrentPath[mCurrentSeg].mSpeed);

            mCurrentSeg++;  // Move index to next segment of path
        }
    }
    /*
        Determines if the current path is complete
        As each segment completes, the next segment is started unless there are no more.
        Returns true if the last leg has completed and the robot is stopped.
     */
    private boolean pathComplete()
    {
        // Wait for this Segement to end and then see what's next.
        if (moveComplete())
        {
            // Start next Segement if there is one.
            if (mCurrentSeg < mCurrentPath.length)
            {
                startSeg();
            }
            else  // Otherwise, stop and return done
            {
                mCurrentPath = null;
                mCurrentSeg = 0;
                setDriveSpeed(0, 0);
                useConstantSpeed();
                return true;
            }
        }
        return false;
    }
    //--------------------------------------------------------------------------
    // moveComplete()
    // Return true if motors have both reached the desired encoder target
    //--------------------------------------------------------------------------
    boolean moveComplete()
    {
        //  return (!mLeftMotor.isBusy() && !mRightMotor.isBusy());
        return ((Math.abs(getFrontLeftPosition() -  mFrontLeftEncoderTarget ) < 10) &&
                (Math.abs(getFrontRightPosition() - mFrontRightEncoderTarget) < 10) &&
                (Math.abs(getBackLeftPosition() -   mBackLeftEncoderTarget  ) < 10) &&
                (Math.abs(getBackRightPosition() -  mBackRightEncoderTarget ) < 10)
        );
    }
    //--------------------------------------------------------------------------
    // useConstantSpeed ()
    // Set both drive motors to constant speed (requires encoders)
    //--------------------------------------------------------------------------
    public void useConstantSpeed()
    {
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }
    //--------------------------------------------------------------------------
    // addEncoderTarget( LeftEncoder, RightEncoder);
    // Sets relative Encoder Position.  Offset current targets with passed data
    //--------------------------------------------------------------------------
    void addEncoderTarget(int leftEncoder, int rightEncoder)
    {
        frontl.setTargetPosition(mFrontLeftEncoderTarget += leftEncoder);
        frontr.setTargetPosition(mFrontRightEncoderTarget += rightEncoder);
        backl.setTargetPosition(mBackLeftEncoderTarget += leftEncoder);
        backr.setTargetPosition(mBackRightEncoderTarget += rightEncoder);
    }

    //--------------------------------------------------------------------------
    // setDriveSpeed( LeftSpeed, RightSpeed);
    //--------------------------------------------------------------------------
    void setDriveSpeed(double leftSpeed, double rightSpeed)
    {
        setDrivePower(leftSpeed, rightSpeed);
    }

    //--------------------------------------------------------------------------
    // setDrivePower( LeftPower, RightPower);
    //--------------------------------------------------------------------------
    void setDrivePower(double leftPower, double rightPower)
    {
        frontl.setPower(Range.clip(leftPower, -1, 1));
        frontr.setPower(Range.clip(rightPower, -1, 1));
        backl.setPower(Range.clip(leftPower, -1, 1));
        backr.setPower(Range.clip(rightPower, -1, 1));
    }
}
