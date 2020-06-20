package ru.javanerd;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        //Создание барьера с ожиданием 5 потоков и
        //потоком Action который будет выполнен при снятии барьера
        CyclicBarrier cb = new CyclicBarrier(5, new Action());

        System.out.println("Старт потоков!");

        //Запуск потоков
        new CustomThread(cb, "Поток a");
        new CustomThread(cb, "Поток b");
        new CustomThread(cb, "Поток c");
        new CustomThread(cb, "Поток d");
        new CustomThread(cb, "Поток e");
    }
}

//Реализация потоков, которые будут ждать снятия барьера
class CustomThread implements Runnable {
    final private CyclicBarrier cb;
    final private String message;

    CustomThread(CyclicBarrier cb, String message) {
        this.cb = cb;
        this.message = message;
        //Старт потока
        new Thread(this).start();
    }

    public void run() {
        System.out.println(this.message);
        try {
            //Ожидание потоком снятия барьера
            this.cb.await();
        } catch (BrokenBarrierException | InterruptedException ex) {
            System.out.println(ex);
        }
    }
}

//Поток который будет запущен при снятии барьера
class Action implements Runnable {
    public void run() {
        System.out.println("Барьер снят!");
    }
}
