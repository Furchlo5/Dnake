package com.dnake.utils;

import com.dnake.engine.Map;
import com.dnake.entities.Snake;
import com.dnake.interfaces.Spawnable;
import java.util.Random;
;

public class EntitySpawner<T>{

    // create a food in a random location
        public T createRandomObject(Snake snake, Spawnable<T> factory) {    
            Random random_object = new Random();
            int object_random_x;
            int object_random_y;
    
            do {
                object_random_x = random_object.nextInt(Map.WIDTH);
                object_random_y = random_object.nextInt(Map.HEIGHT);
            } while (snake.checkSnakeAt(object_random_x, object_random_y) != null);

            // TODO: food ve poison aynı değerleri alabiliyor -> fix it 
    
            // ai çözümü
            T obj = factory.spawn(object_random_x, object_random_y);
            return obj;
    
            /*  TODO
                yılanın boyu uzadıkça yemi boş alanda oluşturma olasılığı çok düşecek
                bu yüzden sonsuz döngüye girme ihtimali artacak
                
                çözüm: map sınıfında boş koordinatları bir ArrayList içerisinde tutarak
                food'umuzu o boş alandan seçtirebiliriz. böylece yılanın boyu ne kadar
                uzarsa uzasın boş bir alanı %100 bulacak bir algoritmamız olur. 
             */
        }
}
