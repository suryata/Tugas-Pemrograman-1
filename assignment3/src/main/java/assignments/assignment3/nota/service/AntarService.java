package assignments.assignment3.nota.service;

// import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    
    //Saya menambahkan boolean done untuk setiap service
    private boolean done;

    //jika dowork done akan true
    @Override
    public String doWork() {
        done = true;
        return "Sedang mengantar...";
    }

    //mereturn boolean isdone(status service)
    @Override
    public boolean isDone() {
        return done;
    }

    //harga dari antar service sesuai dengan ketentuan soal
    @Override
    public long getHarga(int berat) {
        if ((berat*500)<2000){
            return 2000;
        }else{
            return berat*500;
        }
    }

    //mereturn antar
    @Override
    public String getServiceName() {
        return "Antar";
    }
}
