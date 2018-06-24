package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by ShambotGold on 2/14/2018.
 */
@Autonomous(name = "RobotJava Test", group = "Sctuff")
public class RobotJavaTest extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.initialize(RobotJavaTest.this, hardwareMap, telemetry);

        waitForStart();

        robot.driveForward(6);
    }
}
