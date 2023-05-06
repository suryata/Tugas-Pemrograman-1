package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private boolean done;
    @Override
    public String doWork() {
        done = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public long getHarga(int berat) {
        return 1000*berat;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
