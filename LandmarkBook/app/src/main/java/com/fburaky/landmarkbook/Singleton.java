package com.fburaky.landmarkbook;

import android.graphics.Bitmap;

public class Singleton {

    // Static anahtar kelimesi ilede bitmap objesine ulaşabiliyoruz ama
    // Static anahtar kelimesini büyük prjolerde kullanırken dikkatli olmalıyız
    // Bunun daha güvenli olan signleton sınıfını kullanacağız .

    // NOT : Tek bir Objeyesi ve Tek bir Instanceye sahip olan sınıfa Singleton sınıf demekteyiz !!!
    //          Ve böylece bana tek bir obje üzerinden işlem yapılmasını istiyorum
    //
    private Bitmap chosenImage;
    private static Singleton singleton;

    private Singleton(){

    }

    public Bitmap getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(Bitmap chosenImage) {
        this.chosenImage = chosenImage;
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }

        return singleton;
    }

}
