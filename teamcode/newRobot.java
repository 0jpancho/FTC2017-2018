package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class newRobot {

    public DcMotor driveLeft, driveRight;

    public ElapsedTime runtime = new ElapsedTime();
    public LinearOpMode l;
    public Telemetry realTelemetry;

    static final double ENC_COUNTS_PER_REV = 280;
    static final double WHEEL_DIAMETER = 4 * Math.PI;
    static final double COUNTS_PER_INCH = ENC_COUNTS_PER_REV / WHEEL_DIAMETER;

    public void initialize (LinearOpMode Input, HardwareMap hardwareMap, Telemetry telemetry)
    {
        l = Input;
        realTelemetry = telemetry;

        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");

        realTelemetry.addData("Current Function", "Initialize");
        realTelemetry.update();
        l.idle();
    }
}
