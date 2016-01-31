package io.github.benas.randombeans.beans;

import java.util.List;

public class Mamals {

    Mammal mamal;
    MammalImpl mamalImpl;

    List<Mammal> mamalList;
    List<MammalImpl> mamalImplList;
    
    public Mammal getMamal() {
        return mamal;
    }

    public void setMamal(Mammal mamal) {
        this.mamal = mamal;
    }

    public MammalImpl getMamalImpl() {
        return mamalImpl;
    }

    public void setMamalImpl(MammalImpl mamalImpl) {
        this.mamalImpl = mamalImpl;
    }

    protected List<Mammal> getMamalList() {
        return mamalList;
    }

    protected void setMamalList(List<Mammal> mamalList) {
        this.mamalList = mamalList;
    }

    protected List<MammalImpl> getMamalImplList() {
        return mamalImplList;
    }

    protected void setMamalImplList(List<MammalImpl> mamalImplList) {
        this.mamalImplList = mamalImplList;
    }
}
