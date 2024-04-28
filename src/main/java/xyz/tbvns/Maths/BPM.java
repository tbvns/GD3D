package xyz.tbvns.Maths;

public class BPM {
    float BPM;
    float BAR;
    public BPM(float BPM, float BPMperBAR) {
        this.BPM = BPM;
        this.BAR = BPMperBAR;
    }



    public float toFPS() {
        if (BAR == 0) {
            BAR = 1;
        }
        return BPM/60;
    }

    public float toMinimalFPS() {
        if (BAR == 0) {
            BAR = 1;
        }
        return (BPM/60)/BAR;
    }

}
