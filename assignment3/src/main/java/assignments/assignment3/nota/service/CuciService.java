package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{

    //Saya menambahkan boolean done untuk setiap service
    private boolean done;

    //jika dowork done akan true
    @Override
    public String doWork() {
        // 
        done = true;
        return "Sedang mencuci...";
    }

    //mereturn boolean isdone(status service)
    @Override
    public boolean isDone() {
        return done;
    }

    //harga sudah dicalculate di nota sehingga 0
    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
