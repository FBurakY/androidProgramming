package com.fburaky.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Fruit {

    // Meyvelerin yarı çapını oluşturmak istiyorum ama ekranlara göre dinamik olmalı.
    public static float radius = 60f;


    public  enum Type {

        REGULAR , EXTRA , ENEMY , LIFE

    }

    Type type;
    Vector2 pos , velocity;

    public  boolean living = true;

    Fruit(Vector2 pos , Vector2 velocity){

        this.pos = pos;
        this.velocity = velocity;
        type = Type.REGULAR;
    }


    // Herhangi bir meyveye tıklandı mı , tıklanmadı mı onu anlamamız için metot yazalım .
    public boolean clicked(Vector2 click){

        //Tıklanıldığını Nasıl anlayacağız
        if (pos.dst2(click) <= radius * radius + 1) {
            return true;
        }

        return  false;
    }

    // Meyvenin güncel pozisyonunu almak için aşağıdaki metodu kullanacağız
    public final Vector2 getPos(){

        return pos;
    }

    // Meyvenin dışarıya çıkıp çıkmadığını almamız için kullanacağımız metot .
    public boolean outOfScreen(){

        return (pos.y < -2f * radius);
    }

    // Meyvenin ekranda haretinin hızını sürekli olarak güncellenmesi
    public void update(float dt){

        velocity.y -= dt * (Gdx.graphics.getHeight() * 0.2f);
        velocity.x -= dt * Math.signum(velocity.x) * 5f;

        pos.mulAdd(velocity,dt);
    }
}
