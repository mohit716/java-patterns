package bridge;

// Bridge Pattern = separate an abstraction from its implementation
// so both can vary independently.
//
// Abstraction: Remote
// Implementation: Device
//
// Remote "has a" Device, instead of Remote being tightly coupled to TV/Radio.

interface Device {
    void powerOn();
    void powerOff();
    void setVolume(int v);
    int getVolume();
    String getName();
}

// Concrete implementations
class TV implements Device {
    private boolean on = false;
    private int volume = 10;

    public void powerOn()  { on = true;  System.out.println("TV: ON"); }
    public void powerOff() { on = false; System.out.println("TV: OFF"); }

    public void setVolume(int v) {
        volume = v;
        System.out.println("TV: volume = " + volume);
    }

    public int getVolume() { return volume; }
    public String getName() { return "TV"; }
}

class Radio implements Device {
    private boolean on = false;
    private int volume = 5;

    public void powerOn()  { on = true;  System.out.println("Radio: ON"); }
    public void powerOff() { on = false; System.out.println("Radio: OFF"); }

    public void setVolume(int v) {
        volume = v;
        System.out.println("Radio: volume = " + volume);
    }

    public int getVolume() { return volume; }
    public String getName() { return "Radio"; }
}

// Abstraction
class Remote {
    protected Device device;

    public Remote(Device device) {
        this.device = device;
    }

    public void togglePower() {
        // For demo: just call powerOn (real code would track power state)
        System.out.println("Remote toggles power for " + device.getName());
        device.powerOn();
    }

    public void volumeUp() {
        device.setVolume(device.getVolume() + 1);
    }

    public void volumeDown() {
        device.setVolume(device.getVolume() - 1);
    }
}

// Refined abstraction
class AdvancedRemote extends Remote {
    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        System.out.println("AdvancedRemote: mute " + device.getName());
        device.setVolume(0);
    }
}

public class Main {
    public static void main(String[] args) {

        // Same Remote types can control different Devices
        Device tv = new TV();
        Remote basicTvRemote = new Remote(tv);
        basicTvRemote.togglePower();
        basicTvRemote.volumeUp();

        System.out.println();

        Device radio = new Radio();
        AdvancedRemote advancedRadioRemote = new AdvancedRemote(radio);
        advancedRadioRemote.togglePower();
        advancedRadioRemote.mute();
    }
}
