/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import processing.core.PApplet;
import processing.sound.*;

public class AudioPlayer {

    ExecutorService pool = Executors.newFixedThreadPool(4);
    PApplet apl;
    private final SoundFile ping;
    private final SoundFile readerModeActivated;
    private final SoundFile readerModeDeactivated;
    private final SoundFile groundFloorSelected;
    private final SoundFile firstFloorSelected;
    private final SoundFile secondFloorSelected;
    private final SoundFile thirdFloorSelected;

    private final SoundFile groundFloor;
    private final SoundFile firstFloor;
    private final SoundFile secondFloor;
    private final SoundFile thirdFloor;

    private final SoundFile groundFloorButton;
    private final SoundFile firstFloorButton;
    private final SoundFile secondFloorButton;
    private final SoundFile thirdFloorButton;
    private final SoundFile callElevatorOperatorButton;
    private final SoundFile closeDoorButton;
    private final SoundFile emergencyAlarmButton;
    private final SoundFile emergencyStopButton;
    private final SoundFile fireOperatorButton;
    private final SoundFile openDoorButton;
    private final SoundFile screenReaderButton;

    public AudioPlayer(PApplet apl) {
        this.apl = apl;
        ping = new SoundFile(this.apl, "resources/ping.wav");

        readerModeActivated = new SoundFile(this.apl, "resources/readerModeActivated.wav");
        readerModeDeactivated = new SoundFile(this.apl, "resources/readerModeDeactivated.wav");

        groundFloorSelected = new SoundFile(this.apl, "resources/groundFloorSelected.wav");
        firstFloorSelected = new SoundFile(this.apl, "resources/firstFloorSelected.wav");
        secondFloorSelected = new SoundFile(this.apl, "resources/secondFloorSelected.wav");
        thirdFloorSelected = new SoundFile(this.apl, "resources/thirdFloorSelected.wav");

        groundFloor = new SoundFile(this.apl, "resources/groundFloor.wav");
        firstFloor = new SoundFile(this.apl, "resources/firstFloor.wav");
        secondFloor = new SoundFile(this.apl, "resources/secondFloor.wav");
        thirdFloor = new SoundFile(this.apl, "resources/thirdFloor.wav");

        groundFloorButton = new SoundFile(this.apl, "resources/groundFloorButton.wav");
        firstFloorButton = new SoundFile(this.apl, "resources/firstFloorButton.wav");
        secondFloorButton = new SoundFile(this.apl, "resources/secondFloorButton.wav");
        thirdFloorButton = new SoundFile(this.apl, "resources/thirdFloorButton.wav");

        callElevatorOperatorButton = new SoundFile(this.apl, "resources/callElevatorOperatorButton.wav");
        closeDoorButton = new SoundFile(this.apl, "resources/closeDoorButton.wav");
        emergencyAlarmButton = new SoundFile(this.apl, "resources/emergencyAlarmButton.wav");
        emergencyStopButton = new SoundFile(this.apl, "resources/emergencyStopButton.wav");
        fireOperatorButton = new SoundFile(this.apl, "resources/fireOperatorButton.wav");
        openDoorButton = new SoundFile(this.apl, "resources/openDoorButton.wav");
        screenReaderButton = new SoundFile(this.apl, "resources/screenReaderButton.wav");
    }

    public void ping() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                ping.play();
            }
        });
    }

    public void readerModeActivated() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                readerModeActivated.play();
            }
        });

    }

    public void readerModeDeactivated() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                readerModeDeactivated.play();
            }
        });

    }

    public void groundFloorSelected() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                groundFloorSelected.play();
            }
        });

    }

    public void firstFloorSelected() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                firstFloorSelected.play();
            }
        });

    }

    public void secondFloorSelected() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                secondFloorSelected.play();
            }
        });

    }

    public void thirdFloorSelected() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                thirdFloorSelected.play();
            }
        });

    }

    public void groundFloor() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                groundFloor.play();
            }
        });

    }

    public void firstFloor() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                firstFloor.play();
            }
        });

    }

    public void secondFloor() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                secondFloor.play();
            }
        });

    }

    public void thirdFloor() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                thirdFloor.play();
            }
        });

    }

    public void groundFloorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                groundFloorButton.play();
            }
        });

    }

    public void firstFloorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                firstFloorButton.play();
            }
        });

    }

    public void secondFloorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                secondFloorButton.play();
            }
        });

    }

    public void thirdFloorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                thirdFloorButton.play();
            }
        });

    }

    public void callElevatorOperatorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                callElevatorOperatorButton.play();
            }
        });

    }

    public void closeDoorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                closeDoorButton.play();
            }
        });

    }

    public void emergencyAlarmButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                emergencyAlarmButton.play();
            }
        });

    }

    public void emergencyStopButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                emergencyStopButton.play();
            }
        });

    }

    public void fireOperatorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                fireOperatorButton.play();
            }
        });

    }

    public void openDoorButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                openDoorButton.play();
            }
        });

    }

    public void screenReaderButton() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                screenReaderButton.play();
            }
        });

    }
}
