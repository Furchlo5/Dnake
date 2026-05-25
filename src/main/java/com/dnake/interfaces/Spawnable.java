package com.dnake.interfaces;

// bu interface ai tarafıdan oluşturuldu.

public interface Spawnable<T> {
    // içine x ve y alıp T (Food veya Poison) döndüren üretim methodemiz
    public T spawn(int x, int y);
}
