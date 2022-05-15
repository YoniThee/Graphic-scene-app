package primitives;

public class Material {
   public Double3 kD = new Double3(0.0), kS = new Double3(0.0),
    kT = new Double3(0.0),kR = new Double3(0.0);
   public int nShininess  = 0;





    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }


    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
