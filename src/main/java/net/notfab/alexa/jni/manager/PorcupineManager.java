/*
    Copyright 2018 Picovoice Inc.

    You may not use this file except in compliance with the license. A copy of the license is
    located in the "LICENSE" file accompanying this source.

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
    express or implied. See the License for the specific language governing permissions and
    limitations under the License.
*/

package net.notfab.alexa.jni.manager;

import net.notfab.alexa.jni.Porcupine;
import net.notfab.alexa.jni.PorcupineException;

/**
 * Records audio from microphone, processes it in real-time using Porcupine engine, and notifies the
 * client when the a keyword is detected.
 */
public class PorcupineManager {

    private final Porcupine porcupine;
    private final KeywordCallback keywordCallback;

    int getSampleRate() {
        return porcupine.getSampleRate();
    }

    int getFrameLength() {
        return porcupine.getFrameLength();
    }

    public void consume(short[] pcm) throws PorcupineManagerException {
        try {
            final int keyword_index = porcupine.process(pcm);
            if (keyword_index >= 0) {
                keywordCallback.run(keyword_index);
            }
        } catch (PorcupineException e) {
            throw new PorcupineManagerException(e);
        }
    }

    /**
     * Constructor for single keyword use case.
     *
     * @param modelFilePath   Absolute path to file containing model parameters.
     * @param keywordFilePath Absolute path to keyword file containing hyper-parameters.
     * @param sensitivity     Sensitivity parameter. A higher sensitivity value lowers miss rate
     *                        at the cost of increased false alarm rate.
     * @param keywordCallback callback when hte keyword is detected.
     * @throws PorcupineManagerException if there is an error while initializing Porcupine.
     */
    public PorcupineManager(
            String modelFilePath,
            String keywordFilePath,
            float sensitivity,
            KeywordCallback keywordCallback) throws PorcupineManagerException {
        try {
            porcupine = new Porcupine(modelFilePath, keywordFilePath, sensitivity);
        } catch (PorcupineException e) {
            throw new PorcupineManagerException(e);
        }

        this.keywordCallback = keywordCallback;
    }

    /**
     * Constructor for multiple keywords use case.
     *
     * @param modelFilePath    Absolute path to file containing model parameters.
     * @param keywordFilePaths Absolute path to keyword files.
     * @param sensitivities    Array of sensitivity parameters for each keyword.
     * @param keywordCallback  Callback when keyword is detected.
     * @throws PorcupineManagerException if there is an error while initializing Porcupine.
     */
    public PorcupineManager(
            String modelFilePath,
            String[] keywordFilePaths,
            float[] sensitivities,
            KeywordCallback keywordCallback) throws PorcupineManagerException {
        try {
            porcupine = new Porcupine(modelFilePath, keywordFilePaths, sensitivities);
        } catch (PorcupineException e) {
            throw new PorcupineManagerException(e);
        }

        this.keywordCallback = keywordCallback;
    }

}
