package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean done;
    @Override
    public String doWork() {
        // 
        done = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
