package assignments.assignment2;
import assignments.assignment1.NotaGenerator;

public class Member {
    //tambahkan attributes yang diperlukan untuk class ini
    // mempunyai atribut nama, noHp, id, bonusCounter, dapatDiskon
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    private boolean dapatDiskon;
    
    public Member(String nama, String noHp) {
        //constructor untuk class ini, setalah diconstruct akan mengeprint berhasil membuat member
        this.nama=nama;
        this.noHp=noHp;
        this.id=NotaGenerator.generateId(this.nama, this.noHp);
        this.bonusCounter=0;
        this.dapatDiskon=false;
        
        System.out.printf("Berhasil membuat member dengan ID %s !",this.getid());
    }

    //getter nama
    public String getNama(){
        return this.nama;
    }

    //getter id
    public String getid(){
        return this.id;
    }

    //getter nohp
    public String getnoHp(){
        return this.noHp;
    }

    //getter bonus
    public int bonusCounter(){
        return this.bonusCounter;
    }

    //methods yang diperlukan untuk class ini
    
    public boolean isEligible() {
        return dapatDiskon;
    }
    
    public void setEligibleForDiscount(boolean eligibleForDiscount) {
        this.dapatDiskon = eligibleForDiscount;
    }

    public void resetbonusCounter(){
        this.bonusCounter=0;
    }

    public void addOrder() {
        bonusCounter++;
        if (bonusCounter == 3) {
            dapatDiskon = true;
        }
    }
}
