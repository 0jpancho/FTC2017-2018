package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jancho on 10/18/2017.
 */
@TeleOp(name = "Main Teleop", group = "Sctuff")
public class TeleopMain extends OpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight, lift, relic, leftRoller, rightRoller;
    Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight, relicLeft, relicRight;
    CRServo leftElevator, rightElevator;

    final double driveSpeed = 1;
    final double turnSpeed = 0.5;
    final double strafeSpeed = 1;

    final double liftSpeed = 0.5;

    @Override
    public void init(){
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        lift = hardwareMap.dcMotor.get("lift");

        leftRoller = hardwareMap.dcMotor.get("leftRoller");
        rightRoller = hardwareMap.dcMotor.get("rightRoller");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");

        topLiftRight = hardwareMap.servo.get("topLiftRight");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");

        leftElevator = hardwareMap.crservo.get("leftElevator");
        rightElevator = hardwareMap.crservo.get("rightElevator");

        relic = hardwareMap.dcMotor.get("relic");
        relicLeft = hardwareMap.servo.get("relicLeft");
        relicRight = hardwareMap.servo.get("relicRight");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relic.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop(){

        /*forward*/
        if (gamepad1.left_stick_y < -0.25) {
            frontLeft.setPower(-driveSpeed);
            backLeft.setPower(-driveSpeed);

            frontRight.setPower(driveSpeed);
            backRight.setPower(driveSpeed);

            telemetry.addData("Drive Direction", "Forward");
        }

        /*backward*/
        else if (gamepad1.left_stick_y > 0.25) {
            frontLeft.setPower(driveSpeed);
            backLeft.setPower(driveSpeed);

            frontRight.setPower(-driveSpeed);
            backRight.setPower(-driveSpeed);

            telemetry.addData("Drive Direction", "Backward");
        }

        /*strafe left*/
        else if (gamepad1.left_stick_x < -0.25) {
            frontLeft.setPower(strafeSpeed);
            frontRight.setPower(strafeSpeed);

            backLeft.setPower(-strafeSpeed);
            backRight.setPower(-strafeSpeed);

            telemetry.addData("Drive Direction", "Strafe Left");
        }

        /*strafe right*/
        else if (gamepad1.left_stick_x > 0.25) {
            frontLeft.setPower(-strafeSpeed);
            frontRight.setPower(-strafeSpeed);

            backLeft.setPower(strafeSpeed);
            backRight.setPower(strafeSpeed);

            telemetry.addData("Drive Direction", "Strafe Right");
        }
        /*rotate clockwise*/
        else if (gamepad1.b) {
            frontLeft.setPower(-turnSpeed);
            frontRight.setPower(-turnSpeed);

            backLeft.setPower(-turnSpeed);
            backRight.setPower(-turnSpeed);

            telemetry.addData("Drive Direction", "Rotate Right");
        }
        /*rotate counterclockwise*/
        else if (gamepad1.x)
        {
            frontLeft.setPower(turnSpeed);
            frontRight.setPower(turnSpeed);

            backLeft.setPower(turnSpeed);
            backRight.setPower(turnSpeed);

            telemetry.addData("Drive Direction", "Rotate Left");
        }

        /*
         * Deadzone for drive base. disallows movement of the drive train unless
         *  a value of 0.25 is exceeded by the joystick in any direction
         */
        else if (gamepad1.left_stick_y < 0.25 && gamepad1.left_stick_y > -0.25 &&
                gamepad1.left_stick_x < 0.25 && gamepad1.left_stick_x > -0.25) {

            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            telemetry.addData("Drive Direction", "Stop Motors (Deadzone)");
        }

        /*
         * Diagonal drive code. Allows us to drive diagonally relative to our relic manipulator.
         * Aids the driver in lining up our robot to pick up the relic during teleop.
         */
        else if(gamepad1.dpad_up)
        {
            frontLeft.setPower(1);
            backRight.setPower(-1);
        }
        else if(gamepad1.dpad_down)
        {
            frontLeft.setPower(-1);
            backRight.setPower(1);
        }

        //Controls the lift motor for the glyph manipulator
        if (gamepad2.left_stick_y < -0.25)
        {
            lift.setPower(liftSpeed);
        }

        else if (gamepad2.left_stick_y > 0.25)
        {
            lift.setPower(-liftSpeed);
        }

        else
        {
            lift.setPower(0);
        }

    //Left glyph arm control
    if (gamepad2.left_bumper) {
        topLiftLeft.setPosition(0);
        bottomLiftLeft.setPosition(1);
    }

    else {
        topLiftLeft.setPosition(0.4);
        bottomLiftLeft.setPosition(0.6);
    }

    //Right glyph arm control
    if (gamepad2.right_bumper) {
        topLiftRight.setPosition(1);
        bottomLiftRight.setPosition(0);
    }

    else {
        topLiftRight.setPosition(0.6);
        bottomLiftRight.setPosition(0.4);
    }


    //Control of the motor on the relic manipulator
    if (gamepad1.right_stick_y < -0.25)
    {
        relic.setPower(1);
    }

    else if (gamepad1.right_stick_y > 0.25)
    {
        relic.setPower(-1);
    }

    else
    {
        relic.setPower(0);
    }

    //Control of the servos on the relic manipulator that pick up the relic itself
    if (gamepad1.left_bumper) {
        relicLeft.setPosition(1);
        relicRight.setPosition(0);
    }
    else if (gamepad1.right_bumper) {
        relicLeft.setPosition(0);
        relicRight.setPosition(1);
    }

    //Intake roller control
    if (gamepad2.right_stick_y < -0.25)
    {
        leftRoller.setPower(-0.75);
        rightRoller.setPower(0.75);
    }

    else if (gamepad2.right_stick_y > 0.25)
    {
        leftRoller.setPower(0.75);
        rightRoller.setPower(-0.75);
    }

    else
    {
        leftRoller.setPower(0);
        rightRoller.setPower(0);
    }
    //Elevator omni roller control
    if(gamepad2.left_trigger > 0.25)
    {
        leftElevator.setPower(1);
        rightElevator.setPower(-1);
    }

    else if(gamepad2.right_trigger > 0.25)
    {
        leftElevator.setPower(-1);
        rightElevator.setPower(1);
    }

    else
    {
        leftElevator.setPower(0);
        rightElevator.setPower(0);
    }
}}
