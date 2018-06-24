package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jancho on 1/21/2018.
 */
@Autonomous(name = "Blue Alliance",  group = "Sctuff")
public class Blue_Alliance extends LinearOpMode {

    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public Servo colorArm, colorPivot;
    public ColorSensor colorSensor;


    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        colorArm = hardwareMap.servo.get("colorArm");
        colorPivot = hardwareMap.servo.get("colorPivot");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        waitForStart();

        //Sets the jewel manipulator to center position with the arm extended
        while (getRuntime() >= 0 && getRuntime() <= 2 && opModeIsActive()) {
            colorArm.setPosition(0);
            colorPivot.setPosition(0.5);

            idle();
        }

        //Runs a function which checks the color of the jewel and moves based on it
        while (getRuntime() >= 2 && getRuntime() <= 4 && opModeIsActive()) {
            blueAllianceColor();

            idle();
        }

        //Resets the position of the jewel manipulator
        while (getRuntime() >= 4 && getRuntime() <= 6 && opModeIsActive()) {
            colorPivot.setPosition(0.5);
            colorArm.setPosition(0.9);

            idle();
        }

        //Drives the robot to the safezone
        while (getRuntime() >= 6 && getRuntime() <= 8 && opModeIsActive()) {

            frontLeft.setPower(-0.75);
            backLeft.setPower(-0.75);

            frontRight.setPower(0.75);
            backRight.setPower(0.75);

            idle();
        }

        //Drives the robot backwards in order to not score be in contact with a glyph in the cryptobox
        while (getRuntime() >= 8 && getRuntime() <= 9 && opModeIsActive()) {

            frontLeft.setPower(0.25);
            backLeft.setPower(0.25);

            frontRight.setPower(-0.25);
            backRight.setPower(-0.25);

            idle();
        }
    }

    //Function that checks the color of the jewel, assuming you are the Blue Alliance
    public void blueAllianceColor () {

        //If a blue jewel is detected, pivot away from it
        if (colorSensor.blue() - colorSensor.red() > 30) {
            colorPivot.setPosition(0);

            idle();
        }
        //If a red jewel is detected, pivot towards it
        else if (colorSensor.red() - colorSensor.blue() > 70) {
            colorPivot.setPosition(1);

            idle();
        }
    }

    //Function that checks the color of the jewel, assuming you are the Red Alliance
    public void redAlianceColor () {

        //If a blue jewel is detected, pivot towards it
        if (colorSensor.blue() - colorSensor.red() > 30) {
            colorPivot.setPosition(1);
        }

        //If a red jewel is detected, pivot away from it
        else if (colorSensor.red() - colorSensor.blue() > 70) {
            colorPivot.setPosition(0);
        }
    }
}