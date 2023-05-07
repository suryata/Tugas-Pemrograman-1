package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{

    //Saya menambahkan boolean done untuk setiap service
    private boolean done;

    //jika dowork done akan true
    @Override
    public String doWork() {
        done = true;
        return "Sedang menyetrika...";
    }

    //mereturn boolean isdone(status service)
    @Override
    public boolean isDone() {
        return done;
    }

    //harga setrika sesuai ketentuan soal
    @Override
    public long getHarga(int berat) {
        return 1000*berat;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
