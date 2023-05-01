package assignments.assignment3.nota.service;

// import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private boolean done;
    @Override
    public String doWork() {
        done = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public long getHarga(int berat) {
        if ((berat*500)<2000){
            return 2000;
        }else{
            return berat*500;
        }
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
