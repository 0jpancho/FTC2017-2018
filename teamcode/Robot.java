package org.firstinspires.ftc.teamcode;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Jancho on 10/18/2017.
 */

public class Robot {

    public DcMotor frontLeft, frontRight, backLeft, backRight, lift;
    public Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight;

    public Servo colorArm;
    public ColorSensor colorSensor;

    //Computes wheel circumference and declares ppr of the Neverest 40 drive motors
    final float wheelCircumference = (float) Math.PI * 4;
    final float ppr = 1120;

    public final int RED = 1;
    public final int BLUE = 2;

    public final int OPEN_SERVOS = 1;
    public final int CLOSE_SERVOS = 2;

    public final int LIFT_LIFT = 1;
    public final int LOWER_LIFT = 2;

    public LinearOpMode l;
    public Telemetry realTelemetry;

    public final double autonDriveSpeed = 0.1;

    private final int NAVX_DIM_I2C_PORT = 0;

    final int motorTolerance = 5;

    /*
    * Function that allows us to create multiple autonomous modes without
    * having to redeclare hardware maps or variables. Allows us to have
    * autonomous functions on standby depending on what the autonomous requires
    * Credit to FTC Team 5998 for structure concept
     */
    public void initialize(LinearOpMode Input, HardwareMap hardwareMap, Telemetry telemetry) {
        l = Input;
        realTelemetry = telemetry;

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        lift = hardwareMap.dcMotor.get("lift");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");

        topLiftRight = hardwareMap.servo.get("topLiftRight");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");

        //colorArm = hardwareMap.servo.get("colorArm");
        //colorSensor = hardwareMap.colorSensor.get("colorSensor");
        /*
        navX = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        */

        realTelemetry.addData("Current Function", "Initialize");
        realTelemetry.update();
        l.idle();
    }
    //Resets the # of encoder counts back to zero
    public void resetDriveEncoders() throws InterruptedException {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        l.idle();

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        realTelemetry.addData("Current Function", "Reset Drive Encoders");
        realTelemetry.update();
        l.idle();
    }

    public void setMotorsZero (){

        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setPower(0);
        backRight.setPower(0);
    }
    /*
    public void resetNavXYaw () {
        navX.zeroYaw();
    }

    /*
     * Drive function that computes inches to encoder counts, so the drive base can
     * move to specific distances.
     */
    public void driveForward(float inches) throws InterruptedException {

        //Computes inches to encoder counts
        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        resetDriveEncoders();
        realTelemetry.addData("Current Function", "Start Driving Forward");
        l.idle();

        while (l.opModeIsActive() && frontLeft.getCurrentPosition() <= newEncoderCounts) {

            frontLeft.setPower(autonDriveSpeed);
            frontRight.setPower(autonDriveSpeed);

            backLeft.setPower(autonDriveSpeed);
            backRight.setPower(autonDriveSpeed);

            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            l.telemetry.addData("Front Left Enc Counts", frontLeft.getCurrentPosition());
            l.telemetry.addData("Front Right Enc Counts", frontRight.getCurrentPosition());
            l.telemetry.addData("Back Left Enc Counts", backLeft.getCurrentPosition());
            l.telemetry.addData("Back Right Enc Counts", backRight.getCurrentPosition());

            realTelemetry.addData("Current Function", "Drive Forward");
            realTelemetry.update();
            l.idle();
        }
        setMotorsZero();

        realTelemetry.addData("Current Function", "Drive Forward Finished");
        realTelemetry.update();
    }

    public void driveBackward (float inches) throws InterruptedException {

        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        resetDriveEncoders();
        realTelemetry.addData("Current Function", "Start Driving Backward");
        l.idle();

        while (l.opModeIsActive() && frontLeft.getCurrentPosition() >= newEncoderCounts) {

            frontLeft.setPower(autonDriveSpeed);
            frontRight.setPower(autonDriveSpeed);

            backLeft.setPower(-autonDriveSpeed);
            backRight.setPower(-autonDriveSpeed);

            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            realTelemetry.addData("Current Function", "Drive Backward");
            realTelemetry.update();
            l.idle();
        }
        setMotorsZero();

        realTelemetry.addData("Current Function", "Drive Backward Finished");
        realTelemetry.update();
    }

    public void strafeLeft (float inches) throws InterruptedException {

        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        resetDriveEncoders();
        realTelemetry.addData("Current Function", "Start Drive");
        l.idle();

        while (l.opModeIsActive() && (Math.abs(frontLeft.getCurrentPosition()) <= newEncoderCounts - motorTolerance
                || Math.abs(frontLeft.getCurrentPosition()) >= newEncoderCounts + motorTolerance)) {

            frontLeft.setPower(autonDriveSpeed);
            frontRight.setPower(autonDriveSpeed);

            backLeft.setPower(autonDriveSpeed);
            backRight.setPower(autonDriveSpeed);

            frontLeft.setTargetPosition(-newEncoderCounts);
            backLeft.setTargetPosition(newEncoderCounts);

            frontRight.setTargetPosition(-newEncoderCounts);
            backRight.setTargetPosition(newEncoderCounts);

            realTelemetry.addData("Current Function", "Strafe Left");
            realTelemetry.update();
            l.idle();
        }
        setMotorsZero();

        realTelemetry.addData("Current Function", "Strafe Left Finished");
        realTelemetry.update();
    }

    public void strafeRight (float inches) throws InterruptedException {

        float numRevolutions = inches / wheelCircumference;

        float encoderCounts = numRevolutions * ppr * 2;
        int newEncoderCounts = (int) encoderCounts;

        resetDriveEncoders();
        realTelemetry.addData("Current Function", "Start Drive");
        l.idle();

        while (l.opModeIsActive() && (frontLeft.getCurrentPosition() <= newEncoderCounts - motorTolerance
                || frontLeft.getCurrentPosition() >= newEncoderCounts + motorTolerance)) {

            frontLeft.setPower(autonDriveSpeed);
            frontRight.setPower(autonDriveSpeed);

            backLeft.setPower(autonDriveSpeed);
            backRight.setPower(autonDriveSpeed);

            frontLeft.setTargetPosition(newEncoderCounts);
            backLeft.setTargetPosition(-newEncoderCounts);

            frontRight.setTargetPosition(newEncoderCounts);
            backRight.setTargetPosition(-newEncoderCounts);

            realTelemetry.addData("Current Function", "Strafe Right");
            realTelemetry.update();
            l.idle();
        }
        setMotorsZero();

        realTelemetry.addData("Current Function", "Strafe Right Finished");
        realTelemetry.update();
    }

    // Function used to rotate to a specific angle in autonomous
    /*public void turnLeft (double degrees) throws InterruptedException {

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        realTelemetry.addData("Current Function", "Start Turning Left");
        realTelemetry.update();

        double power = 0.35;
        double yaw  = navX.getYaw();

        resetNavXYaw();

        while (Math.abs(yaw) >= degrees && l.opModeIsActive()) {

            frontLeft.setPower(power);
            backLeft.setPower(power);

            frontRight.setPower(-power);
            backRight.setPower(-power);

            realTelemetry.addData("Current Function", "Turning Left");
            realTelemetry.addData("Yaw", navX.getYaw());
            realTelemetry.update();

            l.idle();
        }

        setMotorsZero();
        resetNavXYaw();
        resetDriveEncoders();

        realTelemetry.addData("Current Function", "Finished Turning Left");
        realTelemetry.update();

    }

    public void turnRight (double degrees) throws InterruptedException {

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        realTelemetry.addData("Current Function", "Start Turning Right");
        realTelemetry.update();

        double power = 0.15;
        double yaw  = navX.getYaw();

        resetNavXYaw();

        while (Math.abs(yaw) >= degrees&& l.opModeIsActive()) {

            frontLeft.setPower(-power);
            backLeft.setPower(-power);

            frontRight.setPower(power);
            backRight.setPower(power);

            realTelemetry.addData("Current Function", "Turning Right");
            realTelemetry.addData("Yaw", navX.getYaw());
            realTelemetry.update();

            l.idle();
        }

        setMotorsZero();
        resetNavXYaw();
        resetDriveEncoders();

        realTelemetry.addData("Current Function", "Finished Turning Right");
        realTelemetry.update();

    }

    //Sets position of color servo in autonomous
    public void setColorArm(int pos){
        colorArm.setPosition(pos);
    }

    /*
    * Function used in order to push particle in autonomous by
    * using our drive functions. Drive directions are assuming
    * the color sensor is mounted facing right.
     */

    public void moveOnColor(double power, int team, boolean ledEnable) throws InterruptedException{

        realTelemetry.addData("Current Function", "Start Move On Color");
        l.idle();

        colorSensor.enableLed(ledEnable);

        //If Red Alliance, red particle facing sensor, strafe opposite direction
        if (colorSensor.red() > colorSensor.blue() && team == RED && l.opModeIsActive()){
            strafeLeft(8);
            realTelemetry.addData("Current Function", "Move on Color Strafe Left (RED)");
            realTelemetry.update();
            l.idle();
        }

        //If Red Alliance, red particle not detected, strafe right
        else if (colorSensor.red() < colorSensor.blue() && team == RED && l.opModeIsActive()){
            strafeRight(8);
            realTelemetry.addData("Current Function", "Move on Color Strafe Right (RED)");
            realTelemetry.update();
            l.idle();
        }

        //If Blue Alliance, blue particle facing sensor, strafe opposite direction
        else if (colorSensor.blue() > colorSensor.red() && team == BLUE && l.opModeIsActive()){
            strafeLeft(8);
            realTelemetry.addData("Current Function", "Move on Color Strafe Left (BLUE)");
            realTelemetry.update();
            l.idle();
        }

        //If Blue Alliance, blue particle not detected, strafe right
        else if (colorSensor.blue() < colorSensor.red() && team == BLUE && l.opModeIsActive()){
            strafeRight(8);
            realTelemetry.addData("Current Function", "Move on Color Strafe Right (BLUE)");
            realTelemetry.update();
            l.idle();
        }

        colorSensor.enableLed(false);
        realTelemetry.addData("Current Function", "Move on Color Successful");
        realTelemetry.update();

        l.idle();
    }

    //Configurable servo states for the glyph manipulators
    public void moveServos(int state){

        realTelemetry.addData("Current Function", "Start Move Servos");
        realTelemetry.update();

        while(l.opModeIsActive()){

            l.idle();

            switch(state) {

                case OPEN_SERVOS:

                    topLiftLeft.setPosition(0.4);
                    bottomLiftLeft.setPosition(0.6);

                    topLiftRight.setPosition(0.6);
                    bottomLiftRight.setPosition(0.4);

                    realTelemetry.addData("Current Function", "Open Servos");
                    realTelemetry.update();

                    break;

                case CLOSE_SERVOS:

                    topLiftLeft.setPosition(0);
                    bottomLiftLeft.setPosition(1);

                    topLiftRight.setPosition(1);
                    bottomLiftRight.setPosition(0);

                    realTelemetry.addData("Current Function", "Close Servos");
                    realTelemetry.update();

                    break;
            }
        }

        realTelemetry.addData("Current Function", "Finish Moving Servos");
        realTelemetry.update();
    }
    //Configurable states for the lift. Allows it to move to set heights
    public void moveLift(int state){

        realTelemetry.addData("Current Function", "Start Move Lift");
        realTelemetry.update();

        while(l.opModeIsActive()){

            l.idle();

            switch(state){

                case LIFT_LIFT:

                    lift.setPower(0.25);

                    lift.setTargetPosition(3000);

                    realTelemetry.addData("Current Function", "Lift Lift");
                    realTelemetry.update();

                    break;

                case LOWER_LIFT:

                    lift.setPower(0.25);

                    lift.setTargetPosition(0);

                    realTelemetry.addData("Current Function", "Lower Lift");
                    realTelemetry.update();

                    break;
            }
        }

        realTelemetry.addData("Current Function", "Finish Move Lift");
        realTelemetry.update();
    }
    //Sets the position of the lift to a specific amount of encoder counts
    public void changeLiftPos(int pos){

        realTelemetry.addData("Current Function", "Start Changing LIft Position");
        realTelemetry.update();

        lift.setPower(0.5);

        while(l.opModeIsActive()){

            l.idle();

            lift.setTargetPosition(pos);

            realTelemetry.addData("Current Function", "Changing Lift Position");
            realTelemetry.update();
        }

        realTelemetry.addData("Current Function", "Finish Changing Lift Position");
        realTelemetry.update();
    }

    public void waitUntilFinish(DcMotor thing){
        while(thing.getCurrentPosition() < thing.getTargetPosition());
    }
}