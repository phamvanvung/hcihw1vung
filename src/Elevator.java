
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
    boolean isClosing = true;
    boolean isOpening = false;
    boolean isClosed = false;
    boolean isOpened = false;
    private int startY;
    private int currentFloorIndex = 3;

    public Elevator(PApplet apl, int floorNumber, int floorHeight, int minY, int maxY, int startY) {
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
        this.startY = startY;
        this.prevDoorClosingTime = this.apl.millis();

    }

    public void setCloseDoor(boolean closeDoor) {
        this.closeDoor = closeDoor;

    }

    private int getRowY(int i) {
        return startY + i * (floorHeight);
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
        if (getRowY(i) != this.posY) {
            this.floors[i] = status;
        }
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
    int prevDoorClosingTime;
    int prevDoorOpeningTime;
    int prevDoorOpenedTime;
    boolean isGoingUp = true;
    boolean isGoingDown = false;
    boolean isStopped = true;

    public void go() {
        int curTime = this.apl.millis();
        way = -1;
        if (this.isClosing) {
            if (curTime - prevDoorClosingTime > 3000) {
                this.isClosing = false;
                this.isClosed = true;
            }
            way = 0;
            return;
        };

        if (this.isOpening) {
            if (curTime - prevDoorOpeningTime > 3000) {
                this.isOpening = false;
                this.isOpened = true;
                this.prevDoorOpenedTime = this.apl.millis();
            }
            way = 0;
            return;
        }

        if (this.isOpened) {
            if (curTime - prevDoorOpenedTime > 3000) {
                this.isOpened = false;
                this.isClosing = true;
                this.prevDoorClosingTime = this.apl.millis();
            }
            way = 0;
            return;
        }
        for (int i = 0; i < floors.length; i++) {
            boolean floor = floors[i];
            if (floor && this.posY == getRowY(i)) {
                //At the selected floor
                prevDoorOpeningTime = this.apl.millis();
                this.isOpening = true;
                floors[i] = false;//Deselect it.
                currentFloorIndex = i;
                return;
            }
        }

        if (this.isClosed) {
            //Check if there is some selected floor.
            way = 0;

            boolean thereIsUp = false;
            boolean thereIsDown = false;

            for (int i = currentFloorIndex - 1; i >= 0; i--) {
                boolean floor = floors[i];
                if (floor) {
                    thereIsUp = true;
                    break;
                }
            }

            for (int i = currentFloorIndex + 1; i < this.floors.length; i++) {
                boolean floor = floors[i];
                if (floor) {
                    thereIsDown = true;
                    break;
                }
            }

            if (thereIsUp && thereIsDown) {
                if (this.isGoingUp) {
                    way = -1;

                } else if (this.isGoingDown) {
                    way = 1;

                }
            } else if (thereIsUp && !thereIsDown) {
                way = -1;

            } else if (!thereIsUp && thereIsDown) {
                way = 1;
            }
            if (way == 0) {
                this.isStopped = true;
            } else if (way == -1) {
                this.isGoingUp = true;
                this.isGoingDown = false;
            } else if (way == 1) {
                this.isGoingUp = false;
                this.isGoingDown = true;
            }
            this.posY += way;
        }

        if (this.posY == maxY) {
            this.isGoingUp = false;
            this.isGoingDown = true;
        }

        if (this.posY == minY) {
            this.isGoingUp = true;
            this.isGoingDown = false;
        }
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
