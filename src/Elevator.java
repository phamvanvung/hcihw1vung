
import processing.core.PApplet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vanpham
 */
public class Elevator {

    public int floorNumber;
    public boolean floors[];
    public boolean closeDoor;
    public boolean openDoor;
    public boolean disability;
    public boolean alarm;
    public boolean call;
    public boolean stop;
    public boolean fire;
    public int way = -1;
    public int minY;
    public int maxY;
    public int posY;
    public int floorHeight;
    private PApplet apl;
    private AudioPlayer audioPlayer;

    public Elevator(PApplet apl, int floorNumber, int floorHeight, int minY, int maxY) {
        this.apl = apl;
        audioPlayer = new AudioPlayer(apl);
        audioPlayer.ping();
        this.floorNumber = floorNumber;
        floors = new boolean[floorNumber];
        for (int i = 0; i < floors.length; i++) {
            floors[i] = false;
        }
        this.closeDoor = false;
        this.openDoor = false;
        this.disability = false;
        this.alarm = false;
        this.call = false;
        this.stop = false;
        this.fire = false;
        this.floorHeight = floorHeight;
        this.minY = minY;
        this.maxY = maxY;
        this.posY = minY;
    }

    public void setCloseDoor(boolean closeDoor) {
        this.closeDoor = closeDoor;

    }

    public void setOpenDoor(boolean openDoor) {
        this.openDoor = openDoor;
    }

    public void setDisability(boolean disability) {
        if (this.disability != disability) {
            this.disability = disability;
            if (disability) {
                audioPlayer.readerModeActivated();
            } else {
                audioPlayer.readerModeDeactivated();
            }
        }
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public void setCall(boolean call) {
        this.call = call;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public void setFloor(int i, boolean status) {
        this.floors[i] = status;
        if (status && this.disability) {
            switch (i) {
                case 0:
                    audioPlayer.thirdFloorSelected();
                    break;
                case 1:
                    audioPlayer.secondFloorSelected();
                    break;
                case 2:
                    audioPlayer.firstFloorSelected();
                    break;
                case 3:
                    audioPlayer.groundFloorSelected();
                    break;
            }
        }

    }

    public void go() {
        this.posY = this.posY + way;
    }

    public void readButton(int i) {
        if (this.disability) {
            switch (i) {
                case 0:
                    audioPlayer.thirdFloorButton();
                    break;
                case 1:
                    audioPlayer.secondFloorButton();
                    break;
                case 2:
                    audioPlayer.firstFloorButton();
                    break;
                case 3:
                    audioPlayer.groundFloorButton();
                    break;
                case 4:
                    audioPlayer.emergencyStopButton();
                    break;
                case 5:
                    audioPlayer.fireOperatorButton();
                    break;
                case 6:
                    audioPlayer.emergencyAlarmButton();
                    break;
                case 7:
                    audioPlayer.callElevatorOperatorButton();
                    break;
                case 8:
                    audioPlayer.screenReaderButton();
                    break;
                case 9:
                    audioPlayer.closeDoorButton();
                    break;
                case 10:
                    audioPlayer.openDoorButton();
                    break;

            }
        }
    }
}
