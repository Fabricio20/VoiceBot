package net.notfab.alexaondiscord;

import net.notfab.alexa.jni.manager.KeywordCallback;

public class KeywordHandler implements KeywordCallback {

    @Override
    public void run(int keyword_index) {
        System.out.println("Keyword: " + keyword_index);
    }

}
