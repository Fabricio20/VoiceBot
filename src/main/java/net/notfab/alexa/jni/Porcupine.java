package net.notfab.alexa.jni;

public class Porcupine {

    private final long object;

    static {

    }

    /**
     * Constructor.
     *
     * @param modelFilePath   Absolute path to file containing model parameters.
     * @param keywordFilePath Absolute path to keyword model file.
     * @param sensitivity     Sensitivity parameter. A higher sensitivity value lowers miss rate at
     *                        the cost of increased false alarm rate. A sensitivity value should be
     *                        within [0, 1].
     * @throws PorcupineException if there is an error while initializing Porcupine.
     */
    public Porcupine(String modelFilePath, String keywordFilePath, float sensitivity) throws PorcupineException {
        try {
            System.load("C:/Workspace/AlexaOnDiscord/src/main/resources/libpv_porcupine.dll");
            System.load("C:/Workspace/AlexaOnDiscord/src/main/resources/libjni.dll");
            object = init(modelFilePath, new String[]{keywordFilePath}, new float[]{sensitivity});
        } catch (Exception e) {
            throw new PorcupineException(e);
        }
    }

    /**
     * Constructor.
     *
     * @param modelFilePath    Absolute path to file containing model parameters.
     * @param keywordFilePaths Array of absolute paths to keyword files.
     * @param sensitivities    Array of sensitivity parameters.
     * @throws PorcupineException if there is an error while initializing Porcupine.
     */
    public Porcupine(
            String modelFilePath,
            String[] keywordFilePaths,
            float[] sensitivities) throws PorcupineException {
        try {
            object = 0L;
            //object = init(modelFilePath, keywordFilePaths, sensitivities);
        } catch (Exception e) {
            throw new PorcupineException(e);
        }
    }

    /**
     * Number of audio samples per frame expected by C library.
     *
     * @return acceptable number of audio samples per frame.
     */
    public native int getFrameLength();

    /**
     * Audio sample rate accepted by Porcupine.
     *
     * @return sample rate acceptable by Porcupine.
     */
    public native int getSampleRate();

    /**
     * Monitors incoming audio stream for keywords.
     *
     * @param pcm An array of consecutive audio samples. The number of samples per frame can be
     *            attained by calling {@link #getFrameLength()}. The incoming audio needs to have a
     *            sample rate equal to {@link #getSampleRate()} and be 16-bit linearly-encoded.
     *            Porcupine operates on single-channel audio.
     * @return Index of detected keyword. Indexing is 0-based and according to ordering of keyword
     * files passed to constructor. When no keyword is detected it returns -1.
     * @throws PorcupineException if there is an error while processing the audio sample.
     */
    public int process(short[] pcm) throws PorcupineException {
        try {
            return process(object, pcm);
        } catch (Exception e) {
            throw new PorcupineException(e);
        }
    }

    /**
     * Releases resources acquired.
     */
    public void delete() {
        delete(object);
    }

    private native long init(String modelFilePath, String[] keywordFilePaths, float[] sensitivities);

    private native void delete(long object);

    private native int process(long object, short[] pcm);

}
