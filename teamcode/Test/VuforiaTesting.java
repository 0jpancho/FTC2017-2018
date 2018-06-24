package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Vuforia Testing", group ="Concept")
public class VuforiaTesting extends LinearOpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight, lift;
    Servo topLiftLeft, bottomLiftLeft, topLiftRight, bottomLiftRight, colorArm, colorPivot;
    ColorSensor colorSensor;

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        colorArm = hardwareMap.servo.get("colorArm");
        colorPivot = hardwareMap.servo.get("colorPivot");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        lift = hardwareMap.dcMotor.get("lift");

        topLiftLeft = hardwareMap.servo.get("topLiftLeft");
        bottomLiftLeft = hardwareMap.servo.get("bottomLiftLeft");

        topLiftRight = hardwareMap.servo.get("topLiftRight");
        bottomLiftRight = hardwareMap.servo.get("bottomLiftRight");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATTrTZj/////AAAAGcuqIv1tG0l2rtJHyHTkjMQg/aogzK1jRT4UF+1z1+ElhoWZYNSXXslT5PmhJZYURIBfaKiLT6AiZ58eOj9E6UMI16UXSek86LW0GK0pvVzCfX7N594z4UH+c7H4MCnnB0urwR25TRXbn/WE65bKMJkj3DJps2yV+5e7gwvb6ccHrRTd+BVUvgbeCA/u1tSbL/nUq49ar0xKDYxUpnSXvvc1TAF4rWyHGMAQx1IU/cvxme8ta4qbv724IjtJVh1NS1aJp/ybtPpMlLh96yIFc6nDFr3EAPolKw/MAV71FJB1D0Bpu20TgOUQH2gZ9nxxmShx9QKIIId32qKs/EdEjS659IqI9d8eOatQ/CZBTpQo";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

            while (getRuntime() >= 0 && getRuntime() <= 2 && opModeIsActive()) {
                colorArm.setPosition(0);
                colorPivot.setPosition(0.5);

                telemetry.addData("Current Action", "Bring Down Color Arm");
                telemetry.update();
                idle();
            }

            //Runs a function which checks the color of the jewel and moves based on it
            while (getRuntime() >= 2 && getRuntime() <= 4 && opModeIsActive()) {
                blueAllianceColor();

                telemetry.addData("Current Action", "Blue Alliance Color");
                telemetry.update();
                idle();
            }

            //Resets the position of the jewel manipulator
            while (getRuntime() >= 4 && getRuntime() <= 6 && opModeIsActive()) {
                colorPivot.setPosition(0.5);
                colorArm.setPosition(0.9);

                telemetry.addData("Current Action", "Re-center Arm");
                telemetry.update();
                idle();
            }

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);

                //If the VuMark is returns LEFT
                if (vuMark.equals(RelicRecoveryVuMark.LEFT)) {
                    //Sets the jewel manipulator to center position with the arm extended

                    //Drives the robot to the safezone
                    while (getRuntime() >= 6 && getRuntime() <= 8 && opModeIsActive()) {

                        frontLeft.setPower(-0.75);
                        backLeft.setPower(-0.75);

                        frontRight.setPower(0.75);
                        backRight.setPower(0.75);

                        telemetry.addData("Current Action", "Drive Forward");
                        telemetry.update();
                        idle();
                    }

                    //Drives the robot backwards in order to not score be in contact with a glyph in the cryptobox
                    while (getRuntime() >= 8 && getRuntime() <= 9 && opModeIsActive()) {

                        frontLeft.setPower(0.25);
                        backLeft.setPower(0.25);

                        frontRight.setPower(-0.25);
                        backRight.setPower(-0.25);

                        telemetry.addData("Current Action", "Drive Away from Crypto");
                        telemetry.update();
                        idle();
                    }
                }

                else {
                    telemetry.addData("VuMark", "not visible");
                }

                telemetry.update();
                //Function that checks the color of the jewel, assuming you are the Blue Alliance
            }

            //If the VumMark returns CENTER
            else if (vuMark.equals(RelicRecoveryVuMark.CENTER))
            {
                //Closes glyph arms
                while (getRuntime() >= 6 && getRuntime() <= 8 && opModeIsActive()) {

                    topLiftLeft.setPosition(0);
                    bottomLiftLeft.setPosition(1);

                    topLiftRight.setPosition(1);
                    bottomLiftRight.setPosition(0);

                    telemetry.addData("Current Action", "Close glyph arms");
                    telemetry.update();
                    idle();
                }

                //Elevates lift for glyph manipulator
                while (getRuntime() >= 8 && getRuntime() <= 10 && opModeIsActive()) {

                    lift.setPower(-0.5);

                    telemetry.addData("Current Action", "Actuate Lift");
                    telemetry.update();
                    idle();
                }

                lift.setPower(0);

                //Strafes right to line up with center crypto column
                while (getRuntime() >= 10 && getRuntime() <= 12 && opModeIsActive()) {

                    frontLeft.setPower(-0.25);
                    frontRight.setPower(-0.25);

                    backLeft.setPower(0.25);
                    backRight.setPower(0.25);

                    telemetry.addData("Current Action", "Strafe Right");
                    telemetry.update();
                    idle();
                }

                //Drives forward to crypto column
                while (getRuntime() >= 12 && getRuntime() <= 16 && opModeIsActive()){

                    frontLeft.setPower(-0.75);
                    backLeft.setPower(-0.75);

                    frontRight.setPower(0.75);
                    backRight.setPower(0.75);

                    telemetry.addData("Current Action", "Drive to crypto");
                    telemetry.update();
                    idle();
                }

                //Opens glyph servos
                while (getRuntime() >= 16 && getRuntime() <= 18 && opModeIsActive())
                {
                    topLiftLeft.setPosition(0.4);
                    bottomLiftLeft.setPosition(0.6);

                    topLiftRight.setPosition(0.6);
                    bottomLiftRight.setPosition(0.4);

                    telemetry.addData("Current Action", "Open Glyph Arms");
                    telemetry.update();
                    idle();
                }

                while (getRuntime() >= 18 && getRuntime() <= 20 && opModeIsActive())
                {
                    frontLeft.setPower(0.25);
                    backLeft.setPower(0.25);

                    frontRight.setPower(-0.25);
                    backRight.setPower(-0.25);

                    telemetry.addData("Current Action", "Drive Away from Crypto");
                    telemetry.update();
                    idle();
                }
            }

            //If the VuMark returns RIGHT
            else if (vuMark.equals(RelicRecoveryVuMark.RIGHT))
            {
                //Closes glyph arms
                while (getRuntime() >= 6 && getRuntime() <= 8 && opModeIsActive()) {

                    topLiftLeft.setPosition(0);
                    bottomLiftLeft.setPosition(1);

                    topLiftRight.setPosition(1);
                    bottomLiftRight.setPosition(0);

                    telemetry.addData("Current Action", "Close glyph arms");
                    telemetry.update();
                    idle();
                }

                //Elevates lift for glyph manipulator
                while (getRuntime() >= 8 && getRuntime() <= 10 && opModeIsActive()) {

                    lift.setPower(-0.5);

                    telemetry.addData("Current Action", "Actuate Lift");
                    telemetry.update();
                    idle();
                }

                lift.setPower(0);

                //Strafes right to line up with center crypto column
                while (getRuntime() >= 10 && getRuntime() <= 14 && opModeIsActive()) {

                    frontLeft.setPower(-0.25);
                    frontRight.setPower(-0.25);

                    backLeft.setPower(0.25);
                    backRight.setPower(0.25);

                    telemetry.addData("Current Action", "Strafe Right");
                    telemetry.update();
                    idle();
                }

                //Drives forward to crypto column
                while (getRuntime() >= 14 && getRuntime() <= 18 && opModeIsActive()){

                    frontLeft.setPower(-0.75);
                    backLeft.setPower(-0.75);

                    frontRight.setPower(0.75);
                    backRight.setPower(0.75);

                    telemetry.addData("Current Action", "Drive to crypto");
                    telemetry.update();
                    idle();
                }

                while (getRuntime() >= 18 && getRuntime() <= 20 && opModeIsActive())
                {
                    topLiftLeft.setPosition(0.4);
                    bottomLiftLeft.setPosition(0.6);

                    topLiftRight.setPosition(0.6);
                    bottomLiftRight.setPosition(0.4);

                    telemetry.addData("Current Action", "Open Glyph Arms");
                    telemetry.update();
                    idle();
                }

                while (getRuntime() >= 20 && getRuntime() <= 22 && opModeIsActive())
                {
                    frontLeft.setPower(0.25);
                    backLeft.setPower(0.25);

                    frontRight.setPower(-0.25);
                    backRight.setPower(-0.25);

                    telemetry.addData("Current Action", "Drive Away from Crypto");
                    telemetry.update();
                    idle();
                }
            }

            else {
                telemetry.addData("VuMark", "not visible");
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
}