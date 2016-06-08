package hu.cdogbot.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by cdog on 2016.06.08..
 */
public class DefaultResponses {
    private DefaultResponses() {
    }

    public static final List<String> RANDOM_RESPONSES = Arrays.asList(
        "Bocsesz de nem értettem.",
        "Ilyet még sosem kérdeztek tőlem.",
        "Ezt most nem vágom.",
        "Te különleges vagy. Olyan furcsán beszélsz.");

    private static final Random RAND = new Random();

    public static String selectRandom() {
        return RANDOM_RESPONSES.get(RAND.nextInt(RANDOM_RESPONSES.size()));
    }
}
