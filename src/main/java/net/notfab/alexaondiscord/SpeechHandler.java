package net.notfab.alexaondiscord;

import net.notfab.alexa.jni.manager.PorcupineManager;
import net.notfab.alexa.jni.manager.PorcupineManagerException;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SpeechHandler implements Runnable {

    public static Queue<byte[]> pcmQueue = new ConcurrentLinkedQueue<>();

    private final PorcupineManager porcupineManager;

    public SpeechHandler() throws PorcupineManagerException {
        this.porcupineManager = new PorcupineManager("C:\\Workspace\\AlexaOnDiscord\\src\\main\\resources\\porcupine_params.pv",
                "C:\\Workspace\\AlexaOnDiscord\\src\\main\\resources\\porcupine_windows.ppn",
                1.0F,
                new KeywordHandler());
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            byte[] bytes = pcmQueue.poll();
            if (bytes == null) {
                return;
            }
            try {
                porcupineManager.consume(this.toShort(bytes));
            } catch (PorcupineManagerException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private short[] toShort(byte[] bytes) {
        int size = bytes.length;
        short[] shortArray = new short[size];
        for (int index = 0; index < size; index++)
            shortArray[index] = bytes[index];
        return shortArray;
    }

}
