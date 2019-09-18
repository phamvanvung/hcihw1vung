
import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlP5;
import controlP5.Icon;
import controlP5.Slider;
import controlP5.Textlabel;

import processing.core.PApplet;
import processing.core.PFont;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vanpham
 */
public class Sketch extends PApplet {

    private final int screenWidth = 400;
    private final int screenHeight = 500;
    private final int startX = 55;
    private final int startY = 100;
    private final int btnWidth = 60;
    private final int btnHeight = 60;
    private final int sldInfoWidth = 45;
    private final int btnBorderWeight = 4;
    private final int verticalSpace = 20;
    private final int horizontalSpace = 20;
    private final int secondColX = startX + btnWidth + verticalSpace + sldInfoWidth + verticalSpace;
    private final int thirdColX = secondColX + btnWidth + verticalSpace;
    private final String[] floors = {"3", "2", "1", "G*"};
    private final Button[] floorButtons = new Button[floors.length];
    private Button btnStop;
    private Button btnFire;
    private Button btnDisability;
    private Button btnCall;
    private Button btnAlarm;
    private Button btnOpen;
    private Button btnClose;
    private Button[] allButtons;

    private Icon icoPos;
    private Icon icoLeftArrow;
    private Icon icoRightArrow;
    private final int redColor = color(255, 79, 26);
    private final int orangeColor = color(255, 154, 0);
    private final int greenColor = color(68, 255, 0);
    private final int blackColor = color(0, 0, 0);
    private final int grayColor = color(100, 100, 100);
    private final int whiteColor = color(255, 255, 255);
    private int defaultControlBGColor;
    private int lightBlueColor = color(0, 255, 255);
    Elevator elvControl;
    Slider sldCapacity;

    int rightArrowCode = 0x00f105;
    int leftArrowCode = 0x00f104;
    int upArrowCode = 0x00f102;
    int downArrowCode = 0x00f103;

    private ControlP5 cp5;

    @Override
    public void settings() {
        size(screenWidth, screenHeight);
    }

    int b1, b2, c1, c2;

    private int getRowY(int i) {
        return startY + i * (btnHeight + verticalSpace);
    }

    private void paintBackground() {
        background(whiteColor);
        //Position slider
        stroke(blackColor);
        strokeWeight(btnBorderWeight / 2);
        fill(whiteColor);
        rect(startX + btnWidth + horizontalSpace - btnBorderWeight / 2,
                startY - btnBorderWeight / 2, sldInfoWidth, floors.length * (btnHeight + verticalSpace) - verticalSpace + btnBorderWeight);

        c1 = greenColor;
        c2 = orangeColor;
        setCapacity(startX, startY - 50, 3 * (btnWidth + horizontalSpace) + sldInfoWidth, 20, c1, c2);
        fill(defaultControlBGColor);
        text("Capacity: 1600 Kg, 21 Persons", startX, startY - 35);
    }

    void setCapacity(int x, int y, float w, float h, int c1, int c2) {
        noFill();
        for (int i = x; i <= x + w; i++) {
            float inter = map(i, x, x + w, 0, 1);
            int c = lerpColor(c1, c2, inter);
            stroke(c);
            line(i, y, i, y + h);
        }
        //Draw current capacity
        stroke(blackColor);
        strokeWeight(3);
        //Set capacity
        line(thirdColX, y - 2, thirdColX, y + h + 2);
    }

    @Override
    public void setup() {

        cp5 = new ControlP5(this);
        ControlFont cf = new ControlFont(createFont("Arial", 24, true));
        cp5.setFont(cf);

        //Floor buttons
        for (int i = 0; i < floors.length; i++) {
            floorButtons[i] = createButton(startX, getRowY(i), floors[i]);
        }
        defaultControlBGColor = floorButtons[0].getColor().getBackground();

        icoPos = addIcon("up", startX + btnWidth + horizontalSpace - 11, getRowY(3) + 5, upArrowCode, orangeColor);

        btnStop = createButton(secondColX, getRowY(0), "v", "Wingdings 2", redColor);//Stop
        btnFire = createIconButton(thirdColX, getRowY(0), 0x00f06d, "btnFire", redColor);
        btnDisability = createIconButton(secondColX, getRowY(2), 0x00f193, "btnDisability", lightBlueColor);
        btnAlarm = createButton(secondColX, getRowY(1), "%", "Wingdings", orangeColor);//Alarm
        btnCall = createButton(thirdColX, getRowY(1), ")", "Wingdings", greenColor);//Call

        //Close door
        btnClose = createButton(secondColX, getRowY(3), ">||<");
        //Open door
        btnOpen = createButton(thirdColX, getRowY(3), "<||>");

        //Info panel
        fill(defaultControlBGColor);
        rect(startX, 10, screenWidth - startX * 2, 50);
        createText(thirdColX, getRowY(2), "z", "Webdings", 50, redColor);
        elvControl = new Elevator(this, 4, btnHeight + verticalSpace, getRowY(3), getRowY(0), startY);
        setupButtonsForVoice();

        //Arrow left/right
        icoLeftArrow = addIcon("arrowLeft", startX - 25, getRowY(3) + 5, rightArrowCode, orangeColor, 30);
        icoRightArrow = addIcon("arrowRight", startX + 25, getRowY(3) + 5, leftArrowCode, orangeColor, 30);
    }

    @Override
    public void draw() {
        paintBackground();
        for (int i = 0; i < this.floorButtons.length; i++) {
            if (elvControl.floors[i]) {
                this.floorButtons[i].setColorBackground(orangeColor);
            } else {
                this.floorButtons[i].setColorBackground(defaultControlBGColor);
            }
        }
        if (elvControl.disability) {
            btnDisability.setColorBackground(orangeColor);
        } else {
            btnDisability.setColorBackground(defaultControlBGColor);
        }
        elvControl.go();
        if (this.elvControl.isGoingUp) {
            icoPos.setFontIcon(upArrowCode);
        }
        if (this.elvControl.isGoingDown) {
            icoPos.setFontIcon(downArrowCode);
        }
        icoPos.setPosition(icoPos.getPosition()[0], elvControl.posY);
        blinkArrows();
    }
    private int prevBlinkTime = 0;

    private void blinkArrows() {
        if (elvControl.isClosing) {
            icoLeftArrow.setFontIcon(rightArrowCode);
            icoRightArrow.setFontIcon(leftArrowCode);
        }
        if (elvControl.isOpening) {
            icoLeftArrow.setFontIcon(leftArrowCode);
            icoRightArrow.setFontIcon(rightArrowCode);
        }
        float[] leftPos = icoLeftArrow.getPosition();
        float[] rightPos = icoRightArrow.getPosition();
        //Set position
        if (elvControl.isClosing || elvControl.isOpening) {
            icoLeftArrow.setPosition(leftPos[0], elvControl.posY);
            icoRightArrow.setPosition(rightPos[0], elvControl.posY);
        } else {
            icoLeftArrow.setPosition(leftPos[0], -1000);
            icoRightArrow.setPosition(rightPos[0], -1000);
        }
        if (elvControl.isClosing || elvControl.isOpening) {
            int curBlinkTime = millis();
            if (curBlinkTime - prevBlinkTime > 500) {
                if (icoLeftArrow.getColor().getForeground() == orangeColor) {
                    icoLeftArrow.setColor(new CColor().setForeground(whiteColor));
                    icoRightArrow.setColor(new CColor().setForeground(whiteColor));
                } else {
                    icoLeftArrow.setColor(new CColor().setForeground(orangeColor));
                    icoRightArrow.setColor(new CColor().setForeground(orangeColor));
                }
                prevBlinkTime = curBlinkTime;
            }
        }

    }

    private void setupButtonsForVoice() {
        int numOfFloor = floorButtons.length;
        allButtons = new Button[numOfFloor + 7];
        //Setup buttons
        for (int i = 0; i < numOfFloor; i++) {
            Button floorButton = floorButtons[i];
            allButtons[i] = floorButton;
        }
        allButtons[numOfFloor] = btnStop;
        allButtons[numOfFloor + 1] = btnFire;
        allButtons[numOfFloor + 2] = btnAlarm;
        allButtons[numOfFloor + 3] = btnCall;
        allButtons[numOfFloor + 4] = btnDisability;
        allButtons[numOfFloor + 5] = btnClose;
        allButtons[numOfFloor + 6] = btnOpen;
    }
    private int prevMouseX = 0;
    private int prevMouseY = 0;
    private boolean dragged = false;

    @Override
    public void mouseReleased() {
        dragged = false;
    }

    @Override
    public void mouseDragged() {
        elvControl.setDisability(true);
        for (int i = 0; i < allButtons.length; i++) {
            Button btn = allButtons[i];
            float[] btnPos = btn.getPosition();
            float radius = Math.min(btnWidth / 2, btnHeight / 2);
            float d = dist(mouseX, mouseY, btnPos[0] + btnWidth / 2, btnPos[1] + btnHeight / 2);
            float prevD = dist(prevMouseX, prevMouseY, btnPos[0] + btnWidth / 2, btnPos[1] + btnHeight / 2);
            if (d < radius && prevD > radius) {
                elvControl.readButton(i);
                break;
            }
        }
        prevMouseX = mouseX;
        prevMouseY = mouseY;
        dragged = true;
    }

    public void controlEvent(ControlEvent theEvent) {
        if (dragged) {
            dragged = false;
            return;
        }
        String eventName = theEvent.getName();
        for (int i = 0; i < this.floors.length; i++) {
            String floor = this.floors[i];
            if (eventName.equals(floor)) {
                elvControl.setFloor(i, true);
                return;
            }
        }

        if (eventName.equals(btnClose.getName())) {
            elvControl.setCall(false);//just dummy value to call the sound
            return;
        }

        if (eventName.equals(btnOpen.getName())) {
            elvControl.setCall(false);//just dummy value to call the sound
            return;
        }
        if (eventName.equals(btnDisability.getName()) || eventName.equals(btnDisability.getName() + "icon")) {
            elvControl.setDisability(!elvControl.disability);
            return;
        }
        if (eventName.equals(btnAlarm.getName()) || eventName.equals(btnAlarm.getName() + "icon")) {
            elvControl.setAlarm(!elvControl.alarm);
            return;
        }

        //    private final SoundFile callElevatorOperatorButton;
        if (eventName.equals(btnCall.getName())) {
            elvControl.setCall(!elvControl.call);
            return;
        }

        //    private final SoundFile emergencyStopButton;
        if (eventName.equals(btnStop.getName()) || eventName.equals(btnStop.getName() + "icon")) {
            elvControl.setStop(!elvControl.stop);
            return;
        }
        if (eventName.equals(btnFire.getName()) || eventName.equals(btnFire.getName() + "icon")) {
            elvControl.setFire(!elvControl.fire);
            return;
        }

    }

    //<editor-fold desc="create controls">
    private int capacityColorIndex(float value, float maxValue) {
        int idx = 0;
        float ratio = value / maxValue;
        if (ratio > 0.8) {
            idx = 1;
        }
        if (ratio > 0.9) {
            idx = 2;
        }
        return idx;
    }

    private Textlabel createText(int x, int y, String text) {
        Textlabel tlb = cp5.addTextlabel(text, text, x, y);
        return tlb;
    }

    private Textlabel createText(int x, int y, String text, String fontName, int fontSize) {
        Textlabel tlb = createText(x, y, text);
        PFont pf = createFont(fontName, fontSize, true);
        ControlFont cf = new ControlFont(pf);
        tlb.setFont(cf);
        return tlb;
    }

    private Textlabel createText(int x, int y, String text, String fontName, int fontSize, int color) {
        Textlabel tlb = createText(x, y, text, fontName, fontSize);
        tlb.setColor(color);
        return tlb;
    }

    private Textlabel createText(int x, int y, String text, int color) {
        Textlabel tlb = createText(x, y, text);
        tlb.setColor(color);
        return tlb;
    }

    private Button createButton(int x, int y, String label) {
        //Background
        cp5.addButton(label + "background").setPosition(x - btnBorderWeight / 2, y - btnBorderWeight / 2)
                .setSize(btnWidth + btnBorderWeight, btnHeight + btnBorderWeight)
                .setLabelVisible(false)
                .setColorBackground(blackColor)
                .setColorForeground(blackColor);

        Button btn = cp5.addButton(label)
                .setPosition(x, y)
                .setSize(btnWidth, btnHeight);

        return btn;
    }

    private Button createIconButton(int x, int y, int iconNumber, String id, int color) {
        cp5.addButton(id + "background").setPosition(x - btnBorderWeight / 2, y - btnBorderWeight / 2)
                .setSize(btnWidth + btnBorderWeight, btnHeight + btnBorderWeight)
                .setLabelVisible(false)
                .setColorBackground(blackColor)
                .setColorForeground(blackColor);

        Button btn = cp5.addButton(id)
                .setPosition(x, y)
                .setSize(btnWidth, btnHeight)
                .setLabelVisible(false);

        addIcon(id + "icon", x, y, iconNumber, color);

        return btn;
    }

    private Icon addIcon(String id, int x, int y, int code, int color) {
        Icon ic = cp5.addIcon(id, 10)
                .setPosition(x - btnBorderWeight, y)
                .setSize(70, 50)
                .setRoundedCorners(20)
                .setFont(createFont("resources/fontawesome-webfont.ttf", 40, true))
                .setFontIcon(code)
                .setColorBackground(color(255, 100))
                .hideBackground()
                .setColor(new CColor().setForeground(color));
        return ic;
    }

    private Icon addIcon(String id, int x, int y, int code, int color, int fontSize) {
        Icon ic = addIcon(id, x, y, code, color);
        ic.setFont(createFont("resources/fontawesome-webfont.ttf", fontSize, true));
        return ic;
    }

    private Button createButton(int x, int y, String label, String fontName) {
        Button btn = createButton(x, y, label);
        PFont pf = createFont(fontName, 48, true);
        ControlFont cf = new ControlFont(pf);
        btn.getCaptionLabel().setFont(cf);
        return btn;
    }

    private Button createButton(int x, int y, String label, String fontName, int color) {
        Button btn = createButton(x, y, label, fontName);
        btn.getCaptionLabel().setColor(color);
        return btn;
    }

    private Button createButton(int x, int y, String label, int color) {
        Button btn = createButton(x, y, label);
        btn.getCaptionLabel().setColor(color);
        return btn;
    }
    //</editor-fold>

    public static void main(String[] args) {
        Sketch mySketch = new Sketch();
        PApplet.runSketch(new String[]{"Elevator"}, mySketch);
    }

}
