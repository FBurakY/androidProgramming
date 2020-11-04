package com.fburaky.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;

	// Arka planı koyabilmemiz için ; Texture'yi kullanıyoruz .
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;

	int score = 0;
	int scoreEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffSet = new float[numberOfEnemies];
	float[] enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3 = new float[numberOfEnemies];

	// Çarpışmayı anlamak için nesnemi oluşturalım .
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	Circle[] enemyCircle;
	Circle[] enemy2Circle;
	Circle[] enemy3Circle;

	Random random;

	float distance = 0;

	float birdX = 0;
	float birdY = 0;

	int gameState = 0;

	float velocity = 0;
	float gravity = 0.1f;

	float enemyVelocity = 2;

	@Override
	public void create () {
		// Oyun başladığında neler olacaksa buraya yazıyoruz
		// Obje yarattığımızda initialize burada yapmaktayız .

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 =  new Texture("bee.png");
		bee2 =  new Texture("bee.png");
		bee3 =  new Texture("bee.png");

		distance = Gdx.graphics.getWidth()/2;

		random = new Random();

		birdX = Gdx.graphics.getWidth()/2-bird.getWidth()/2;
		birdY = Gdx.graphics.getHeight()/3;

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.CYAN);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.DARK_GRAY);
		font2.getData().setScale(6);

		birdCircle = new Circle();
		enemyCircle = new Circle[numberOfEnemies];
		enemy2Circle = new Circle[numberOfEnemies];
		enemy3Circle = new Circle[numberOfEnemies];

		for (int i=0;i<numberOfEnemies;i++){

			enemyOffSet[i]  = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;
			enemyCircle[i] = new Circle();
			enemy2Circle[i] = new Circle();
			enemy3Circle[i] = new Circle();
		}
	}

	@Override
	public void render () {

		// render() metodunda , oyun devam ettiğinde sürekli devam etmesini istediğimiz olayları burada kodluyoruz .
		// render() metodu oyun çalışırken sürekli bir periyot ile çağırılmaktadır .

		batch.begin();
		// begin() ve end() metotlarının arasına ne oluşturmak istediğimizi bu iki metot arasına yazıyoruz .

		// Duvar kağıdımız için aşağıdaki kodumuzu yazıyoruz .
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		// Oyun başladığında durumumuz 1 ;
 		if (gameState == 1){

 			if (enemyX[scoreEnemy] < Gdx.graphics.getWidth()/2-bird.getWidth()/2){
 				score++;
 				if (scoreEnemy < numberOfEnemies - 1){
 					scoreEnemy++;
				}
 				else {
 					scoreEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()){
				// Kullanıcı tıkladığı zaman ne olmasını istiyorsak !
				velocity = -7;
			}


			for (int i = 0 ; i < numberOfEnemies ; i++){

				if (enemyX[i] < Gdx.graphics.getWidth()/12 ){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i]  = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}
				else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);

				enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);
				enemy2Circle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);
				enemy3Circle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);

			}
			if (birdY > 0 ){

				// Kullanıcı oyunu başlattığı zaman yer çekimini oluşturmak istiyoruz .
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}
			else{
				// Kuş Y ekseninin altına düşerse oyunumuz bitecek !
				gameState = 2;
			}
		}
 		// Oyun hiç başlamamışsa ...
		else if (gameState == 0){
			if (Gdx.input.justTouched()){
				// Kullanıcı tıkladığı zaman ne olmasını istiyorsak !
				gameState = 1;
			}
		}
		// Oyun Sonlandığında ...
		else if (gameState == 2){

				font2.draw(batch,"Game Over ! Tap To Play Again" , 350 ,Gdx.graphics.getHeight()/2);

				if (Gdx.input.justTouched()){
					gameState = 1;
					birdY = Gdx.graphics.getHeight()/3;
				}

			for (int i=0;i<numberOfEnemies;i++){

				enemyOffSet[i]  = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


				enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;
				enemyCircle[i] = new Circle();
				enemy2Circle[i] = new Circle();
				enemy3Circle[i] = new Circle();
			}

			velocity = 0;
			scoreEnemy = 0;
			score = 0;
		}

		batch.draw(bird , birdX , birdY ,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/8);
		font.draw(batch , String.valueOf(score) , 100,200);
		batch.end();
		birdCircle.set(birdX + Gdx.graphics.getWidth()/20,birdY + Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/20);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.GRAY);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


		for (int i = 0 ; i < numberOfEnemies ; i++){

			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/24 , Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);

			if (Intersector.overlaps(birdCircle,enemyCircle[i]) || Intersector.overlaps(birdCircle,enemy2Circle[i]) || Intersector.overlaps(birdCircle,enemy3Circle[i])) {
				// Çarpışma olduğunda oyunu sonrandırmak istiyorum .
				gameState = 2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
	/*

	ÖNEMLİ NOT : Oyunlarda sprite denilen bir takım nesneler ile çalışmaktayız .
			     ilk önce spritebatch sınıfından bir obje oluşturacağız ve bunu kullanarak istediğimiz nesneyi oluşturacağız .
	* */
}
